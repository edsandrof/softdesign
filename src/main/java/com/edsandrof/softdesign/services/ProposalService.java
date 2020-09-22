package com.edsandrof.softdesign.services;

import com.edsandrof.softdesign.dto.ProposalDTO;
import com.edsandrof.softdesign.exceptions.*;
import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.model.Vote;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.payload.ProposalPayloadPatch;
import com.edsandrof.softdesign.payload.VotePayload;
import com.edsandrof.softdesign.repository.ProposalRepository;
import com.edsandrof.softdesign.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private MemberService memberService;

    public Proposal register(ProposalPayload proposalPayload) {
        Proposal proposal = new Proposal(proposalPayload.getDescription(), proposalPayload.getVotingOptions());
        return proposalRepository.save(proposal);
    }

    public Proposal findById(String id) {
        Optional<Proposal> proposal = proposalRepository.findById(id);
        return proposal.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Proposal> findAll() {
        return proposalRepository.findAll();
    }

    public Proposal openVotingSession(String id, ProposalPayloadPatch proposalPayloadPatch) {
        int minute = 60000;
        Proposal proposal = findById(id);

        if (proposal.getStatus().equals(Proposal.ProposalStatus.OPENED)) {
            throw new VotingSessionOpenedException(id);
        }
        if (proposal.getStatus().equals(Proposal.ProposalStatus.CLOSED)) {
            throw new VotingSessionClosedException(id);
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));

        proposal.setVotingSessionDuration(proposalPayloadPatch.getVotingSessionDuration());
        proposal.setVotingSessionOpeningDate(Date.from(zonedDateTime.toInstant()));
        proposal.setVotingSessionClosingDate(Date.from(zonedDateTime.plusMinutes(proposal.getVotingSessionDuration()).toInstant()));

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Proposal proposalUpdate = findById(id);
                proposalUpdate.setStatus(Proposal.ProposalStatus.CLOSED);
                proposalRepository.save(proposalUpdate);
            }
        };

        Timer timer = new Timer();
        proposal.setStatus(Proposal.ProposalStatus.OPENED);
        timer.schedule(task, (proposal.getVotingSessionDuration() * minute));

        return proposalRepository.save(proposal);
    }

    public Vote vote(String id, VotePayload votePayload) {
        Proposal proposal = findById(id);
        Member member = memberService.findById(votePayload.getMemberId());

        validateVoting(proposal, member, votePayload.getVotingOption());

        Vote vote = new Vote(member, votePayload.getVotingOption());
        proposal.getVotingSession().add(vote);
        proposalRepository.save(proposal);
        return vote;
    }

    private void validateVoting(Proposal proposal, Member member, String votingOption) {
        if (!proposal.getStatus().equals(Proposal.ProposalStatus.OPENED)) {
            throw new VotingSessionClosedException(proposal.getId());
        }
        if (proposal.getVotingSession().contains(new Vote(member, null))) {
            throw new VotingSessionAlreadyVotedException(proposal.getId());
        }
        if (!proposal.getVotingOptions().contains(votingOption)) {
            throw new VotingSessionWrongOptionException(votingOption);
        }
        if (!Utils.canMemberVote(member.getCpf())) {
            throw new MemberCannotVoteException(member.getCpf());
        }
    }

    public ProposalDTO checkResults(String id) {
        Proposal proposal = findById(id);

        StringBuilder sb = new StringBuilder();

        List<Vote> votes = proposal.getVotingSession();
        for (String votingOption : proposal.getVotingOptions()) {
            sb.append("Option '");
            sb.append(votingOption);
            sb.append("' ");
            sb.append(votes.stream().filter(v -> v.getVotingOption().equals(votingOption)).count());
            sb.append(" votes. ");
        }
        sb.append("Total of votes: ");
        sb.append(votes.size());
        return new ProposalDTO(proposal, sb.toString());
    }
}

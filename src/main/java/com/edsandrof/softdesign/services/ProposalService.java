package com.edsandrof.softdesign.services;

import com.edsandrof.softdesign.exceptions.*;
import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.model.Vote;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.payload.ProposalPayloadPatch;
import com.edsandrof.softdesign.payload.VotePayload;
import com.edsandrof.softdesign.repository.ProposalRepository;
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

        if (proposal.getVotingSessionClosingDate() != null) {
            throw new VotingSessionOpenedException(id);
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));

        proposal.setVotingSessionDuration(proposalPayloadPatch.getVotingSessionDuration());
        proposal.setVotingSessionOpeningDate(Date.from(zonedDateTime.toInstant()));
        proposal.setVotingSessionClosingDate(Date.from(zonedDateTime.plusMinutes(proposal.getVotingSessionDuration()).toInstant()));

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Proposal proposalUpdate = findById(id);
                System.out.println("Task performed on: " + new Date() + " Thread's name: " + Thread.currentThread().getName());
                proposalUpdate.setVotingSessionOpen(false);
                proposalRepository.save(proposalUpdate);
            }
        };

        Timer timer = new Timer();
        proposal.setVotingSessionOpen(true);
        timer.schedule(task, (proposal.getVotingSessionDuration() * minute));
        System.out.println("Task performed on: " + new Date() + " Thread's name: " + Thread.currentThread().getName());

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
        if (proposal.getVotingSessionClosingDate() == null) {
            throw new VotingSessionNotOpenedException(proposal.getId());
        }
        if (!proposal.isVotingSessionOpen()) {
            throw new VotingSessionClosedException(proposal.getId());
        }
        if (proposal.getVotingSession().contains(new Vote(member, null))) {
            throw new VotingSessionAlreadyVotedException(proposal.getId());
        }
        if (!proposal.getVotingOptions().contains(votingOption)) {
            throw new VotingSessionWrongOptionException(votingOption);
        }
    }
}

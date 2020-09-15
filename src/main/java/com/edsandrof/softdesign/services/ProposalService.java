package com.edsandrof.softdesign.services;

import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProposalService {
    @Autowired
    private ProposalRepository proposalRepository;

    public Proposal register(ProposalPayload proposalPayload) {
        Proposal proposal = new Proposal(proposalPayload.getDescription(), new Date(), null, null, proposalPayload.getVotingOptions());
        return proposalRepository.save(proposal);
    }

    public Proposal findById(String id) {
        return proposalRepository.findById(id).get();
    }
}

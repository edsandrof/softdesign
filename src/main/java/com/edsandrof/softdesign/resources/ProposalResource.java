package com.edsandrof.softdesign.resources;

import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.services.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api")
public class ProposalResource {
    private static final String V1_PROPOSAL = "/v1/proposal";

    @Autowired
    private ProposalService proposalService;

    @PostMapping(value = V1_PROPOSAL)
    public ResponseEntity<Proposal> register(@RequestBody ProposalPayload proposalPayload) {
        Proposal proposal = proposalService.register(proposalPayload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = V1_PROPOSAL + "/{id}", produces = "application/json")
    public ResponseEntity<Proposal> findById(@PathVariable String id) {
        Proposal proposal = proposalService.findById(id);
        return ResponseEntity.ok().body(proposal);
    }
}

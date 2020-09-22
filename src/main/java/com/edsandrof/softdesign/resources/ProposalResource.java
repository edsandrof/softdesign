package com.edsandrof.softdesign.resources;

import com.edsandrof.softdesign.dto.ProposalDTO;
import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.model.Vote;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.payload.ProposalPayloadPatch;
import com.edsandrof.softdesign.payload.VotePayload;
import com.edsandrof.softdesign.services.ProposalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProposalResource {
    private static final String V1_ENDPOINT = "/api/v1/proposal";

    @Autowired
    private ProposalService proposalService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register new proposal")
    @ApiResponses(value = @ApiResponse(code = 201, message = "Proposal registered"))
    @PostMapping(value = V1_ENDPOINT)
    public ResponseEntity<Proposal> register(@ApiParam(value = "Object containing description and voting options")
                                                 @RequestBody ProposalPayload proposalPayload) {
        Proposal proposal = proposalService.register(proposalPayload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Find a proposal by id")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Resource id not found"),
            @ApiResponse(code = 200, message = "Return the proposal of specified id")
    })
    @GetMapping(value = V1_ENDPOINT + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Proposal> findById(@ApiParam(value = "Id of proposal") @PathVariable String id) {
        Proposal proposal = proposalService.findById(id);
        return ResponseEntity.ok().body(proposal);
    }

    @ApiOperation(value = "List all proposals")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Returns a list of proposals"))
    @GetMapping(value = V1_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proposal>> findAll() {
        List<Proposal> proposals = proposalService.findAll();
        return ResponseEntity.ok().body(proposals);
    }

    @ApiOperation(value = "Open voting session in this proposal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Open voting session"),
            @ApiResponse(code = 409, message = "Voting session is already open")
    })
    @PatchMapping(value = V1_ENDPOINT + "/{id}")
    public ResponseEntity<Proposal> openVotingSession(@ApiParam(value = "Id of proposal") @PathVariable String id,
                                                      @ApiParam(value = "Voting session duration (in minutes)")
                                                      @RequestBody ProposalPayloadPatch proposalPayloadPatch) {
        Proposal proposal = proposalService.openVotingSession(id, proposalPayloadPatch);
        return ResponseEntity.ok().body(proposal);
    }

    @ApiOperation(value = "Vote on proposal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vote registered"),
            @ApiResponse(code = 400, message = "Member submitted an incorrect voting option"),
            @ApiResponse(code = 403, message = "Member has already voted on this proposal or member cannot vote"),
            @ApiResponse(code = 409, message = "Voting session was not opened yet or was opened and closed")
    })
    @PostMapping(value = V1_ENDPOINT + "/{id}/voting")
    public ResponseEntity<Vote> vote(@ApiParam(value = "Id of proposal") @PathVariable String id,
                                     @ApiParam(value = "Object containing a member id and voting option")
                                     @RequestBody VotePayload votePayload) {
        Vote vote = proposalService.vote(id, votePayload);
        return ResponseEntity.ok().body(vote);
    }

    @ApiOperation(value = "Voting results on the proposal")
    @ApiResponses(value = @ApiResponse(code = 200, message = "Get voting results"))
    @GetMapping(value = V1_ENDPOINT + "/{id}/voting")
    public ResponseEntity<ProposalDTO> results(@ApiParam(value = "Id of proposal") @PathVariable String id) {
        ProposalDTO proposalDTO = proposalService.checkResults(id);
        return ResponseEntity.ok().body(proposalDTO);
    }
}

package com.edsandrof.softdesign.dto;

import com.edsandrof.softdesign.model.Proposal;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProposalDTO implements Serializable {
    private static final long serialVersionUID = 5965550609996985195L;

    @ApiModelProperty(value = "Proposal id")
    private String id;
    @ApiModelProperty(value = "Proposal description")
    private String description;
    @ApiModelProperty(value = "Closing date for voting")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date votingSessionClosingDate;
    @ApiModelProperty(value = "List of voting options")
    private List<String> votingOptions = new ArrayList<>();
    @ApiModelProperty(value = "Status of proposal")
    private Proposal.ProposalStatus status;
    @ApiModelProperty(value = "Results of proposal")
    private String results;

    public ProposalDTO() {
    }

    public ProposalDTO(Proposal proposal, String results) {
        this.id = proposal.getId();
        this.description = proposal.getDescription();
        this.votingSessionClosingDate = proposal.getVotingSessionClosingDate();
        this.status = proposal.getStatus();
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getVotingSessionClosingDate() {
        return votingSessionClosingDate;
    }

    public void setVotingSessionClosingDate(Date votingSessionClosingDate) {
        this.votingSessionClosingDate = votingSessionClosingDate;
    }

    public List<String> getVotingOptions() {
        return votingOptions;
    }

    public Proposal.ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(Proposal.ProposalStatus status) {
        this.status = status;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}

package com.edsandrof.softdesign.payload;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProposalPayload implements Serializable {
    private static final long serialVersionUID = -5995546082170405135L;

    @ApiModelProperty(value = "Proposal description")
    private String description;
    @ApiModelProperty(value = "List of voting options")
    private List<String> votingOptions = new ArrayList<>();

    public ProposalPayload() {
    }

    public ProposalPayload(String description, List<String> votingOptions) {
        this.description = description;
        this.votingOptions = votingOptions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getVotingOptions() {
        return votingOptions;
    }

    public void setVotingOptions(List<String> votingOptions) {
        this.votingOptions = votingOptions;
    }
}

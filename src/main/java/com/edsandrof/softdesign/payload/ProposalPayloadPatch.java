package com.edsandrof.softdesign.payload;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ProposalPayloadPatch implements Serializable {
    private static final long serialVersionUID = -2727570864267593350L;

    @ApiModelProperty(value = "Duration of opened voting session")
    private Integer votingSessionDuration;

    public ProposalPayloadPatch() {
    }

    public ProposalPayloadPatch(Integer votingSessionDuration) {
        this.votingSessionDuration = votingSessionDuration;
    }

    public Integer getVotingSessionDuration() {
        return votingSessionDuration;
    }

    public void setVotingSessionDuration(Integer votingSessionDuration) {
        this.votingSessionDuration = votingSessionDuration;
    }
}

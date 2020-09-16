package com.edsandrof.softdesign.payload;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class VotePayload implements Serializable {
    private static final long serialVersionUID = 698245814601463922L;

    @ApiModelProperty(value = "Member id")
    private String memberId;
    @ApiModelProperty(value = "Member voting option")
    private String votingOption;

    public VotePayload() {
    }

    public VotePayload(String memberId, String votingOption) {
        this.memberId = memberId;
        this.votingOption = votingOption;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getVotingOption() {
        return votingOption;
    }

    public void setVotingOption(String votingOption) {
        this.votingOption = votingOption;
    }
}

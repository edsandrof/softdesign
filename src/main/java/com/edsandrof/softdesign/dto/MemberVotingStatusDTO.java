package com.edsandrof.softdesign.dto;

import java.io.Serializable;

public class MemberVotingStatusDTO implements Serializable {
    private static final long serialVersionUID = 4574986251795919197L;

    public enum Status {
        ABLE_TO_VOTE,
        UNABLE_TO_VOTE
    }

    private Status status;

    public MemberVotingStatusDTO() {
    }

    public MemberVotingStatusDTO(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

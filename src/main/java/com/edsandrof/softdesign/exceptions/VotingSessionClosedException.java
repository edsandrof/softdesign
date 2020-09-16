package com.edsandrof.softdesign.exceptions;

public class VotingSessionClosedException extends RuntimeException {
    private static final long serialVersionUID = -6456119370336742756L;

    public VotingSessionClosedException(Object id) {
        super("Proposal " + id + " is closed for voting");
    }
}

package com.edsandrof.softdesign.exceptions;

public class VotingSessionOpenedException extends RuntimeException {
    private static final long serialVersionUID = -6456119370336742756L;

    public VotingSessionOpenedException(Object id) {
        super("Proposal " + id + " has already been opened for voting");
    }
}

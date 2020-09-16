package com.edsandrof.softdesign.exceptions;

public class VotingSessionNotOpenedException extends RuntimeException {
    private static final long serialVersionUID = -497507986129700196L;

    public VotingSessionNotOpenedException(Object id) {
        super("Voting session for proposal " + id + " was not opened");
    }
}

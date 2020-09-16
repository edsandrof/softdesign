package com.edsandrof.softdesign.exceptions;

public class VotingSessionAlreadyVotedException extends RuntimeException {
    private static final long serialVersionUID = -6456119370336742756L;

    public VotingSessionAlreadyVotedException(Object id) {
        super("You already voted for this proposal " + id);
    }
}

package com.edsandrof.softdesign.exceptions;

public class VotingSessionWrongOptionException extends RuntimeException {
    private static final long serialVersionUID = -6592210997856333306L;

    public VotingSessionWrongOptionException(Object option) {
        super("This proposal does not have the option to vote '" + option + "'");
    }
}

package com.edsandrof.softdesign.exceptions;

public class MemberCannotVoteException extends RuntimeException {
    public MemberCannotVoteException(Object cpf) {
        super("Member " + cpf + " cannot vote in this proposal");
    }
}

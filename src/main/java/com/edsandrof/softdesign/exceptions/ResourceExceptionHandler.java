package com.edsandrof.softdesign.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(VotingSessionOpenedException.class)
    public ResponseEntity<StandardError> votingSessionOpened(VotingSessionOpenedException e, HttpServletRequest request) {
        String error = "Voting session already opened";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(VotingSessionClosedException.class)
    public ResponseEntity<StandardError> votingSessionClosed(VotingSessionClosedException e, HttpServletRequest request) {
        String error = "Voting session was closed";
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(VotingSessionAlreadyVotedException.class)
    public ResponseEntity<StandardError> votingSessionAlreadyVoted(VotingSessionAlreadyVotedException e, HttpServletRequest request) {
        String error = "Already voted";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(VotingSessionWrongOptionException.class)
    public ResponseEntity<StandardError> votingSessionWrongOption(VotingSessionWrongOptionException e, HttpServletRequest request) {
        String error = "Wrong option";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MemberCannotVoteException.class)
    public ResponseEntity<StandardError> memberCannotVote(MemberCannotVoteException e, HttpServletRequest request) {
        String error = "Member cannot vote";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}

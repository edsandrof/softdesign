package com.edsandrof.softdesign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Proposal implements Serializable {
    private static final long serialVersionUID = -6841268201061177337L;
    private static final int DEFAULT_SESSION_DURATION = 1;

    @ApiModelProperty(value = "Proposal id")
    @Id
    private String id;
    @ApiModelProperty(value = "Proposal description")
    private String description;
    @ApiModelProperty(value = "Proposal creation date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date creationDate;
    @ApiModelProperty(value = "Opening date for voting")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date votingSessionOpeningDate;
    @ApiModelProperty(value = "Closing date for voting")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date votingSessionClosingDate;
    @ApiModelProperty(value = "List of voting options")
    private List<String> votingOptions = new ArrayList<>();
    @JsonIgnore
    private boolean votingSessionOpen;
    @ApiModelProperty(value = "Duration of opened voting session (in minutes)")
    private Integer votingSessionDuration;
    @ApiModelProperty(value = "Votes of members")
    private List<Vote> votingSession = new ArrayList<>();

    public Proposal() {
    }

    public Proposal(String description, List<String> votingOptions) {
        this(null, description, new Date(), null, null, votingOptions, null);
    }

    public Proposal(String id, String description, Date creationDate, Date votingSessionOpeningDate, Date votingSessionClosingDate, List<String> votingOptions, Integer votingSessionDuration) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
        this.votingSessionOpeningDate = votingSessionOpeningDate;
        this.votingSessionClosingDate = votingSessionClosingDate;
        this.votingOptions = votingOptions;
        this.votingSessionDuration = votingSessionDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getVotingSessionOpeningDate() {
        return votingSessionOpeningDate;
    }

    public void setVotingSessionOpeningDate(Date votingSessionOpeningDate) {
        this.votingSessionOpeningDate = votingSessionOpeningDate;
    }

    public Date getVotingSessionClosingDate() {
        return votingSessionClosingDate;
    }

    public void setVotingSessionClosingDate(Date votingSessionClosingDate) {
        this.votingSessionClosingDate = votingSessionClosingDate;
    }

    public List<String> getVotingOptions() {
        return votingOptions;
    }

    public void setVotingOptions(List<String> votingOptions) {
        this.votingOptions = votingOptions;
    }

    public boolean isVotingSessionOpen() {
        return votingSessionOpen;
    }

    public void setVotingSessionOpen(boolean votingSessionOpen) {
        this.votingSessionOpen = votingSessionOpen;
    }

    public Integer getVotingSessionDuration() {
        return votingSessionDuration;
    }

    public void setVotingSessionDuration(Integer votingSessionDuration) {
        if (votingSessionDuration == null) {
            this.votingSessionDuration = DEFAULT_SESSION_DURATION;
        }
        this.votingSessionDuration = votingSessionDuration;
    }

    public List<Vote> getVotingSession() {
        return votingSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposal proposal = (Proposal) o;
        return id.equals(proposal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

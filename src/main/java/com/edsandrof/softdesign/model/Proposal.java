package com.edsandrof.softdesign.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Proposal implements Serializable {
    private static final long serialVersionUID = -6841268201061177337L;

    @ApiModelProperty(value = "Id of proposal")
    @Id
    private String id;
    @ApiModelProperty(value = "Proposal description")
    private String description;
    @ApiModelProperty(value = "Proposal creation date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date creationDate;
    @ApiModelProperty(value = "Opening date for voting")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date pollOpeningDate;
    @ApiModelProperty(value = "Closing date for voting")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date pollClosingDate;
    @ApiModelProperty(value = "List of voting options")
    private List<String> votingOptions = new ArrayList<>();

    public Proposal() {
    }

    public Proposal(String description, List<String> votingOptions) {
        this(description, new Date(), null, null, votingOptions);
    }

    public Proposal(String description, Date creationDate, Date pollOpeningDate, Date pollClosingDate, List<String> votingOptions) {
        this.description = description;
        this.creationDate = creationDate;
        this.pollOpeningDate = pollOpeningDate;
        this.pollClosingDate = pollClosingDate;
        this.votingOptions = votingOptions;
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

    public Date getPollOpeningDate() {
        return pollOpeningDate;
    }

    public void setPollOpeningDate(Date pollOpeningDate) {
        this.pollOpeningDate = pollOpeningDate;
    }

    public Date getPollClosingDate() {
        return pollClosingDate;
    }

    public void setPollClosingDate(Date pollClosingDate) {
        this.pollClosingDate = pollClosingDate;
    }

    public List<String> getVotingOptions() {
        return votingOptions;
    }

    public void setVotingOptions(List<String> votingOptions) {
        this.votingOptions = votingOptions;
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

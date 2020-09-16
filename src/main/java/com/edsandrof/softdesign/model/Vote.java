package com.edsandrof.softdesign.model;

import java.io.Serializable;
import java.util.Objects;

public class Vote implements Serializable {
    private static final long serialVersionUID = 4277570099618648961L;

    private Member member;
    private String votingOption;

    public Vote() {
    }

    public Vote(Member member, String votingOption) {
        this.member = member;
        this.votingOption = votingOption;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getVotingOption() {
        return votingOption;
    }

    public void setVotingOption(String votingOption) {
        this.votingOption = votingOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return member.equals(vote.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member);
    }
}

package com.edsandrof.softdesign.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document
public class Member implements Serializable {
    private static final long serialVersionUID = -3924054909795267521L;

    @ApiModelProperty(value = "Member id")
    @Id
    private String id;
    @ApiModelProperty(value = "Member full name")
    private String fullName;
    @ApiModelProperty(value = "Member CPF")
    private String cpf;

    public Member() {
    }

    public Member(String fullName, String cpf) {
        this(null, fullName, cpf);
    }

    public Member(String id, String fullName, String cpf) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

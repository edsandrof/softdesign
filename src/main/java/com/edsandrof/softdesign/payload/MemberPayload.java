package com.edsandrof.softdesign.payload;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class MemberPayload implements Serializable {
    private static final long serialVersionUID = -2723187496764448957L;

    @ApiModelProperty(value = "Member full name")
    private String fullName;
    @ApiModelProperty(value = "Member CPF")
    private String cpf;

    public MemberPayload() {
    }

    public MemberPayload(String fullName, String cpf) {
        this.fullName = fullName;
        this.cpf = cpf;
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
}

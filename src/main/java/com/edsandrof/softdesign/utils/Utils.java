package com.edsandrof.softdesign.utils;

import com.edsandrof.softdesign.dto.MemberVotingStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Utils {
    public static boolean canMemberVote(String cpf) {
        final String uri = "https://user-info.herokuapp.com/users/";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MemberVotingStatusDTO> response = restTemplate.getForEntity(uri + cpf, MemberVotingStatusDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Objects.requireNonNull(response.getBody()).getStatus().equals(MemberVotingStatusDTO.Status.ABLE_TO_VOTE);
        }
        return false;
    }
}

package com.edsandrof.softdesign.utils;

import com.edsandrof.softdesign.dto.MemberVotingStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Utils {
    public static boolean canMemberVote(String cpf) {
        try {
            final String uri = "https://user-info.herokuapp.com/users/";

            RestTemplate restTemplate = new RestTemplate();
            cpf = "00055562012";
            ResponseEntity<MemberVotingStatusDTO> response = restTemplate.getForEntity(uri + cpf, MemberVotingStatusDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return Objects.requireNonNull(response.getBody()).getStatus().equals(MemberVotingStatusDTO.Status.ABLE_TO_VOTE);
            }
            return false;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }
}

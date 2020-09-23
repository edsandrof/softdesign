package com.edsandrof.softdesign.unit.resources;

import com.edsandrof.softdesign.dto.ProposalDTO;
import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.model.Vote;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.payload.ProposalPayloadPatch;
import com.edsandrof.softdesign.payload.VotePayload;
import com.edsandrof.softdesign.resources.ProposalResource;
import com.edsandrof.softdesign.services.ProposalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@WebMvcTest(ProposalResource.class)
public class ProposalResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProposalService proposalService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @BeforeAll
    public static void setup() {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void shouldReturnProposals() throws Exception {
        Mockito.when(proposalService.findAll()).
                thenReturn(Arrays.asList(new Proposal("Description 1", Arrays.asList("Yes", "No"))));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/proposal"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("CREATED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].votingOptions[0]").value("Yes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].votingOptions[1]").value("No"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldRegisterProposal() throws Exception {
        Proposal proposal = new Proposal("2", "Description 2", sdf.parse("2020-10-20T10:00:00Z"),
                null, null, Arrays.asList("Yes", "No"), null, Proposal.ProposalStatus.CREATED);

        ProposalPayload proposalPayload = new ProposalPayload("Description 2", Arrays.asList("Yes", "No"));
        String body = (new ObjectMapper()).valueToTree(proposalPayload).toString();

        Mockito.when(proposalService.register(Mockito.any(ProposalPayload.class))).
                thenReturn(proposal);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/proposal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location", "http://localhost/api/v1/proposal/2"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldOpenVotingSession() throws Exception {
        Proposal proposal = new Proposal("3", "Description 3", sdf.parse("2020-10-20T10:00:00Z"),
                sdf.parse("2020-10-20T10:05:00Z"), sdf.parse("2020-10-20T10:10:00Z"), Arrays.asList("Yes", "No"), 5, Proposal.ProposalStatus.OPENED);

        ProposalPayloadPatch proposalPayloadPatch = new ProposalPayloadPatch(5);
        String body = (new ObjectMapper()).valueToTree(proposalPayloadPatch).toString();

        Mockito.when(proposalService.openVotingSession(Mockito.any(String.class), Mockito.any(ProposalPayloadPatch.class))).
                thenReturn(proposal);

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/proposal/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPENED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingOptions[0]").value("Yes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingOptions[1]").value("No"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingSessionOpeningDate").value("2020-10-20T10:05:00Z"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingSessionClosingDate").value("2020-10-20T10:10:00Z"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingSessionDuration").value("5"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldMemberVote() throws Exception {
        Vote vote = new Vote(new Member("123", "Full name", "01234567890"), "Yes");
        VotePayload votePayload = new VotePayload("123", "Yes");
        String body = (new ObjectMapper()).valueToTree(votePayload).toString();

        Mockito.when(proposalService.vote(Mockito.any(String.class), Mockito.any(VotePayload.class))).thenReturn(vote);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/proposal/4/voting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.id").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.fullName").value("Full name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.member.cpf").value("01234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingOption").value("Yes"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldCheckVotingResults() throws Exception {
        Proposal proposal = new Proposal("4", "Description 4", sdf.parse("2020-10-20T10:00:00Z"),
                sdf.parse("2020-10-20T10:05:00Z"), sdf.parse("2020-10-20T10:10:00Z"), Arrays.asList("Yes", "No"), 5, Proposal.ProposalStatus.CLOSED);

        ProposalDTO proposalDTO = new ProposalDTO(proposal, "Option 'Yes' 1 votes. Option 'No' 0 votes. Total of votes: 1");
        Mockito.when(proposalService.checkResults(Mockito.any(String.class))).thenReturn(proposalDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/proposal/4/voting"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CLOSED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingOptions[0]").value("Yes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.votingOptions[1]").value("No"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results").value("Option 'Yes' 1 votes. Option 'No' 0 votes. Total of votes: 1"))
                .andDo(MockMvcResultHandlers.print());
    }
}

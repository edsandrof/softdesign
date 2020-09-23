package com.edsandrof.softdesign.unit.services;

import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.model.Vote;
import com.edsandrof.softdesign.payload.ProposalPayload;
import com.edsandrof.softdesign.payload.ProposalPayloadPatch;
import com.edsandrof.softdesign.payload.VotePayload;
import com.edsandrof.softdesign.repository.MemberRepository;
import com.edsandrof.softdesign.repository.ProposalRepository;
import com.edsandrof.softdesign.services.MemberService;
import com.edsandrof.softdesign.services.ProposalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ExtendWith({MockitoExtension.class})
public class ProposalServiceTest {
    private static String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    @Mock
    private ProposalRepository proposalRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private ProposalService proposalService;

    @BeforeAll
    public static void setup() {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void shouldReturnProposals() throws Exception {
        List<Proposal> expectedProposals = new ArrayList<>();
        expectedProposals.add(new Proposal("Description 1", Arrays.asList("Yes", "No")));
        expectedProposals.add(new Proposal("Description 2", Arrays.asList("A", "B")));

        Mockito.when(proposalRepository.findAll()).thenReturn(expectedProposals);

        List<Proposal> actualProposals = proposalService.findAll();
        Assertions.assertEquals(expectedProposals, actualProposals);
        Mockito.verify(proposalRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldRegisterProposal() throws Exception {
        ProposalPayload proposalPayload = new ProposalPayload("Description 1", Arrays.asList("Yes", "No"));
        Proposal expectedProposal = new Proposal(proposalPayload.getDescription(), proposalPayload.getVotingOptions());

        Mockito.when(proposalRepository.save(Mockito.any(Proposal.class))).thenReturn(expectedProposal);
        Proposal actualProposal = proposalService.register(proposalPayload);

        Assertions.assertEquals(expectedProposal.getDescription(), actualProposal.getDescription());
        Assertions.assertEquals(expectedProposal.getVotingOptions(), actualProposal.getVotingOptions());
        Assertions.assertNotNull(actualProposal.getCreationDate());
        Assertions.assertEquals("CREATED", actualProposal.getStatus().name());
        Assertions.assertNull(actualProposal.getVotingSessionClosingDate());
        Assertions.assertNull(actualProposal.getVotingSessionOpeningDate());
        Assertions.assertNull(actualProposal.getVotingSessionDuration());
        Assertions.assertTrue(actualProposal.getVotingSession().isEmpty());

        Mockito.verify(proposalRepository, Mockito.times(1)).save(Mockito.any(Proposal.class));
    }

    @Test
    public void shouldOpenVotingSession() throws Exception {
        ProposalPayloadPatch proposalPayloadPatch = new ProposalPayloadPatch(5);
        Proposal proposal = new Proposal("2", "Description 2", sdf.parse("2020-10-20T10:00:00Z"),
                null, null, Arrays.asList("Yes", "No"), null, Proposal.ProposalStatus.CREATED);
        Optional<Proposal> expectedProposal = Optional.of(proposal);

        Mockito.when(proposalRepository.findById("2")).thenReturn(expectedProposal);
        Mockito.when(proposalRepository.save(Mockito.any(Proposal.class))).thenReturn(expectedProposal.get());

        Proposal actualProposal = proposalService.openVotingSession("2", proposalPayloadPatch);

        Assertions.assertEquals(expectedProposal.get().getDescription(), actualProposal.getDescription());
        Assertions.assertEquals(expectedProposal.get().getVotingOptions(), actualProposal.getVotingOptions());
        Assertions.assertEquals(5, actualProposal.getVotingSessionDuration());
        Assertions.assertEquals("2", actualProposal.getId());
        Assertions.assertEquals(
                DateTimeFormatter.ofPattern(pattern).format(ZonedDateTime.ofInstant(actualProposal.getVotingSessionOpeningDate().toInstant(),
                        ZoneId.of("UTC")).plusMinutes(5)), sdf.format(actualProposal.getVotingSessionClosingDate()));
        Assertions.assertEquals("OPENED", actualProposal.getStatus().name());

        Mockito.verify(proposalRepository, Mockito.times(1)).findById(Mockito.any(String.class));
        Mockito.verify(proposalRepository, Mockito.times(1)).save(Mockito.any(Proposal.class));
    }

    @Test
    public void shouldMemberVote() throws Exception {
        Proposal proposal = new Proposal("3", "Description 3", sdf.parse("2020-10-20T10:00:00Z"),
                sdf.parse("2020-10-20T10:05:00Z"), sdf.parse("2020-10-20T10:10:00Z"), Arrays.asList("Yes", "No"), 5, Proposal.ProposalStatus.OPENED);
        Optional<Proposal> expectedProposal = Optional.of(proposal);
        Optional<Member> expectedMember = Optional.of(new Member("1", "full name", "98895400704"));
        VotePayload votePayload = new VotePayload("1", "Yes");

        Mockito.when(proposalRepository.findById("3")).thenReturn(expectedProposal);
        Mockito.when(memberService.findById("1")).thenReturn(expectedMember.get());
        Mockito.when(proposalRepository.save(Mockito.any(Proposal.class))).thenReturn(expectedProposal.get());

        Vote actualVote = proposalService.vote("3", votePayload);
        Assertions.assertEquals("Yes", actualVote.getVotingOption());
        Assertions.assertEquals("1", actualVote.getMember().getId());

        Mockito.verify(memberService, Mockito.times(1)).findById(Mockito.any(String.class));
        Mockito.verify(proposalRepository, Mockito.times(1)).findById(Mockito.any(String.class));
        Mockito.verify(proposalRepository, Mockito.times(1)).save(Mockito.any(Proposal.class));
    }
}

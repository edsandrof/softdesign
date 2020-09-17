package com.edsandrof.softdesign.config;

import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.repository.MemberRepository;
import com.edsandrof.softdesign.repository.ProposalRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> votingOptions = new ArrayList<>(Arrays.asList("Sim", "Não"));
        Proposal proposal = new Proposal("Destinar resultados para ação ABC", votingOptions);
        proposalRepository.deleteAll();
        memberRepository.deleteAll();

        List<Member> members = new ArrayList<>();
        List<String> cpfs = new ArrayList<>(Arrays.asList("46780128260", "79115237206", "28136579130"));
        Faker faker = new Faker();
        for (int i = 0; i < 3; i++) {
            Member member = new Member(String.valueOf(faker.number().randomNumber()), faker.name().fullName(), cpfs.get(i));
            members.add(member);
        }

        memberRepository.saveAll(members);
        /*Vote v1 = new Vote(members.get(0), "Sim");
        Vote v2 = new Vote(members.get(1), "Não");
        Vote v3 = new Vote(members.get(2), "Sim");
        proposal.getVotingSession().addAll(Arrays.asList(v1, v2, v3));
         */
        proposalRepository.save(proposal);
    }
}

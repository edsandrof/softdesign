package com.edsandrof.softdesign.config;

import com.edsandrof.softdesign.model.Proposal;
import com.edsandrof.softdesign.repository.ProposalRepository;
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

    @Override
    public void run(String... args) throws Exception {
        List<String> votingOptions = new ArrayList<>(Arrays.asList("Sim", "Não"));
        Proposal proposal = new Proposal("Destinar resultados para ação ABC", votingOptions);
        proposalRepository.deleteAll();
        proposalRepository.save(proposal);
    }
}

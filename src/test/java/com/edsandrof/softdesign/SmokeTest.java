package com.edsandrof.softdesign;

import com.edsandrof.softdesign.resources.MemberResource;
import com.edsandrof.softdesign.resources.ProposalResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private ProposalResource proposalResource;
    @Autowired
    private MemberResource memberResource;

    @Test
    public void proposalContextLoads() throws Exception {
        Assertions.assertThat(proposalResource).isNotNull();
    }

    @Test
    public void memberContextLoads() throws Exception {
        Assertions.assertThat(memberResource).isNotNull();
    }
}

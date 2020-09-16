package com.edsandrof.softdesign.services;

import com.edsandrof.softdesign.exceptions.ResourceNotFoundException;
import com.edsandrof.softdesign.model.Member;
import com.edsandrof.softdesign.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member findById(String id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}

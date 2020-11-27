package com.tae.tae.service;

import com.tae.tae.dto.member.Member;
import com.tae.tae.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findOne(String id) throws NoSuchElementException {
        return memberRepository.findById(id).get();
    }
}

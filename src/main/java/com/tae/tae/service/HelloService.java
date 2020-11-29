package com.tae.tae.service;

import com.tae.tae.dto.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class HelloService {

    @PersistenceContext
    EntityManager em;

    @Autowired Repository1 repository1;
    @Autowired Repository2 repository2;


    @Transactional
    public Member logic() {
        repository1.hello();

        // member는 영속 상태다.
        Member member = repository2.findMember();
        return member;
    }
}

@Repository
class Repository1 {

    @PersistenceContext
    EntityManager em;

    public void hello() {
        //em.xxx()
    }
}

@Repository
class Repository2 {

    @PersistenceContext
    EntityManager em;

    public Member findMember() {
        return em.find(Member.class, "id1");
    }
}
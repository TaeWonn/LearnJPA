package com.tae.tae.jpa.proxy;

import com.mysema.commons.lang.Assert;
import com.tae.tae.dto.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProxyTest {

    @Autowired
    private TestEntityManager tem;

    private EntityManager em;

    @BeforeEach
    void setup() {
        em = tem.getEntityManager();
    }

    @Test
    public void 영속성컨텍스트와_프록시() {

//        Member newMember = new Member();
//        newMember.setId("member1");
//        newMember.setName("회원1");
//
//        em.persist(newMember);
//        em.flush();
//        em.clear();

        Member refMember = em.getReference(Member.class, "member1");
        Member findMember = em.find(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());

        Assertions.assertTrue(refMember == findMember); //성공
    }
}

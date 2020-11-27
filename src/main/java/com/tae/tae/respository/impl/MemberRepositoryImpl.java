package com.tae.tae.respository.impl;

import com.mysema.query.jpa.impl.JPAQuery;
import com.tae.tae.dto.member.Member;
import com.tae.tae.respository.custom.MemberRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.tae.tae.dto.member.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        JPAQuery query = new JPAQuery(em);

        return query.from(member)
                .orderBy(member.name.desc())
                .list(member);
    }
}

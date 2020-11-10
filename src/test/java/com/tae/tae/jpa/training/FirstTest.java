package com.tae.tae.jpa.training;

import com.querydsl.jpa.impl.JPAQuery;
import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.member.QMember;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FirstTest {

    @Autowired
    private TestEntityManager em;



    @Test
    public void JPQL사용(){
        String jpql = "select m from Member as m where m.name = '회원5'";
        List<Member> resultList =
                em.getEntityManager()
                    .createQuery(jpql, Member.class)
                    .getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void Criteria쿼리(){
        //Criteria 사용 준비
        CriteriaBuilder cb = em.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        //루트 클래스 (조회를 시작할 클래스)
        Root<Member> m = query.from(Member.class);

        //쿼리생성
        CriteriaQuery<Member> cq =
                query.select(m)
                    .where(cb.equal(m.get("name"),"회원5"));
        List<Member> resultList = em.getEntityManager()
                                        .createQuery(cq)
                                        .getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void queryDSL(){
        JPAQuery query = new JPAQuery(em.getEntityManager());
        QMember member = QMember.member;

        //쿼리 결과 조회
        List<Member> members =query.from(member)
                .select(member).fetch();
        members.forEach(System.out::println);
    }

    @Test
    public void 하이버네이트_JDBC_획득(){
        Session session = em.getEntityManager().unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                //work...
            }
        });
    }


}

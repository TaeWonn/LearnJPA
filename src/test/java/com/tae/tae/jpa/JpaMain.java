package com.tae.tae.jpa;

import com.tae.tae.member.vo.Member;
import com.tae.tae.team.vo.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

//@SpringBootTest
@DataJpaTest
public class JpaMain {

    @Test
    void contextLoads(){
        // [엔티티 매니저 팩토리] - 생성
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabook");
        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();
        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();
        
        try {
            
            tx.begin();     //[트랜잭션] - 시작
            //logic(em);      //비즈니스 로직 실행
            //testSave(em);
            //queryLogicJoin(em);
            //updateRelation(em);
            //deleteRelation(em);
            biDirection(em);
            tx.commit();    //[트랜잭션 커밋]
            
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
    
    // 비즈니스 로직
    void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setName("tae");
        //member.setAge(2);

        //등록
        em.persist(member);

        //수정
        //member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember= "+ findMember.getName());

        //목록 조회

        List<Member> members =
            em.createQuery("select m from Member m",Member.class)
                .getResultList();
        System.out.println("member.size=" + members.size());

        //삭제
        em.remove(member);
    }

    public void testSave(EntityManager em) {

        // 팀1 저장
        Team team1 = new Team("team1","팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1","회원1");
        member1.setTeam(team1); //연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2","회원2");
        member2.setTeam(team1); //연관관계 설정 member1 -> team1
        em.persist(member2);
        //em.flush();
    }

    void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where " +
                    "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName","팀1")
                .getResultList();

        for(Member member : resultList) {
            System.out.println("[query] member.username=" +
                member.getName());
        }
    }

    void updateRelation(EntityManager em) {

        //새로운 팀2
        Team team2 = new Team("team2","팀2");
        em.persist(team2);

        //회원1에 새로운 팀2 설정
        Member member = em.find(Member.class,"member1");
        member.setTeam(team2);
    }

    void deleteRelation(EntityManager em) {

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    void biDirection(EntityManager em) {

        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();

        System.out.println("==결과==");
        for (Member member : members) {
            System.out.println("member.username = " +
                    member.getName());
        }
    }

}

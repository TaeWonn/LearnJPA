package com.tae.tae.jpa;

import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.member.Team;
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

    private EntityManagerFactory emf;

    public EntityManager getEntityMnager() {
        // [엔티티 매니저 팩토리] - 생성
        emf = Persistence.createEntityManagerFactory("jpabook");
        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();

        return em;
    }

    @Test
    void contextLoads(){
        EntityManager em = getEntityMnager();
        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();
        
        try {
            
            tx.begin();     //[트랜잭션] - 시작
            //logic(em);      //비즈니스 로직 실행
            //testSave(em);
            //queryLogicJoin(em);
            //updateRelation(em);
            //deleteRelation(em);
            //biDirection(em);
            //test순순한객체_양방향();
            //test순수한객체_양방향2();
            //testORM_양방향(em);
            일대다단방향save(em);
            find(em);
            tx.commit();    //[트랜잭션 커밋]
            
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
    
    // 비즈니스 로직
    void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        //member.setId(id);
        //member.setName("tae");
        //member.setAge(2);

        //등록
        em.persist(member);

        //수정
        //member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        //System.out.println("findMember= "+ findMember.getName());

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
        //Member member1 = new Member("member1","회원1");
        //member1.setTeam(team1); //연관관계 설정 member1 -> team1
       // em.persist(member1);

        // 회원2 저장
//        Member member2 = new Member("member2","회원2");
//        member2.setTeam(team1); //연관관계 설정 member1 -> team1
//        em.persist(member2);
        //em.flush();
    }

    void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where " +
                    "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName","팀1")
                .getResultList();

        for(Member member : resultList) {
            //System.out.println("[query] member.username=" +
                //member.getName());
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
            //System.out.println("member.username = " +
             //       member.getName());
        }
    }

    public void testSaveNonOwner(EntityManager em) {

        //회원1 저장
        //Member member1 = new Member("member1","회원1");
        //em.persist(member1);

        //회원2 저장
//        Member member2 = new Member("member2","회원2");
//        em.persist(member2);

        Team team1 = new Team("team1","팀1");
//        team1.getMembers().add(member1);
//        team1.getMembers().add(member2);

        em.persist(team1);
    }

    void test순순한객체_양방향() {

        //팀1
        Team team1 = new Team("team1","팀1");
//        Member member1 = new Member("member1","회원1");
//        Member member2 = new Member("member2","회원2");

//        member1.setTeam(team1);
//        member2.setTeam(team1);

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());
        //결과 : members.siz = 0
    }

    void test순수한객체_양방향2() {

        //팀1
        Team team1 = new Team("team1","팀1");
//        Member member1 = new Member("member1","회원1");
//        Member member2 = new Member("member2","회원2");

//        member1.setTeam(team1);           //연관관계 설정 member1 -> team1
//        team1.getMembers().add(member1);  //연관관계 설정 team1 -> member1
//
//        member2.setTeam(team1);           //연관관계 설정 member2 -> team1
//        team1.getMembers().add(member2);  //연관관계 설정 team1 -> member2

        List<Member> members = team1.getMembers();
        System.out.println("members.size = " + members.size());
    }

    void testORM_양방향(EntityManager em) {

        //팀1 저장
        Team team1 = new Team("team1","팀1");
        em.persist(team1);

//        Member member1 = new Member("member1","회원1");

        //양방향 연관관계 설정
//        member1.setTeam(team1);             //연관관계 설정 member1 -> team1
//        em.persist(member1);

//        Member member2 = new Member("member2","회원2");

        //양방향 연관관계 설정
//        member2.setTeam(team1);             //연관관계 설정 member2 -> team1
//        em.persist(member2);

        //==기존 코드 삭제==start//
//        team1.getMembers().add(member1);    //연관관계 설정 team1 -> member1
//        team1.getMembers().add(member2);    //연관관계 설정 team1 -> member2
        //==기존 코드 삭제==end//
    }

    public void 일대다단방향save(EntityManager em) {

        Member member1 = new Member();
        Member member2 = new Member();

        member1.setId("member1");
        member1.setName("회원1");
        member2.setId("member2");
        member2.setName("회원2");

        Team team1 = new Team("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(team1);
        em.persist(member1); //INSERT-member1
        em.persist(member2); //INSERT-member2
        //em.persist(team1);   //INSERT-team1,
                             //UPDATE-member1.fk
                             //UPDATE-member2.fk

        //transaction.commit();
    }

    @Test
    public void find(EntityManager em) {

        Member member = em.find(Member.class,"member1");
        //List<Product> products = member.getProducts(); //연관관계 설정
//        for(Product product : products){
//            System.out.println("products.name = " + product.getName());
//        }
    }
}

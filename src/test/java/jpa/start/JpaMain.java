package jpa.start;

import com.tae.member.vo.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args){
        // [엔티티 매니저 팩토리] - 생성
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("jpabook");
        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();
        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();
        
        try {
            
            tx.begin();     //[트랜잭션] - 시작
            logic(em);      //비즈니스 로직 실행
            tx.commit();    //[트랜잭션 커밋]
            
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
    
    // 비즈니스 로직
    private static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setName("tae");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember= "+ findMember.getName()
                    +" age= "+findMember.getAge());

        //목록 조회

        List<Member> members =
            em.createQuery("select m from Member m",Member.class)
                .getResultList();
        System.out.println("member.size=" + members.size());

        //삭제
        em.remove(member);
    }
}

package com.tae.tae.jpa.training.jqpl;

import com.tae.tae.dto.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JPQLTest {

    @Autowired
    private TestEntityManager em;

    @Test
    public void typeQuery(){
        TypedQuery<Member> query =
                em.getEntityManager()
                .createQuery("SELECT m FROM Member m", Member.class);

        List<Member> resultList = query.getResultList();
        resultList.forEach(i -> System.out.println(i.getName()));
    }

    @Test
    public void query예제 (){
        Query query =
                em.getEntityManager()
                .createQuery("SELECT m.name, m.id from Member m");

        query.getResultList()
                .forEach(i -> {
                    Object[] o = (Object[]) i;
                    System.out.println("name = " + o[0]);
                    System.out.println("id = " + o[1]);
                });
    }

    @Test
    public void 이름_기준_파라미터_사용 () {
        String usernameParam = "회원5";
        TypedQuery query =
                em.getEntityManager()
                .createQuery("SELECT m FROM Member m where m.name = :name",Member.class);

        query.setParameter("name",usernameParam);
        query.getResultList()
                .forEach(System.out::println);
    }

    @Test
    public void 위치_기준_파라미터_사용 () {
        List<Member> members =
                em.getEntityManager()
                .createQuery("select m from Member m where m.name = ?1",Member.class)
                .setParameter(1, "회원5")
                .getResultList();
        members.forEach(System.out::println);
    }

    @Test
    public void 여러_프로젝션(){
        Query query =
                em.getEntityManager()
                .createQuery("select m.name, m.id from Member m");
        List resultList = query.getResultList();

        Iterator it = resultList.iterator();
        while(it.hasNext()){
            Object[] row = (Object[]) it.next();
            String name = (String) row[0];
            String od = (String) row[1];
        }
    }

    @Test
    public void TypedQuery_페이징() {
        TypedQuery query =
                em.getEntityManager()
                    .createQuery("select m from Member m order by m.name desc",Member.class);

        query.setFirstResult(1);
        query.setMaxResults(10);
        query.getResultList();

    }

    @Test
    public void groupByHaving(){
        List<Object[]> resultList =
                em.getEntityManager()
                .createQuery("select t.name ,count(m.age), sum(m.age), avg(m.age) " +
                        "from Member m LEFT join  m.team t " +
                        "group by t.name " +
                        "having avg(m.age) >= 10")
                    .getResultList();
    }

    @Test
    public void jqplJoin() {
        String teamName = "팀A";
        String query = "SELECT m FROM Member m INNER JOIN m.team t " +
                "WHERE t.name = :name";

        List<Member> members = em.getEntityManager()
                    .createQuery(query, Member.class)
                    .setParameter("name", teamName)
                    .getResultList();
    }


}

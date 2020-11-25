package com.tae.tae.jpa.training;

import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.member.Team;
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
    void typeQuery(){
        TypedQuery<Member> query =
                em.getEntityManager()
                .createQuery("SELECT m FROM Member m", Member.class);

        List<Member> resultList = query.getResultList();
        resultList.forEach(i -> System.out.println(i.getName()));
    }

    @Test
    void query예제 (){
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
    void 이름_기준_파라미터_사용 () {
        String usernameParam = "회원5";
        TypedQuery query =
                em.getEntityManager()
                .createQuery("SELECT m FROM Member m where m.name = :name",Member.class);

        query.setParameter("name",usernameParam);
        query.getResultList()
                .forEach(System.out::println);
    }

    @Test
    void 위치_기준_파라미터_사용 () {
        List<Member> members =
                em.getEntityManager()
                .createQuery("select m from Member m where m.name = ?1",Member.class)
                .setParameter(1, "회원5")
                .getResultList();
        members.forEach(System.out::println);
    }

    @Test
    void 여러_프로젝션(){
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
    void TypedQuery_페이징() {
        TypedQuery query =
                em.getEntityManager()
                    .createQuery("select m from Member m order by m.name desc",Member.class);

        query.setFirstResult(1);
        query.setMaxResults(10);
        query.getResultList();

    }

    @Test
    void groupByHaving(){
        List<Object[]> resultList =
                em.getEntityManager()
                    .createQuery("select t.name ,count(m.age), sum(m.age), avg(m.age) " +
                            "from Member m LEFT join  m.team t " +
                            "group by t.name " +
                            "having avg(m.age) >= 10")
                    .getResultList();
    }

    @Test
    void jqplJoin() {
        String teamName = "팀A";
        String query = "SELECT m FROM Member m INNER JOIN m.team t " +
                "WHERE t.name = :name";

        List<Member> members =
                em.getEntityManager()
                    .createQuery(query, Member.class)
                    .setParameter("name", teamName)
                    .getResultList();
    }

    @Test
    void outerJoin() {
        String query = """
                SELECT m
                FROM 
                    Member m LEFT OUTER JOIN m.team t
                """;

        List<Member> members =
                em.getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    @Test
    void setterJoin() {
        String query = """
                select count(m) 
                from Member m, Team t
                where m.name = t.name
                """;

        List<Member> members =
                em.getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    @Test
    void fetchJoin() {
        String query = """
                select m,t 
                from Member m
                    left join m.team t on t.name= 'A' 
                """;
        List<Member> members =
                em.getEntityManager()
                .createQuery(query)
                .getResultList();


    }

    @Test
    void fetchJoinTeam () {
        String jpql = """
                select 
                        t 
                from 
                    Team t join fetch t.members 
                where t.name = '팀A'
                """;

        List<Team> teams =
                em.getEntityManager()
                .createQuery(jpql, Team.class)
                .getResultList();

        for(Team team : teams) {

            System.out.println("teamName = " + team.getName() +", team = "+ team);

            for(Member member : team.getMembers()) {

                //패치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
                System.out.println("->username = " + member.getName()+ ", member = " + member);
            }
        }
    }

    @Test
    void fetchJoin_distinct () {
        String query = """
                select distinct t
                from Team t join fetch t.members
                where t.name = '팀A'
                """;

        List<Team> teams =
                em.getEntityManager()
                .createQuery(query)
                .getResultList();
        /*
            fetch join과 일반 join의 차이
            일반 조인했을 때 회원 컬렉션도 함께 조회할 것으로 기대해선 안 된다.
            JPQL은 결과를 반환할 때 연관관계 까지 고려하지 않는다.
            단지 SELECT 절에 지정한 엔티티만 조회할 뿐이다.
        */
    }

    /*
        fetch 조인의 특징과 한계

        최적화를 위해 글로벌 로딩 전략을 즉시 로딩을 설정하면
        애플리케이션 전체에서 항상 즉시 로딩이 일어난다.

        물론 일부는 빠를 수는 있지만 전체로 보면 사용하지 않는
        엔티티를 자주 로딩하므로 오히려 성능에 악영향을 미칠 수 있다.

        따라서 글로벌 로딩 전략은 될 수 있으면 지연 로딩을 사용하고
        최적화가 필요하면 페치 조인을 적용하는 것이 효과적이다.

        ㅁ 페치 조인 대상에는 별칭을 줄 수 없다.
        JPA 표준에서는 지원하지 않지만 하이버네트를 포함한 몇몇 구현체들은
        페치 조인에 별칭을 지원한다.
        하지만 별칭을 잘못 사용하면 연관된 데이터 수가 달라져서
        데이터 무결성이 깨질 수 있으므로 조심해서 사용해야 한다.
        특히 2차 캐시와 함께 사용될 때 조심해야 하는데,
        연관된 데이터 수가 달라진 상태에서 2차 캐시에 저장도;면
        다른 곳에서 조회할 때도 연관된 데이터 수가 달라지는 문제가 발생 할 수 있다.

        ㅁ 둘 이상이 컬렉션을 페치할 수 없다.
        구현체에 따라 되기도 하는데 컬렉션 * 컬렉션의
        카테시안 곱이 만들어지므로 주의해야 한다.

        ㅁ 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults) 를 사용할 수 없다.
        컬렉션이 아닌 단일 값 연관필드들은 페치 조인을 사용해도 페이징 API를 사용할 수 있다.
        하이버네이트에서 컬렉션을 페치 조인하고 페이징 API를 사용하면 경고 로그를 남기면서
        메모리에서 페이징 처리를 한다.
        데이터가 적으면 상관없곗지만 데이터가 많으면 성능 이슈와 메모리 초과 예외가 발생할 수 있어서 위험하다.
    */

    /* 경로 표현식 */


    @Test
    // 경로 표현식은 쉽게 이야기해서 .을 찍어 객체 그래프를 탐색하는 것이다.
    // 여기서 m.name, m.team, m.orders 모두 경로 표현식을 사용한다.
    void 경로표현식 () {
        String query = """
                select m.name
                from Member m
                    join m.team t
                    join m.orders o
                where t.name = '팀A'
                """;

        List<Member> results =
                em.getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    /*
        경로 표현식의 용어 정리

        ㅁ 상태 필드 state field
        단순히 값을 저장하기위한 필드

        ㅁ 연관 필드 association field
        연관관계를 위한 필드 , 임베디드 타입 포함

        - 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티
        - 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션
    */



    /* Named 쿼리: 정적 쿼리 */
    @Test
    void namedQuery() {
        List<Member> resultList =
                em.getEntityManager()
                .createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "회원5")
                .getResultList();
    }

}

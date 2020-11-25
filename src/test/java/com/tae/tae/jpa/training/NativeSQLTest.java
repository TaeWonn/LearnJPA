package com.tae.tae.jpa.training;

import com.tae.tae.dto.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NativeSQLTest {

    @Autowired
    private TestEntityManager em;

    @Test
    void 엔티티_조회_코드 () {
        String sql = """
                SELECT  
                    MEMBER_ID
                    ,CREATE_DATE
                    ,UPDATE_DATE
                    ,COMPANY_CITY
                    ,COMPANY_STATE
                    ,COMPANY_STREET
                    ,COMPANY_ZIPCODE
                    ,CITY
                    ,STATE
                    ,STREET
                    ,ZIPCODE
                    ,USERNAME
                    ,AREA_CODE
                    ,LOCAL_NUMBER
                    ,LOCKER_ID
                    ,PROVIER_NAME
                    ,TEAM_ID 
                    ,AGE
                FROM 
                    MEMBER 
                WHERE AGE > ?
                """;

        em.getEntityManager()
                .createNativeQuery(sql, Member.class)
                .setParameter(1, 20)
                .getResultList()
                .forEach(i -> ((Member)i).getName());
    }

    @Test
    void 값_조회() {
        String sql = """
                SELECT MEMBER_ID, AGE, USERNAME, TEAM_ID
                FROM MEMBER 
                WHERE AGE > ?
                """;
        javax.persistence.Query nativeQuery =
                em.getEntityManager()
                .createNativeQuery(sql)
                .setParameter(1, 10);

        List<Object[]> resultList = nativeQuery.getResultList();
        for (Object[] row : resultList) {
            System.out.println("id = "+row[0]);
            System.out.println("ag = "+row[1]);
            System.out.println("name = "+row[2]);
            System.out.println("team_id = "+row[3]);
        }
    }

    @Test
    void 결과_매핑_사용() {
        String sql = """
                SELECT M.*, I.ORDER_COUNT
                FROM MEMBER M
                LEFT JOIN 
                    (SELECT IM.MEMBER_ID, COUNT(*) AS ORDER_COUNT
                     FROM ORDERS O, MEMBER IM 
                     WHERE O.MEMBER_ID = IM.MEMBER_ID) I
                ON M.MEMBER_ID = I.MEMBER_ID
                """;
        Query nativeQuery =
                em.getEntityManager()
                .createNativeQuery(sql, "memberWithOrderCount");

        List<Object[]> resultList = nativeQuery.getResultList();
        for (Object[] row : resultList) {
            Member member = (Member) row[0];
            BigInteger orderCount = (BigInteger)row[1];

            System.out.println("member = " + member);
            System.out.println("orderCount = " + orderCount);
        }
    }

    @Test
    void 표준_명시_예제() {
        String sql = """
                SELECT 
                    O.*, I.NAME
                FROM ORDERS O, ITEM I
                WHERE
                    ORDER_AMOUNT > 25
                    AND ITEM_ID = I.ITEM_ID
                """;
        Query q =
                em.getEntityManager()
                .createNativeQuery(sql,"OrderResults");

        q.getResultList()
                .forEach(System.out::println);
    }

}

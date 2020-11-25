package com.tae.tae.jpa.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeepeningQueryTest {

    @Autowired
    private TestEntityManager em;


    /* 벌크 연산 */
    /*
        ㅁ 벌크 연산의 주의점
        벌크 연산을 사용할 떄는 벌크 연산이 영속성 컨텍스트를 무시하고
        데이터베이스에 직접 퀄한다는 점에 주의해야 한다.
        
        이러한 문제를 대체하는 3가지 방법이 있다.
        - em.refresh() 사용
        - 벌크 연산 먼저 실행
        - 벌크 연산 수행 후 영속성 컨텍스트 초기화

        벌크 연산은 영속성 컨텍스트와 2차 캐시를 무시하고 데이터베이스에
        직접 실행한다.
        따라서 영속성 컨텍스트와 데이터베이스 간에 데이터 차이가 발생할 수 있으므로
        주의해서 사용해야 한다.
        가능하면 벌크 연산을 가장 먼저 수행하는 것이 좋고
        상황에 따라 영속성 컨텍스트를 초기화하는 것도 필요하다.
        
    */
    @Test
    void UPDATE_벌크_연산 () {
        String sql ="""
                update Product p 
                set p.price = p.price * 1.1
                where p.stockAmount < :stockAmount
                """;

        int resultCount =
                em.getEntityManager()
                .createQuery(sql)
                .setParameter("stockAmount", 10)
                .executeUpdate();
    }

    @Test
    void DELETE_벌크_연산 () {
        String sql = """
                delete from Product p
                where p.price < :price
                """;

        int resultCount =
                em.getEntityManager()
                .createQuery(sql)
                .setParameter("price", 100)
                .executeUpdate();
    }



}

package com.tae.tae.jpa.training;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import com.tae.tae.dto.item.Item;
import com.tae.tae.dto.item.QItem;
import com.tae.tae.dto.item.more.ItemDTO;
import com.tae.tae.dto.item.more.SearchParam;
import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.member.QMember;
import com.tae.tae.dto.order.QOrder;
import com.tae.tae.dto.order.QOrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.tae.tae.dto.member.QMember.member; //기본 인스턴스 사용
import static com.tae.tae.dto.item.QItem.item;
import static com.tae.tae.dto.order.QOrder.order;
import static com.tae.tae.dto.order.QOrderItem.orderItem;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QueryDslTest {

    @Autowired
    private TestEntityManager em;

    private JPAQuery query;

    @BeforeEach
    void setup() {
        query = new JPAQuery(em.getEntityManager());
    }

    @Test
    void queryDSL(){
        JPAQuery query = new JPAQuery(em.getEntityManager());
        QMember member = new QMember("m");  // 별칭 직접 지정
        //QMember member = QMember.member;  //기본 인스턴스 사용

        //쿼리 결과 조회
        List<Member> members =
                query.from(member)
                        .where(member.name.eq("회원5"))
                        .orderBy(member.name.desc())
                        .list(member);

        members.forEach(System.out::println);
    }

    @Test
    void 기본쿼리기능() {
        JPAQuery query = new JPAQuery(em.getEntityManager());
        QItem item = QItem.item;

        List<Item> list =
                query.from(item)
                        .where(item.name.eq("회원5").and(item.price.gt(20000)))
                        .list(item);
        /*
            item.price.between(10000,20000)  // 가격이 10000 ~ 20000원 상품
            item.name.contains("상품1")       // 상품1이라는 이름을 포함한 상품,
                                             // SQL에서 like '%상품1%' 검색
            item.name.startWith("고급")       // 이름이 고급으로 시작하는 상품,
                                             // SQL에서 like '고급%' 검색
        */

    }

    /*  paging start  */
    @Test
    void 페이징과정렬() {
        JPAQuery query = new JPAQuery(em.getEntityManager());
        QItem item = QItem.item;

        query.from(item)
                .where(item.price.gt(20000))
                .orderBy(item.price.desc(), item.stockQuantity.asc())
                .offset(1).limit(10)
                .list(item);
    }

    @Test
    void 페이징과정렬QueryModifrers사용() {
        JPAQuery query = new JPAQuery(em.getEntityManager());
        QueryModifiers queryModifiers = new QueryModifiers(20L, 10L); //limit, offset

        List<Item> list =
                query.from(item)
                        .restrict(queryModifiers)
                        .list(item);
    }

    @Test
    void 페이징과정렬listResults사용 () {
        JPAQuery query = new JPAQuery(em.getEntityManager());

        SearchResults<Item> result =
                query.from(item)
                        .where(item.price.gt(10000))
                        .offset(1).limit(10)
                        .listResults(item);
    }
    /*  paging end  */

    /*  groupBy start  */
    @Test
    void groupBy사용 () {
        List<Item> result =
                query.from(item)
                        .groupBy(item.price)
                        .having(item.price.gt(1000))
                        .list(item);
    }
    /*  groupBy end  */

    /*  join start  */
    @Test
    void 기본조인() {
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        query.from(order)
                .join(order.member, member)
                .leftJoin(order.orderItems, orderItem)
                .list(order);
    }

    @Test
    void 조인ON사용() {
        // On 사용
        query.from(order)
                .leftJoin(order.orderItems, orderItem)
                .on(orderItem.count.gt(2))
                .list(order);
    }

    @Test
    void 패치조인사용() {
        query.from(order)
                .innerJoin(order.member, member).fetch()
                .leftJoin(order.orderItems, orderItem).fetch()
                .list(order);
    }

    @Test
    void from절에_여러조건_사용() {
        query.from(order, member)
                .where(order.member.eq(member))
                .list(order);
    }
    /*  groupBy end  */

    /* subQuery start */
    @Test
    void 서브쿼리_예제_한건() {
        QItem itemSub = new QItem("itemSub");

        query.from(item)
                .where(item.price.eq(
                        new JPASubQuery().from(itemSub).unique(itemSub.price.max())
                ))
                .list(item);
    }

    @Test
    void 서브쿼리_예제_다건() {
        QItem itemSub = new QItem("itemSub");

        query.from(item)
                .where(item.in(
                        new JPASubQuery().from(itemSub)
                                .where(item.name.eq(itemSub.name))
                                .list(itemSub)
                ))
                .list(item);
    }

    /* subQuery end */

    /* 프로젝션 결과 반환 start */
    @Test
    void 프로젝션_대상_하나 () {
        query.from(item)
                .list(item.name)
                .forEach(System.out::println);
    }

    @Test
    void 튜플_사용_예제() {
        List<Tuple> result =
                query.from(item)
                    .list(item.name, item.price);

        for(Tuple tuple: result) {
            System.out.println("name = "+ tuple.get(item.name));
            System.out.println("price = "+ tuple.get(item.price));
        }
    }

    @Test
    void 프로퍼티_접근_setter() {
        List<ItemDTO> result =
            query.from(item).list(
                        Projections.bean(ItemDTO.class, item.name.as("username"), item.price)
                );
    }

    @Test
    void 필드_직접_접근 () {
        List<ItemDTO> result =
            query.from(item).list(
                        Projections.fields(ItemDTO.class, item.name.as("username"), item.price)
                );
    }

    @Test
    void 생성자_사용_접근 () {
        List<ItemDTO> result =
                query.from(item).list(
                        Projections.constructor(ItemDTO.class, item.name, item.price)
                );
    }

    /* 프로젝션 결과 반환 end */
    
    /* 수정,삭제 배치 쿼리 start */
    // QueryDSL도 수정,삭제 같은 배치쿼리를 지원한다.
    // JPQL 배치 쿼리와 같이 영속성 컨텍스트를 무시하고 데이터베이스를
    // 직접 쿼리 한다는 점에 유의하자
    @Test
    void 수정_배치쿼리() {
        JPAUpdateClause updateClause = new JPAUpdateClause(em.getEntityManager(), item);
        long count = updateClause.where(item.name.eq("jpa"))
                .set(item.price, item.price.add(100))
                .execute();
    }

    @Test
    void 삭제_배치쿼리() {
        JPADeleteClause deleteClause = new JPADeleteClause(em.getEntityManager(), item);
        long count = deleteClause.where(item.name.eq("jpa"))
                .execute();
    }
    
    /* 수정,삭제 배치 쿼리 end */

    /* 동적 쿼리 start */
    @Test
    void 동적_쿼리_예제() {
        SearchParam param = new SearchParam();
        param.setName("jpa");
        param.setPrice(1000);

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(param.getName()))
            builder.and(item.name.contains(param.getName()));
        if (param.getPrice() != null)
            builder.and(item.price.gt(param.getPrice()));

        List<Item> result =
                query.from(item)
                .where(builder)
                .list(item);
    }
    /* 동적 쿼리 end */

    /* 메소드 위임 start */
    @Test
    void 검색_조건_정의() {
        query.from(item)
                .where(item.isExpensive(30000))
                .list(item);
    }
    /* 메소드 위임 end */
}

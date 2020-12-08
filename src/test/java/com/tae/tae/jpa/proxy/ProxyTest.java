package com.tae.tae.jpa.proxy;

import com.mysema.commons.lang.Assert;
import com.tae.tae.dto.item.Book;
import com.tae.tae.dto.item.Item;
import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.order.OrderItem;
import org.hibernate.proxy.HibernateProxy;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProxyTest {

    @Autowired
    private TestEntityManager tem;

    private EntityManager em;

    @BeforeEach
    void setup() {
        em = tem.getEntityManager();
    }

    @Test
    void 영속성컨텍스트와_프록시() {

//        Member newMember = new Member();
//        newMember.setId("member1");
//        newMember.setName("회원1");
//
//        em.persist(newMember);
//        em.flush();
//        em.clear();

        Member refMember = em.getReference(Member.class, "member1");
        Member findMember = em.find(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());

        Assertions.assertTrue(refMember == findMember); //성공
    }

    @Test
    void 영속성컨텍스트와_프록시2() {

//        Member newMember = new Member();
//        newMember.setId("member1");
//        newMember.setName("회원1");
//
//        em.persist(newMember);
//        em.flush();
//        em.clear();

        Member findMember = em.find(Member.class, "member1");
        Member refMember = em.getReference(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());
        System.out.println("findMember Type = " + findMember.getClass());

        Assertions.assertTrue(refMember == findMember); //성공
    }

    @Test
    void 프록시_타입비교() {
        Member refMember = em.getReference(Member.class, "member1");

        System.out.println("refMember Type = " + refMember.getClass());


        assertFalse(Member.class == refMember.getClass());
        assertTrue(refMember instanceof Member);
    }

    @Test
    void 프록시와_동등성비교() {
        Member newMember = new Member();
        newMember.setId("member1");
        newMember.setName("회원1");

        Member refMember = em.getReference(Member.class, "member1");
        assertTrue(newMember.equals(refMember));
    }

    @Test
    void 부모타입으로_프록시조회() {

        //테스트데이터 준비
        Book saveBook = new Book();
        saveBook.setName("jpabook");
        saveBook.setAuthor("kim");
        em.persist(saveBook);

        em.flush();
        em.clear();

        //테스트 시작
        Item proxyItem = em.getReference(Item.class, saveBook.getId());
        System.out.println("proxy Item = " + proxyItem.getClass());

        if (proxyItem instanceof Book) {
            System.out.println("proxyItem instanceof Book ");
            Book book = (Book) proxyItem;
            System.out.println("책 저자 = " + ((Book) proxyItem).getAuthor());
        }

        //결과 검증
        assertFalse( proxyItem.getClass() == Book.class );
        assertFalse( proxyItem instanceof Book );
        assertTrue( proxyItem instanceof Item );
    }

    @Test
    void 상속관계와_프록시_도메인모델() {

        //테스트 데이터 준비
        Book book = new Book();
        book.setName("jpabook");
        book.setAuthor("kim");
        em.persist(book);

        OrderItem saveOrderItem = new OrderItem();
        saveOrderItem.setItem(book);
        em.persist(saveOrderItem);

        em.flush();
        em.clear();

        //테스트 시작
        OrderItem orderItem =
                em.find(OrderItem.class, saveOrderItem.getId());
        Item item = orderItem.getItem();

        System.out.println("item = " + item.getClass());
        assertFalse( item.getClass() == Book.class );
        assertFalse( item instanceof Book );
        assertTrue( item instanceof Item );
    }

    public static<T> T unProxy(Object entity) {
        if (entity instanceof HibernateProxy) {
            entity = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return (T) entity;
    }
}
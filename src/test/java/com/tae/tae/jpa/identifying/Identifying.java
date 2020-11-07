package com.tae.tae.jpa.identifying;

import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.order.Order;
import com.tae.tae.dto.member.MemberProductId;
import com.tae.tae.dto.order.Product;
import com.tae.tae.jpa.JpaMain;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Identifying {

    private EntityManager em = new JpaMain().getEntityMnager();
    private EntityManagerFactory emf = em.getEntityManagerFactory();

    @Test
    public void save() {
        //회원 저장
        Member member1 = new Member();
        member1.setId("member1");
        member1.setName("회원1");
        em.persist(member1);

        //상품 저장
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품1");
        em.persist(productA);

        //회원 상품 저장
        Order memberProduct = new Order();
        memberProduct.setMember(member1);   //주문 회원 - 연관관계 설정
        //memberProduct.setProduct(productA); //주문 상품 - 연관관계 설정
        memberProduct.setOrderAmount(2);    //주문 수량

        em.persist(memberProduct);
    }

    @Test
    public void find() {

        //기본 키 값 생성
        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("productA");

        Order order = em.find(Order.class, memberProductId);

        Member member = order.getMember();
        //Product product = order.getProduct();

        //System.out.println("member = " + member.getName());
        //System.out.println("product = " + product.getName());
        System.out.println("orderAmount = "
                + order.getOrderAmount());
    }
}

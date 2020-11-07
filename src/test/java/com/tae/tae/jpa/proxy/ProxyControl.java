package com.tae.tae.jpa.proxy;

import com.tae.tae.dto.inheritance.non_identying.Child;
import com.tae.tae.dto.inheritance.non_identying.Parent;
import com.tae.tae.dto.member.Member;
import com.tae.tae.dto.member.Team;
import com.tae.tae.dto.order.Delivery;
import com.tae.tae.dto.order.Order;
import com.tae.tae.dto.order.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProxyControl {

    @Autowired
    private TestEntityManager em;

    @Test
    public void 영속성전이(){
        Delivery delivery = new Delivery();
        OrderItem orderItem1 = new OrderItem();
        OrderItem orderItem2 = new OrderItem();

        Order order = new Order();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);

        em.persist(order); //delivery, orderItem 플러시 시점에 영속성 전이
        em.getEntityManager()
                .getTransaction()
                .commit();
    }

    @Test
    public void 즉시로딩(){
        Member member =em.find(Member.class, "member1");
        Team team = member.getTeam(); //객체 그래프 탐색
    }

    @Test
    public void 지연로딩(){
        Member member = em.find(Member.class,"member1");
        Team team = member.getTeam(); //객체 그래프 탐색
        team.getName(); //팀 객체 실제 사용
    }

    @Test
    public void saveWithCascade(){

        Child child1 = new Child();
        Child child2 = new Child();

        Parent parent = new Parent();
        child1.setParent(parent);   //연관관계 추가
        child2.setParent(parent);   //연관관계 추가
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        //부모 저장, 연관된 자식들 저장
        em.persist(parent);

        em.getEntityManager().getTransaction().commit();
    }

    @Test
    public void save(){

        Member member1 = new Member();
        member1.setId("member1");
        member1.setName("회원1");

        Member member2 = new Member();
        member2.setId("member2");
        member2.setName("회원2");

        Team team = new Team();
        team.setId("team1");
        team.setName("팀1");

        member1.addTeam(team);
        //member2.addTeam(team);

        em.persist(team);
        em.merge(member1);
        //em.persist(member2);

        em.getEntityManager().getTransaction().commit();
    }
}

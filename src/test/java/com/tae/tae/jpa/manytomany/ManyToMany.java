package com.tae.tae.jpa.manytomany;

import com.tae.tae.dto.Member;
import com.tae.tae.dto.Product;
import com.tae.tae.jpa.JpaMain;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@DataJpaTest
public class ManyToMany {

    private EntityManager em = new JpaMain().getEntityMnager();
    private EntityManagerFactory emf =  em.getEntityManagerFactory();


    @Test
    public void save(){
        EntityTransaction et = em.getTransaction();

        et.begin();
        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setName("회원1");
        member1.getProducts().add(productA); //연관관계 설정
        em.persist(member1);

        et.commit();

        em.close();
        emf.close();

    }

    @Test
    public void find() {

        Member member = em.find(Member.class,"member1");
        List<Product> products = member.getProducts(); //연관관계 설정
        for(Product product : products){
            System.out.println("products.name = " + product.getName());
        }
    }

    @Test
    public void findInverse() {

        Product product = em.find(Product.class, "productA");
        List<Member> members = product.getMembers();
        for(Member member : members){
            System.out.println("member = "+ member.getName());
        }
    }


}

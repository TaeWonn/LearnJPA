package com.tae.tae.jpa.inheritance;

import com.tae.tae.dto.inheritance.non_identying.Parent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmbeddedTest {

    @Autowired
    private TestEntityManager em;

    @Test
    public void EmbeddTest(){
        //EntityTransaction tx = em.getEntityManager().getTransaction();

        Parent parent = new Parent();
        //ParentId parentId = new ParentId("myId1","myId2");
        //parent.setId(parentId);
        parent.setName("parentName");
        em.persist(parent);

        //tx.commit();
    }

    @Test
    public void findTest() {
        //ParentId parentId = new ParentId("myId1","myId2");
        //Parent parent = em.find(Parent.class, parentId);
        //System.out.println(parent.getId().getId1());
    }
}

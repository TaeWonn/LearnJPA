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
public class ParentTest {

    @Autowired
    private TestEntityManager em;

    @Test
    public void Parent테스트(){
        Parent parent = new Parent();
        //parent.setId1("myId1");
        //parent.setId2("myId2");
        parent.setName("parentName");
        em.persist(parent);
    }
}

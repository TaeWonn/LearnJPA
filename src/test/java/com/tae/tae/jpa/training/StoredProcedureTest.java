package com.tae.tae.jpa.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoredProcedureTest {

    @Autowired
    private TestEntityManager em;

    @Test
    void 순서_기반_파라미터_호출() {
        StoredProcedureQuery spq =
                em.getEntityManager()
                .createStoredProcedureQuery("proc_multiply");
        spq.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

        spq.setParameter(1, 100);
        spq.execute();

        Integer out = (Integer)spq.getOutputParameterValue(2);
        System.out.println("out = " + out);
    }

    @Test
    void 파라미터에_이름_사용() {
        StoredProcedureQuery spq =
                em.getEntityManager()
                .createStoredProcedureQuery("proc_multiply");

        spq.registerStoredProcedureParameter("inParam", Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("outParam", Integer.class, ParameterMode.OUT);

        spq.setParameter("inParam", 100);
        spq.execute();

        Integer out = (Integer)spq.getOutputParameterValue("outParam");
        System.out.println("out = " + out);
    }
}

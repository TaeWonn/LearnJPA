package com.tae.tae.jpa.value_type;

import com.tae.tae.dto.member.Address;
import com.tae.tae.dto.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ValueType {

    @Autowired
    private TestEntityManager em;

    @Test
    public void 불변객체사용(){
        Member member1 = em.find(Member.class,"member1");
        Member member2 = em.find(Member.class, "member2");

        Address address = member1.getHomeAddress();
        //회원1의 주소값을 조회해서 새로운 주소값을 생성
        Address newAddress = new Address(address.getCity());
        member2.setHomeAddress(newAddress);
    }
}

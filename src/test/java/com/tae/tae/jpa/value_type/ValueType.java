package com.tae.tae.jpa.value_type;

import com.tae.tae.dto.member.Address;
import com.tae.tae.dto.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

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

    @Test
    public void 값_타입_컬렉션_등록() {
        Member member = new Member();

        member.setId("member5");
        member.setName("회원5");
        //임베디드 값 타입
        member.setHomeAddress(new Address("통영","몽돌해수욕장","660-123"));

        //기본 값 타입 컬렉션
        member.getFavoriteFoods().add("짬뽕");
        member.getFavoriteFoods().add("짜장");
        member.getFavoriteFoods().add("탕수육");

        //임베디드 값 타입 컬렉션
        member.getAddressHistory().add(new Address("서울","강남","123-123"));
        member.getAddressHistory().add(new Address("서울","강북","000-000"));

        em.persist(member);

        em.getEntityManager()
                .getTransaction()
                .commit();
    }

    @Test
    public void 값_타입_컬렉션_조회() {
        //SQL : SELECT ID, CITY, STREET, ZIPCODE FROM MEMBER WHERE ID = ?
        Member member = em.find(Member.class, "member5");

        //2. member.homeAddress
        Address homeAddress = member.getHomeAddress();

        //3. member.favoriteFoods
        Set<String> favoriteFoods = member.getFavoriteFoods(); //LAZY

        //SQL: SELECT MEMBER_ID, FOOD_NAME FROM FAVORITE_FOODS WHERE_ID = ?
        favoriteFoods.stream().forEach(System.out::println);

        //4. member.addressHistory
        List<Address> addressHistory = member.getAddressHistory();

        // SQL: SELECT MEMBER_ID, CITY, STREET, ZIPCODE FROM ADDRESS WHERE MEMBER_ID = ?
        addressHistory.get(0);
    }

    @Test
    public void 값_타입_컬렉션_수정(){
        Member member = em.find(Member.class,"member5");

        //1. 임베디드 값 타입 수정
        member.setHomeAddress(new Address("새로운도시","신도시1","123456"));

        //2. 기본값 타입 컬렉션 수정
        Set<String> favoriteFoods = member.getFavoriteFoods();
        favoriteFoods.remove("탕수육");
        favoriteFoods.add("치킨");

        //3. 임베디드 값 타입 컬렉션 수정
        List<Address> addressHistory = member.getAddressHistory();
        addressHistory.remove(new Address("서울","기존 주소","123-123"));
        addressHistory.add(new Address("새로운도시","새로운 주소","123-456"));

        em.getEntityManager()
                .getTransaction()
                .commit();
    }

}

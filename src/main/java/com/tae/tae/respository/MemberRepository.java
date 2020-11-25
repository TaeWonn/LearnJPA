package com.tae.tae.respository;

import com.tae.tae.dto.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("select m from Member m where m.name = :name")
    //@Query("SELECT * FROM MEMBER WHERE USERNAME = :name", nativeQuery= true)
    Member findByUsername(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.price = p.price * 1.1 where p.stockAmount < :stockAmount")
    int bulkPriceUp(@Param("stockAmount") String stockAmount);



}

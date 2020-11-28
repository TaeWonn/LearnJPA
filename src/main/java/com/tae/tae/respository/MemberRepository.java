package com.tae.tae.respository;

import com.tae.tae.dto.member.Member;
import com.tae.tae.respository.custom.MemberRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;


public interface MemberRepository
        extends JpaRepository<Member, String> ,
                JpaSpecificationExecutor ,
                MemberRepositoryCustom {

    @Query("select m from Member m where m.name = :name")
    //@Query("SELECT * FROM MEMBER WHERE USERNAME = :name", nativeQuery= true)
    Member findByUsername(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.price = p.price * 1.1 where p.stockAmount < :stockAmount")
    int bulkPriceUp(@Param("stockAmount") String stockAmount);

    // count 쿼리 사용
    Page<Member> findByNameStartingWith(String name, Pageable pageable);


    // 쿼리 시 락을 걸려면 어노테이션을 사용한다.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findByName(String name);

}

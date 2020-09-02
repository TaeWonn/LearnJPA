package com.tae.jpa.repository;

import com.tae.jpa.vo.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Long> {

    // 비워져 있어도 작동
    // long 이 아니라 Long 으로 작성

    // findBy 뒤에 컬렴명을 붙여주면 이를 이용한 검색이 가능
    public List<MemberVO> findById(String id);

    public List<MemberVO> findByName(String name);

    // like 검색 가능
    public List<MemberVO> findByNameLike(String keyword);
}

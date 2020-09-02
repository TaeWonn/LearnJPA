package com.tae.jpa;

import com.tae.jpa.service.MemberService;
import com.tae.jpa.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("member")
public class MemberRestController {

    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberService memberService;

    // 모든 회원 조회
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MemberVO>> getAllMembers() {
        List<MemberVO> member = memberService.findAll();
        return new ResponseEntity<List<MemberVO>>(member, HttpStatus.OK);
    }

    // 회원 번호로 조회
    @GetMapping(value = "/{mbrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MemberVO> getMember(@PathVariable("mbrNo") Long mbrNo) {
        Optional<MemberVO> member = memberService.findById(mbrNo);
        return new ResponseEntity<MemberVO>(member.get(),HttpStatus.OK);
    }

    // 회원 삭제
    @DeleteMapping(value = "/{mbrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) {
        memberService.deleteById(mbrNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    // 회원 수정
    @PutMapping(value = "/{mbrNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MemberVO> updateMember(@PathVariable Long mbrNo, MemberVO member) {
        memberService.updateById(mbrNo, member);
        return new ResponseEntity<MemberVO>(member, HttpStatus.OK);
    }

    // 회원 입력
    @PostMapping
    public ResponseEntity<MemberVO> save(MemberVO member) {
        return new ResponseEntity<MemberVO>(memberService.save(member), HttpStatus.OK);
    }

    @RequestMapping(value= "/saveMember", method =  RequestMethod.GET)
    public ResponseEntity<MemberVO> save(HttpServletRequest req, MemberVO member) {
        return new ResponseEntity<MemberVO>(memberService.save(member), HttpStatus.OK);
    }


}

package com.tae.tae.controller;


import com.tae.tae.dto.member.Member;
import com.tae.tae.service.HelloService;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    private HelloService helloService;

    public HelloController(final HelloService helloService) {
        this.helloService = helloService;
    }

    public void hello() {
        // 반환된 member 엔티티는 준영속 상태다.
        Member member = helloService.logic();
    }
}

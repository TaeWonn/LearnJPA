package com.tae.tae.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "jpabook")
@Table(name = "MEMBER")
public class Member {

    @GeneratedValue
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;
    private Integer age;
}
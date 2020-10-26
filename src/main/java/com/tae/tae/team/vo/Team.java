package com.tae.tae.team.vo;

import com.tae.tae.member.vo.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEAM")
public class Team {

    public Team (String id , String name ) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name="TEAM_ID")
    private String id;

    @Column(name="NAME")
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
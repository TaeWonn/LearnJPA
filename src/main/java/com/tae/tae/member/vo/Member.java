package com.tae.tae.member.vo;

import com.tae.tae.team.vo.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MEMBER")
public class Member {

    public Member(String id, String name){
        this.id = id;
        this.name = name;
    }

    //@GeneratedValue
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public void setTeam(Team team){

        //기존 팀과 관계를 제거
        if(this.team != null) this.team.getMembers().remove(this);

        this.team = team;
        team.getMembers().add(this);
    }
}
package com.tae.tae.team.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEAM")
public class Team {

    @Id
    @Column(name="TEAM_ID")
    private String id;

    @Column(name="NAME")
    private String name;
}

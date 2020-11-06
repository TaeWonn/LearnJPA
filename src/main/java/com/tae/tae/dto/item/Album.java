package com.tae.tae.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album extends Item {

    private String artist;
    private String etc;
}

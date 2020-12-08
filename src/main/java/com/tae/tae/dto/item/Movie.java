package com.tae.tae.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Item {

    private String director;
    private String actor;

    @Override
    public String getTitle() {
        return "[제목:" + getName() + " 감독:" + director + " 배우:" + actor +"]";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

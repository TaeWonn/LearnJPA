package com.tae.tae.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK_ID") //ID 재정의
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Item{

    private String author;  //작가
    private String isbn;    //ISBN
}

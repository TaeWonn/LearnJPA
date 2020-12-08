package com.tae.tae.dto.item;

import lombok.Getter;

@Getter
public class TitleVisitor implements Visitor{

    private String title;

    @Override
    public void visit(Book book) {
        title = "[제목:" + book.getName() + " 저자:" + book.getAuthor() + "]";
    }

    @Override
    public void visit(Album album) {
        title = "[제목:" + album.getName() + " 작가:" + album.getArtist() + "]";
    }

    @Override
    public void visit(Movie movie) {
        title = "[제목:" + movie.getName() + " 감독:" + movie.getDirector() +
                "배우 : " + movie.getActor() +"]";
    }
}

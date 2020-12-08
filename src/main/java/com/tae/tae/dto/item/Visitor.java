package com.tae.tae.dto.item;

public interface Visitor {

    void visit(Book book);
    void visit(Album album);
    void visit(Movie movie);
}

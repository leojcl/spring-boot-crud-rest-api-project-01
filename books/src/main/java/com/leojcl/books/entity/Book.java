package com.leojcl.books.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private long id;
    private String title;
    private String author;
    private String category;
    private int rating;

    public Book(long id, String title, String author, String category, int rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }
}

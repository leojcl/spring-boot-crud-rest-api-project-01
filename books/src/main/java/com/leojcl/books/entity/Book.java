package com.leojcl.books.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private String title;
    private String author;
    private String category;


    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }
}

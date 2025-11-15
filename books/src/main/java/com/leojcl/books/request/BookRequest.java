package com.leojcl.books.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private long id;
    @Size(min = 1, max = 30, message = "Title is between 1 and 30 characters")
    private String title;
    @Size(min = 1, max = 40, message = "Author is between 1 and 40 characters")
    private String author;
    @Size(min = 1, max = 30, message = "Category is between 1 and 30 characters")
    private String category;
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot go pass 5")
    private int rating;

    public BookRequest(long id, String title, String author, String category, int rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }
}

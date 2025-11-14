package com.leojcl.books.controller;

import com.leojcl.books.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController(){
        initialBooks();
    }

    private void initialBooks(){
        books.addAll(List.of(
                new Book("Title one", "Author one", "technology"),
                new Book("Title two", "Author two", "math"),
                new Book("Title three", "Author three", "Java"),
                new Book("Title four", "Author four", "english"),
                new Book("Title five", "Author five", "science"),
                new Book("Title six", "Author six", "Java"),
                new Book("Title seven", "Author seven", "Web")
        ));

    }
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String category) {

        if(category == null){
            return ResponseEntity.ok(books);
        }
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.isEmpty()
                        ? ResponseEntity.notFound().build() : ResponseEntity.ok(list)
                ));
    }

    @GetMapping("/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title){
        return books.stream()
                .filter(
                        book -> book.getTitle().equalsIgnoreCase(title)
                )
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public void createBook(@RequestBody Book newBook){
       boolean isnewBook = books.stream()
               .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
       if(isnewBook){
           books.add(newBook);
       }
    }

    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updateBook){
        for(int i = 0; i<books.size(); i++){
            if(books.get(i).getTitle().equalsIgnoreCase(title)){
                books.set(i, updateBook);
                return;
            }
        }
    }
    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title){
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }
}

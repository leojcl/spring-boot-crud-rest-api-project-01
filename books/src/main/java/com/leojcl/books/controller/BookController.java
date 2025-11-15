package com.leojcl.books.controller;

import com.leojcl.books.entity.Book;
import com.leojcl.books.exception.BookNotFoundException;
import com.leojcl.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Books Rest API Endpoints", description = "Operations related to book")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController(){
        initialBooks();
    }

    private void initialBooks(){
        books.addAll(List.of(
                new Book(1, "Computer Science Pro", "Leo", "Computer Science", 5),
                new Book(2, "Java Spring Master", "Eric", "Computer Science", 5),
                new Book(3, "C++", "Junkie", "Language", 3),
                new Book(4, "How Bears Hibernate", "Bob B", "Science", 2),
                new Book(5, "A Pirate's Treasure", "Curt C", "History", 4),
                new Book(6, "Why 2+2 is Better", "Dan D", "Math", 5)
        ));

    }

    @Operation(summary = "Get all book", description = "Retrieve a list of all available book")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<Book>> getBooks( @Parameter(description = "Optional query parameter")
            @RequestParam(required = false) String category) {

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

    @Operation(summary = "Get a book by Id", description = "Retrieve a specific book by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById( @Parameter(description = "Id of book to be retrieved")
            @PathVariable @Min(value = 1) long id){
        return books.stream()
                .filter(
                        book -> book.getId() == id
                )
                .findFirst()
//                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BookNotFoundException("Book not found - " + id));
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody  BookRequest bookRequest){
//       boolean isnewBook = books.stream()
//               .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
//       if(isnewBook){
//           books.add(newBook);
//       }
        // version 02 is using BookRequest
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;

        Book book = convertToBook(id, bookRequest);
        books.add(book);

    }

    @Operation(summary = "Update a book", description = "Update the details of an existing book")
    @PutMapping("/{id}")
    public Book updateBook( @Parameter(description = "Id of the book to update")
            @PathVariable @Min(value = 1) long id, @Valid @RequestBody BookRequest bookRequest){
        for(int i = 0; i<books.size(); i++){
            if(books.get(i).getId() == id){
                Book updateBook = convertToBook(id, bookRequest);
                books.set(i, updateBook);
                return updateBook;
            }
        }
        throw new BookNotFoundException("Book not found - "+ id);
    }

    @Operation(summary = "Delete a book", description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the book to delete")
            @PathVariable @Min(value = 1) long id){

        books.stream()
                        .filter(
                                book -> book.getId() == id
                        )
                                .findFirst()
                                        .orElseThrow(() -> new BookNotFoundException("Book not found - " + id));

        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook (long id, BookRequest bookRequest){
        return  new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

}

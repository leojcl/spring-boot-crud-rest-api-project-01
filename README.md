# spring-boot-crud-rest-api-project-01
Simple Book project Spring boot CRUD REST API

What you can learn from this project ?

🔹 1. Spring Boot REST Controller Basics
Annotations used

@RestController
Defines this class as a REST controller.
Automatically converts return objects into JSON using Jackson.

@RequestMapping("/api/books")
Sets a base URL so all endpoints begin with /api/books.

Why it’s needed

It helps structure endpoints cleanly, handle HTTP requests, and return JSON data.

🔹 2. HTTP Method Annotations
Annotation				Method		Use Case
@GetMapping				GET			Retrieve data
@PostMapping			POST		Create new data
@PutMapping				PUT			Update existing data
@DeleteMapping			DELETE		Remove data

Why it’s needed

To build a clear CRUD REST API following standard HTTP semantics.

🔹 3. Path Variable vs Query Parameter
Path Variable – @PathVariable

Used to identify a specific resource.

Example:
GET /api/books/{title}

→ Good for IDs, titles, user codes, etc.

Query Parameter – @RequestParam

Used for filtering or searching.

Example:
GET /api/books?category=Java

→ Good for optional fields like page, limit, sort, category.

✔ Rule of Thumb

Identity → Path Variable

Filter/Search → Query Param

🔹 4. Request Body – @RequestBody

Used for POST/PUT when client sends JSON data.

Example JSON:

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "category": "Programming"
}


Spring automatically converts JSON → Java Object using Jackson.

Why it’s needed

To accept and process input from client when creating or updating data.

🔹 5. Using ResponseEntity for Proper HTTP Responses

Code used:

return ResponseEntity.ok(book);              // 200 OK
return ResponseEntity.notFound().build();    // 404 Not Found

Why use ResponseEntity?

Control HTTP status code

Return JSON or empty body

Make the API correct and professional

Better for interview and real-world REST API design

🔹 6. Java Stream API for Searching & Filtering

Examples used:

Find one book by title
books.stream()
     .filter(book -> book.getTitle().equalsIgnoreCase(title))
     .findFirst()

Find multiple books by category
books.stream()
     .filter(book -> book.getCategory().equalsIgnoreCase(category))
     .collect(Collectors.toList());

Concepts practiced

filter()

equalsIgnoreCase()

findFirst()

collect(Collectors.toList())

Optional.map() + orElseGet()

Why it’s needed

Stream API makes search/filter logic clean and modern.

🔹 7. Java Optional for Null-Safe Handling

Example:

return books.stream()
        .filter(book -> book.getTitle().equalsIgnoreCase(title))
        .findFirst()
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());

Why it’s needed

Prevents returning null

Handles “found / not found” cases elegantly

Best practice in modern Java

🔹 8. Updating & Deleting Using List Operations
Update (replace book)
books.set(i, updateBook);

Delete by condition
books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));

Why it’s needed

Helps practice basic data manipulation before moving to JPA/MySQL.

🔹 9. In-Memory Data Storage for Learning

The project stores books in:

private final List<Book> books = new ArrayList<>();

Why this approach?

No need for a database

Focus on learning REST, controller logic, and HTTP

Easier for beginners

Perfect for practicing API designs

Later, this can be upgraded to Spring Data JPA + MySQL.


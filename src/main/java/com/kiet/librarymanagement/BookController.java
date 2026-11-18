
package com.kiet.librarymanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    // 1. ADD BOOK
    @PostMapping
    public LibraryBook addBook(@RequestBody LibraryBook book) {
        return repository.save(book);
    }

    // 2. GET ALL BOOKS
    @GetMapping
    public List<LibraryBook> getAllBooks() {
        return repository.findAll();
    }

    // 3. GET BOOK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<LibraryBook> getBookById(
            @PathVariable int id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. UPDATE BOOK
    @PutMapping("/{id}")
    public ResponseEntity<LibraryBook> updateBook(
            @PathVariable int id,
            @RequestBody LibraryBook newBook) {

        return repository.findById(id).map(book -> {

            book.setCategory(newBook.getCategory());
            book.setQuantity(newBook.getQuantity());
            book.setPrice(newBook.getPrice());

            return ResponseEntity.ok(repository.save(book));

        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE BOOK
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable int id) {

        if (repository.existsById(id)) {

            repository.deleteById(id);

            return ResponseEntity.ok(
                    "Book Deleted Successfully!"
            );
        }

        return ResponseEntity.status(404)
                .body("Book Not Found!");
    }
}

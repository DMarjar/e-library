package congress.dwp.libraryservices.controller;

import congress.dwp.libraryservices.model.Book;
import congress.dwp.libraryservices.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    @Autowired
    private BookService service;

    @PostMapping("/book")
    public Book saveBook(@RequestBody(required = true) Book book) {
        return service.saveBook(book);
    }

    @PostMapping("/page")
    Page<Book> getPage(Pageable pageable) {
        return service.pageOfBook(pageable);
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

    @PutMapping("/book/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return service.updateBook(book);
    }
}

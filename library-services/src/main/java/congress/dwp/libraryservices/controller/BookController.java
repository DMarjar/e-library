package congress.dwp.libraryservices.controller;

import congress.dwp.libraryservices.model.Book;
import congress.dwp.libraryservices.model.dto.DateRangeDTO;
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
    public Book saveBook(@RequestBody() Book book) {
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

    @GetMapping("/book/title/{title}")
    public Page<Book> findByTitleContaining(@PathVariable String title, Pageable page) {
        return service.findByTitleContaining(title, page);
    }

    @GetMapping("/book/author/{author}")
    public Page<Book> findByAuthorContaining(@PathVariable String author, Pageable page) {
        return service.findByAuthorContaining(author, page);
    }

    @PostMapping("/book/date/between")
    public Page<Book> findByFullPublishDateBetween(@RequestBody DateRangeDTO dates, Pageable page) {
        return service.findByFullPublishDateBetween(dates.getStart(), dates.getEnd(), page);
    }

    @GetMapping("/book/genre/{genre}")
    public Page<Book> findByGenreContaining(@PathVariable String genre, Pageable page) {
        return service.findByGenreContaining(genre, page);
    }

    @GetMapping("/book/date/desc")
    public Page<Book> findByOrderByFullPublishDate(Pageable page) {
        return service.findByOrderByFullPublishDate(page);
    }
}

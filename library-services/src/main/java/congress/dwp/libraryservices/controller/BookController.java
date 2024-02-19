package congress.dwp.libraryservices.controller;

import congress.dwp.libraryservices.model.Book;
import congress.dwp.libraryservices.model.dto.AuthorDTO;
import congress.dwp.libraryservices.model.dto.DateRangeDTO;
import congress.dwp.libraryservices.model.dto.GenreDTO;
import congress.dwp.libraryservices.model.dto.TitleDTO;
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

    @PostMapping("/book/title")
    public Page<Book> findByTitleContaining(@RequestBody TitleDTO title, Pageable page) {
        return service.findByTitleContaining(title.getTitle(), page);
    }

    @PostMapping("/book/author")
    public Page<Book> findByAuthorContaining(@RequestBody AuthorDTO author, Pageable page) {
        return service.findByAuthorContaining(author.getAuthor(), page);
    }

    @PostMapping("/book/date/between")
    public Page<Book> findByFullPublishDateBetween(@RequestBody DateRangeDTO dates, Pageable page) {
        return service.findByFullPublishDateBetween(dates.getStart(), dates.getEnd(), page);
    }

    @PostMapping("/book/genre")
    public Page<Book> findByGenreContaining(@RequestBody GenreDTO genre, Pageable page) {
        return service.findByGenreContaining(genre.getGenre(), page);
    }

    @PostMapping("/book/date/descending")
    public Page<Book> findByOrderByFullPublishDate(Pageable page) {
        return service.findByOrderByFullPublishDateDesc(page);
    }
}

package congress.dwp.libraryservices.service;

import congress.dwp.libraryservices.model.Book;
import congress.dwp.libraryservices.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book saveBook(Book bean) {
        return repository.save(bean);
    }

    public Page<Book> pageOfBook(Pageable page) {
        return repository.findAll(page);
    }

    public Book getBookById(Long id) {
        return repository.findById(id).get();
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }

    public Book updateBook(Book book) {
        Long id = book.getId();
        Book bookToUpdate = repository.findById(id).get();
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setFullPublishDate(book.getFullPublishDate());
        return repository.save(bookToUpdate);
    }

    public Page<Book> findByTitleContaining(String title, Pageable page) {
        return repository.findByTitleContaining(title, page);
    }

    public Page<Book> findByAuthorContaining(String author, Pageable page) {
        return repository.findByAuthorContaining(author, page);
    }

    public Page<Book> findByFullPublishDateBetween(Date start, Date end, Pageable page) {
        return repository.findByFullPublishDateBetween(start, end, page);
    }

    public Page<Book> findByGenreContaining(String genre, Pageable page) {
        return repository.findByGenreContaining(genre, page);
    }

    public Page<Book> findByOrderByFullPublishDate(Pageable page) {
        return repository.findByOrderByFullPublishDate(page);
    }
}

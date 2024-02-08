package congress.dwp.libraryservices.service;

import congress.dwp.libraryservices.model.Book;
import congress.dwp.libraryservices.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        bookToUpdate.setYear(book.getYear());
        return repository.save(bookToUpdate);
    }
}

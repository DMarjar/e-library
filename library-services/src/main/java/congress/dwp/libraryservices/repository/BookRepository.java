package congress.dwp.libraryservices.repository;

import congress.dwp.libraryservices.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContaining(String title, Pageable page);
    Page<Book> findByAuthorContaining(String author, Pageable page);
    Page<Book> findByFullPublishDateBetween(LocalDate start, LocalDate end, Pageable page);
    Page<Book> findByGenreContaining(String genre, Pageable page);
    Page<Book> findByOrderByFullPublishDateDesc(Pageable page);
}

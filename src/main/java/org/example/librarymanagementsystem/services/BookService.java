package org.example.librarymanagementsystem.services;
import org.example.librarymanagementsystem.entities.Book;
import org.example.librarymanagementsystem.exceptions.BookNotFoundException;
import org.example.librarymanagementsystem.exceptions.InvalidBookException;
import org.example.librarymanagementsystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    public Book addBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null) {
            throw new InvalidBookException("Book title and author are required");
        }
        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book newBook) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        book.setAuthor(newBook.getAuthor());
        book.setTitle(newBook.getTitle());
        book.setPublicationYear(newBook.getPublicationYear());
        book.setISBN(newBook.getISBN());
        book.setGenre(newBook.getGenre());
        return bookRepository.save(book);
    }

    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}

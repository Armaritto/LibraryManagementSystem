package org.example.librarymanagementsystem.ServicesTests;
import org.example.librarymanagementsystem.entities.Book;
import org.example.librarymanagementsystem.exceptions.BookNotFoundException;
import org.example.librarymanagementsystem.exceptions.InvalidBookException;
import org.example.librarymanagementsystem.repositories.BookRepository;
import org.example.librarymanagementsystem.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllBooksReturnsListOfBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }
    @Test
    void getBookByIdReturnsBookWhenFound() {
        Book book = new Book();
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Book result = bookService.getBookById(1);
        assertEquals(book, result);
        verify(bookRepository, times(1)).findById(1);
    }
    @Test
    void getBookByIdThrowsExceptionWhenNotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1));
        verify(bookRepository, times(1)).findById(1);
    }
    @Test
    void addBookSavesAndReturnsBook() {
        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.addBook(book);
        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    void addBookThrowsExceptionWhenTitleOrAuthorIsNull() {
        Book book = new Book();
        assertThrows(InvalidBookException.class, () -> bookService.addBook(book));
        verify(bookRepository, never()).save(book);
    }
    @Test
    void updateBookUpdatesAndReturnsBook() {
        Book existingBook = new Book();
        Book newBook = new Book();
        newBook.setTitle("New Title");
        newBook.setAuthor("New Author");
        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);
        Book result = bookService.updateBook(1, newBook);
        assertEquals(existingBook, result);
        assertEquals("New Title", existingBook.getTitle());
        assertEquals("New Author", existingBook.getAuthor());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(existingBook);
    }
    @Test
    void updateBookThrowsExceptionWhenNotFound() {
        Book newBook = new Book();
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1, newBook));
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, never()).save(newBook);
    }
    @Test
    void deleteBookDeletesBookWhenFound() {
        when(bookRepository.existsById(1)).thenReturn(true);
        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }
    @Test
    void deleteBookThrowsExceptionWhenNotFound() {
        when(bookRepository.existsById(1)).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1));
        verify(bookRepository, times(1)).existsById(1);
        verify(bookRepository, never()).deleteById(1);
    }
}

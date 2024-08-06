package org.example.librarymanagementsystem;
import org.example.librarymanagementsystem.controllers.BookController;
import org.example.librarymanagementsystem.entities.Book;
import org.example.librarymanagementsystem.exceptions.BookNotFoundException;
import org.example.librarymanagementsystem.exceptions.InvalidBookException;
import org.example.librarymanagementsystem.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class BookControllerTest {
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllBooksReturnsListOfBooks() {
        List<Book> books = List.of(new Book(1, "Title1", "Author1", 2020, "ISBN1", "Genre1"));
        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(books, result);
    }
    @Test
    void getBookByIdReturnsBook() {
        Book book = new Book(1, "Title1", "Author1", 2020, "ISBN1", "Genre1");
        when(bookService.getBookById(1)).thenReturn(book);

        Book result = bookController.getBookById(1);

        assertEquals(book, result);
    }
    @Test
    void addBookReturnsAddedBook() {
        Book book = new Book(1, "Title1", "Author1", 2020, "ISBN1", "Genre1");
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        Map<String, Object> payload = new HashMap<>();
        payload.put("ID", 1);
        payload.put("title", "Title1");
        payload.put("author", "Author1");
        payload.put("publicationYear", 2020);
        payload.put("ISBN", "ISBN1");
        payload.put("genre", "Genre1");

        Book result = bookController.addBook(payload);

        assertEquals(book, result);
    }
    @Test
    void updateBookReturnsUpdatedBook() {
        Book book = new Book(1, "Title1", "Author1", 2020, "ISBN1", "Genre1");
        when(bookService.updateBook(eq(1), any(Book.class))).thenReturn(book);

        Book result = bookController.updateBook(1, book);

        assertEquals(book, result);
    }
    @Test
    void deleteBookExecutesSuccessfully() {
        doNothing().when(bookService).deleteBook(1);

        bookController.deleteBook(1);

        verify(bookService, times(1)).deleteBook(1);
    }
    @Test
    void handleBookNotFoundExceptionReturnsNotFoundStatus() {
        BookNotFoundException ex = new BookNotFoundException("Book not found");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Map<String, String>> response = bookController.handleBookNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Book not found", response.getBody().get("error"));
    }
    @Test
    void handleInvalidBookExceptionReturnsBadRequestStatus() {
        InvalidBookException ex = new InvalidBookException("Invalid book");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Map<String, String>> response = bookController.handleInvalidBookException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid book", response.getBody().get("error"));
    }
    @Test
    void handleGlobalExceptionReturnsInternalServerErrorStatus() {
        Exception ex = new Exception("Unexpected error");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Map<String, String>> response = bookController.handleGlobalException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().get("error"));
    }
}

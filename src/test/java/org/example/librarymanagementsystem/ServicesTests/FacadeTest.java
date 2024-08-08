package org.example.librarymanagementsystem.ServicesTests;
import org.example.librarymanagementsystem.entities.Book;
import org.example.librarymanagementsystem.entities.BorrowRecord;
import org.example.librarymanagementsystem.entities.Patron;
import org.example.librarymanagementsystem.entities.ReturnRecord;
import org.example.librarymanagementsystem.exceptions.BookNotFoundException;
import org.example.librarymanagementsystem.exceptions.BorrowInvalidException;
import org.example.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.example.librarymanagementsystem.repositories.BorrowingRecordRepository;
import org.example.librarymanagementsystem.repositories.ReturnRecordRepository;
import org.example.librarymanagementsystem.services.BookService;
import org.example.librarymanagementsystem.services.BorrowReturnFacade;
import org.example.librarymanagementsystem.services.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class FacadeTest {
    @Mock
    private BookService bookService;
    @Mock
    private PatronService patronService;
    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;
    @Mock
    private ReturnRecordRepository returnRecordRepository;
    @InjectMocks
    private BorrowReturnFacade borrowReturnFacade;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void borrowBookSuccessfully() {
        Book book = new Book();
        Patron patron = new Patron();
        when(bookService.getBookById(1)).thenReturn(book);
        when(patronService.getPatronById(1)).thenReturn(patron);
        when(borrowingRecordRepository.findByBookIdAndPatronId(1, 1)).thenReturn(Collections.emptyList());
        borrowReturnFacade.borrowBook(1, 1);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowRecord.class));
    }
    @Test
    void borrowBookThrowsBookNotFoundException() {
        when(bookService.getBookById(1)).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> borrowReturnFacade.borrowBook(1, 1));
    }
    @Test
    void borrowBookThrowsPatronNotFoundException() {
        Book book = new Book();
        when(bookService.getBookById(1)).thenReturn(book);
        when(patronService.getPatronById(1)).thenReturn(null);
        assertThrows(PatronNotFoundException.class, () -> borrowReturnFacade.borrowBook(1, 1));
    }
    @Test
    void borrowBookThrowsBorrowInvalidException() {
        Book book = new Book();
        Patron patron = new Patron();
        BorrowRecord borrowRecord = new BorrowRecord(book, patron, new Date(), null);
        when(bookService.getBookById(1)).thenReturn(book);
        when(patronService.getPatronById(1)).thenReturn(patron);
        when(borrowingRecordRepository.findByBookIdAndPatronId(1, 1)).thenReturn(List.of(borrowRecord));
        assertThrows(BorrowInvalidException.class, () -> borrowReturnFacade.borrowBook(1, 1));
    }
    @Test
    void returnBookSuccessfully() {
        Book book = new Book();
        Patron patron = new Patron();
        BorrowRecord borrowRecord = new BorrowRecord(book, patron, new Date(), null);
        when(bookService.getBookById(1)).thenReturn(book);
        when(patronService.getPatronById(1)).thenReturn(patron);
        when(borrowingRecordRepository.findByBookIdAndPatronId(1, 1)).thenReturn(List.of(borrowRecord));
        borrowReturnFacade.returnBook(1, 1);
        verify(borrowingRecordRepository, times(1)).deleteById(borrowRecord.getID());
        verify(returnRecordRepository, times(1)).save(any(ReturnRecord.class));
    }
    @Test
    void returnBookThrowsBookNotFoundException() {
        when(bookService.getBookById(1)).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> borrowReturnFacade.returnBook(1, 1));
    }
    @Test
    void returnBookThrowsPatronNotFoundException() {
        Book book = new Book();
        when(bookService.getBookById(1)).thenReturn(book);
        when(patronService.getPatronById(1)).thenReturn(null);
        assertThrows(PatronNotFoundException.class, () -> borrowReturnFacade.returnBook(1, 1));
    }
    @Test
    void returnBookThrowsBorrowInvalidException() {
        Book book = new Book();
        Patron patron = new Patron();
        when(bookService.getBookById(1)).thenReturn(book);
        when(patronService.getPatronById(1)).thenReturn(patron);
        when(borrowingRecordRepository.findByBookIdAndPatronId(1, 1)).thenReturn(Collections.emptyList());
        assertThrows(BorrowInvalidException.class, () -> borrowReturnFacade.returnBook(1, 1));
    }
}

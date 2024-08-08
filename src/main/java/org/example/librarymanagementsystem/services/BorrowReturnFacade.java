package org.example.librarymanagementsystem.services;
import org.example.librarymanagementsystem.entities.Book;
import org.example.librarymanagementsystem.entities.BorrowRecord;
import org.example.librarymanagementsystem.entities.Patron;
import org.example.librarymanagementsystem.entities.ReturnRecord;
import org.example.librarymanagementsystem.exceptions.BookNotFoundException;
import org.example.librarymanagementsystem.exceptions.BorrowInvalidException;
import org.example.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.example.librarymanagementsystem.repositories.BorrowingRecordRepository;
import org.example.librarymanagementsystem.repositories.ReturnRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
@Service
public class BorrowReturnFacade {
    @Autowired
    private BookService bookService;
    @Autowired
    private PatronService patronService;
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private ReturnRecordRepository returnRecordRepository;
    private void checkBookAndPatron(Book book, Patron patron) {
        if (book == null)
            throw new BookNotFoundException("Book not found");
        if (patron == null)
            throw new PatronNotFoundException("Patron not found");
    }
    public void borrowBook(int bookId, int patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);
        checkBookAndPatron(book, patron);
        List<BorrowRecord> list = borrowingRecordRepository.findByBookIDAndPatronID(bookId, patronId);
        if (!list.isEmpty() && list.get(list.size() - 1).getReturnDate() == null) {
            throw new BorrowInvalidException("Book is already borrowed by the patron");
        }
        BorrowRecord borrowRecord = new BorrowRecord(book, patron, new Date(), null);
        borrowingRecordRepository.save(borrowRecord);
    }
    public void returnBook(int bookId, int patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);
        checkBookAndPatron(book, patron);
        List<BorrowRecord> list = borrowingRecordRepository.findByBookIDAndPatronID(bookId, patronId);
        if (list.isEmpty() || list.get(list.size() - 1).getReturnDate() != null) {
            throw new BorrowInvalidException("Book is not borrowed by the patron");
        }
        BorrowRecord borrowRecord = list.get(list.size() - 1);
        borrowingRecordRepository.deleteById(borrowRecord.getID());
        borrowingRecordRepository.save(borrowRecord);
        ReturnRecord returnRecord = new ReturnRecord(book, patron, borrowRecord.getBorrowDate(), new Date());
        returnRecordRepository.save(returnRecord);
    }
}

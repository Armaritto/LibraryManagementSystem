package org.example.librarymanagementsystem.entities;
import jakarta.persistence.*;
import java.util.Date;
@Entity
public class BorrowRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;
    private Date borrowDate;
    private Date returnDate;

    public BorrowRecord() {
    }
}

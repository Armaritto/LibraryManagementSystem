package org.example.librarymanagementsystem.controllers;
import org.example.librarymanagementsystem.services.BorrowReturnFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/borrow")
public class BorrowingController {
    @Autowired
    private BorrowReturnFacade borrowReturnFacade;
    @PostMapping("/{bookId}/patron/{patronId}")
    public void borrowBook(@PathVariable int bookId, @PathVariable int patronId) {
        borrowReturnFacade.borrowBook(bookId, patronId);
    }
}

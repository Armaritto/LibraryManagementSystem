package org.example.librarymanagementsystem.controllers;
import org.example.librarymanagementsystem.services.BorrowReturnFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/return")
public class ReturningController {
    @Autowired
    private BorrowReturnFacade borrowReturnFacade;
    @PostMapping("/{bookId}/patron/{patronId}")
    public void returnBook(@PathVariable int bookId, @PathVariable int patronId) {
        borrowReturnFacade.returnBook(bookId, patronId);
    }
}

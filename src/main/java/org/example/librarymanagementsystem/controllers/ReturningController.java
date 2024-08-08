package org.example.librarymanagementsystem.controllers;

import org.example.librarymanagementsystem.services.BorrowReturnFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/return")
public class ReturningController {
    @Autowired
    private BorrowReturnFacade borrowReturnFacade;
    @PutMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable int bookId, @PathVariable int patronId) {
        borrowReturnFacade.returnBook(bookId, patronId);
        return ResponseEntity.ok("Book returned successfully");
    }
}

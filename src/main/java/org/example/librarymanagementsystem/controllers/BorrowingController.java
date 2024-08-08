package org.example.librarymanagementsystem.controllers;

import org.example.librarymanagementsystem.services.BorrowReturnFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {
    @Autowired
    private BorrowReturnFacade borrowReturnFacade;
    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable int bookId, @PathVariable int patronId) {
        borrowReturnFacade.borrowBook(bookId, patronId);
        return ResponseEntity.ok("Book borrowed successfully");
    }
}

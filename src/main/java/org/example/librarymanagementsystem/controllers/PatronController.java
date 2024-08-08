package org.example.librarymanagementsystem.controllers;
import org.example.librarymanagementsystem.entities.Patron;
import org.example.librarymanagementsystem.exceptions.InvalidPatronException;
import org.example.librarymanagementsystem.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;
    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }
    @GetMapping("/{id}")
    public Patron getPatronById(@PathVariable int id) {
        return patronService.getPatronById(id);
    }
    @PostMapping
    public Patron addPatron(@RequestBody Map<String, Object> payload) {
        Patron patron = new Patron();
        patron.setName((String) payload.get("name"));
        patron.setAge((Integer) payload.get("age"));
        patron.setAddress((String) payload.get("address"));
        patron.setEmail((String) payload.get("email"));
        patron.setPhoneNumber((String) payload.get("phoneNumber"));
        return patronService.addPatron(patron);
    }
    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable int id, @RequestBody Patron patron) {
        return patronService.updatePatron(id, patron);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable int id) {
        patronService.deletePatron(id);
        return new ResponseEntity<>("Patron deleted successfully", HttpStatus.OK);
    }
    @ExceptionHandler(InvalidPatronException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPatronException(InvalidPatronException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

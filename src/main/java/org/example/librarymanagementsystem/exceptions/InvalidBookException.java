package org.example.librarymanagementsystem.exceptions;
public class InvalidBookException extends RuntimeException {
    public InvalidBookException(String message) {
        super(message);
    }
}

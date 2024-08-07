package org.example.librarymanagementsystem;
import org.example.librarymanagementsystem.controllers.PatronController;
import org.example.librarymanagementsystem.entities.Patron;
import org.example.librarymanagementsystem.exceptions.InvalidPatronException;
import org.example.librarymanagementsystem.services.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class PatronControllerTest {
    @Mock
    private PatronService patronService;
    @InjectMocks
    private PatronController patronController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllPatronsReturnsListOfPatrons() {
        List<Patron> patrons = List.of(new Patron(1, "John Doe", 30, "123 Main St", "john@example.com", "1234567890"));
        when(patronService.getAllPatrons()).thenReturn(patrons);

        List<Patron> result = patronController.getAllPatrons();

        assertEquals(patrons, result);
    }
    @Test
    void getPatronByIdReturnsPatron() {
        Patron patron = new Patron(1, "John Doe", 30, "123 Main St", "john@example.com", "1234567890");
        when(patronService.getPatronById(1)).thenReturn(patron);

        Patron result = patronController.getPatronById(1);

        assertEquals(patron, result);
    }
    @Test
    void addPatronReturnsAddedPatron() {
        Patron patron = new Patron(1, "John Doe", 30, "123 Main St", "john@example.com", "1234567890");
        when(patronService.addPatron(any(Patron.class))).thenReturn(patron);

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "John Doe");
        payload.put("age", 30);
        payload.put("address", "123 Main St");
        payload.put("email", "john@example.com");
        payload.put("phoneNumber", "1234567890");

        Patron result = patronController.addPatron(payload);

        assertEquals(patron, result);
    }
    @Test
    void updatePatronReturnsUpdatedPatron() {
        Patron patron = new Patron(1, "John Doe", 30, "123 Main St", "john@example.com", "1234567890");
        when(patronService.updatePatron(eq(1), any(Patron.class))).thenReturn(patron);

        Patron result = patronController.updatePatron(1, patron);

        assertEquals(patron, result);
    }
    @Test
    void deletePatronExecutesSuccessfully() {
        doNothing().when(patronService).deletePatron(1);

        patronController.deletePatron(1);

        verify(patronService, times(1)).deletePatron(1);
    }
    @Test
    void handleInvalidPatronExceptionReturnsBadRequestStatus() {
        InvalidPatronException ex = new InvalidPatronException("Invalid patron");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Map<String, String>> response = patronController.handleInvalidPatronException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid patron", response.getBody().get("error"));
    }
    @Test
    void handleGlobalExceptionReturnsInternalServerErrorStatus() {
        Exception ex = new Exception("Unexpected error");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Map<String, String>> response = patronController.handleGlobalException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().get("error"));
    }
}

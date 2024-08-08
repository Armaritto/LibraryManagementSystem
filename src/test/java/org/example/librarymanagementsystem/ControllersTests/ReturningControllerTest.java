package org.example.librarymanagementsystem.ControllersTests;
import org.example.librarymanagementsystem.exceptions.GlobalExceptionHandler;
import org.example.librarymanagementsystem.controllers.ReturningController;
import org.example.librarymanagementsystem.exceptions.BookNotFoundException;
import org.example.librarymanagementsystem.exceptions.BorrowInvalidException;
import org.example.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.example.librarymanagementsystem.services.BorrowReturnFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class ReturningControllerTest {
    @Mock
    private BorrowReturnFacade borrowReturnFacade;
    @InjectMocks
    private ReturningController returningController;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(returningController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void returnBookSuccessfully() throws Exception {
        mockMvc.perform(post("/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(borrowReturnFacade, times(1)).returnBook(1, 1);
    }
    @Test
    void returnBookWithInvalidBookId() throws Exception {
        doThrow(new BookNotFoundException("Book Not Found")).when(borrowReturnFacade).returnBook(999, 1);
        mockMvc.perform(post("/return/999/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(borrowReturnFacade, times(1)).returnBook(999, 1);
    }
    @Test
    void returnBookWithInvalidPatronId() throws Exception {
        doThrow(new PatronNotFoundException("Patron not found")).when(borrowReturnFacade).returnBook(1, 999);
        mockMvc.perform(post("/return/1/patron/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(borrowReturnFacade, times(1)).returnBook(1, 999);
    }
    @Test
    void returnBookWhenAlreadyReturned() throws Exception {
        doThrow(new BorrowInvalidException("Invalid Return")).when(borrowReturnFacade).returnBook(1, 1);
        MvcResult result = mockMvc.perform(post("/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(409, status); // Check if the status code is 409 (Conflict)
        verify(borrowReturnFacade, times(1)).returnBook(1, 1);
    }
}

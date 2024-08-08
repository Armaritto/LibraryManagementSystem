package org.example.librarymanagementsystem.ControllersTests;
import org.example.librarymanagementsystem.exceptions.GlobalExceptionHandler;
import org.example.librarymanagementsystem.controllers.BorrowingController;
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
class BorrowingControllerTest {
    @Mock
    private BorrowReturnFacade borrowReturnFacade;
    @InjectMocks
    private BorrowingController borrowingController;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void borrowBookSuccessfully() throws Exception {
        mockMvc.perform(post("/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(borrowReturnFacade, times(1)).borrowBook(1, 1);
    }
    @Test
    void borrowBookWithInvalidBookId() throws Exception {
        doThrow(new BookNotFoundException("Book Not Found")).when(borrowReturnFacade).borrowBook(999, 1);
        mockMvc.perform(post("/borrow/999/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(borrowReturnFacade, times(1)).borrowBook(999, 1);
    }
    @Test
    void borrowBookWithInvalidPatronId() throws Exception {
        doThrow(new PatronNotFoundException("Patron not found")).when(borrowReturnFacade).borrowBook(1, 999);
        mockMvc.perform(post("/borrow/1/patron/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(borrowReturnFacade, times(1)).borrowBook(1, 999);
    }
    @Test
    void borrowBookWhenAlreadyBorrowed() throws Exception {
        doThrow(new BorrowInvalidException("Invalid Borrow")).when(borrowReturnFacade).borrowBook(1, 1);
        MvcResult result = mockMvc.perform(post("/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println("HII");
        int status = result.getResponse().getStatus();
        assertEquals(409, status);
        verify(borrowReturnFacade, times(1)).borrowBook(1, 1);
    }
}

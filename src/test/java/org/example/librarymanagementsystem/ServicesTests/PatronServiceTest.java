package org.example.librarymanagementsystem.ServicesTests;
import org.example.librarymanagementsystem.entities.Patron;
import org.example.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.example.librarymanagementsystem.repositories.PatronRepository;
import org.example.librarymanagementsystem.services.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class PatronServiceTest {
    @Mock
    private PatronRepository patronRepository;
    @InjectMocks
    private PatronService patronService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllPatronsReturnsListOfPatrons() {
        List<Patron> patrons = List.of(new Patron(), new Patron());
        when(patronRepository.findAll()).thenReturn(patrons);
        List<Patron> result = patronService.getAllPatrons();
        assertEquals(2, result.size());
        verify(patronRepository, times(1)).findAll();
    }
    @Test
    void getPatronByIdReturnsPatronWhenFound() {
        Patron patron = new Patron();
        when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
        Patron result = patronService.getPatronById(1);
        assertEquals(patron, result);
        verify(patronRepository, times(1)).findById(1);
    }
    @Test
    void getPatronByIdReturnsNullWhenNotFound() {
        when(patronRepository.findById(1)).thenReturn(Optional.empty());
        Patron result = patronService.getPatronById(1);
        assertNull(result);
        verify(patronRepository, times(1)).findById(1);
    }
    @Test
    void addPatronSavesAndReturnsPatron() {
        Patron patron = new Patron();
        when(patronRepository.save(patron)).thenReturn(patron);
        Patron result = patronService.addPatron(patron);
        assertEquals(patron, result);
        verify(patronRepository, times(1)).save(patron);
    }
    @Test
    void updatePatronUpdatesAndReturnsPatron() {
        Patron existingPatron = new Patron();
        Patron newPatron = new Patron();
        newPatron.setAge(30);
        newPatron.setName("New Name");
        newPatron.setPhoneNumber("1234567890");
        newPatron.setEmail("new@example.com");
        newPatron.setAddress("New Address");
        when(patronRepository.findById(1)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.save(existingPatron)).thenReturn(existingPatron);
        Patron result = patronService.updatePatron(1, newPatron);
        assertEquals(existingPatron, result);
        assertEquals(30, existingPatron.getAge());
        assertEquals("New Name", existingPatron.getName());
        assertEquals("1234567890", existingPatron.getPhoneNumber());
        assertEquals("new@example.com", existingPatron.getEmail());
        assertEquals("New Address", existingPatron.getAddress());
        verify(patronRepository, times(1)).findById(1);
        verify(patronRepository, times(1)).save(existingPatron);
    }
    @Test
    void updatePatronThrowsExceptionWhenNotFound() {
        Patron newPatron = new Patron();
        when(patronRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatronNotFoundException.class, () -> patronService.updatePatron(1, newPatron));
        verify(patronRepository, times(1)).findById(1);
        verify(patronRepository, never()).save(newPatron);
    }
    @Test
    void deletePatronDeletesPatronWhenFound() {
        doNothing().when(patronRepository).deleteById(1);
        patronService.deletePatron(1);
        verify(patronRepository, times(1)).deleteById(1);
    }
}

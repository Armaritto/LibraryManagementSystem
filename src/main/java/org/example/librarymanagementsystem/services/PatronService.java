package org.example.librarymanagementsystem.services;

import org.example.librarymanagementsystem.entities.Patron;
import org.example.librarymanagementsystem.repositories.PatronRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatronService {
    private PatronRepository patronRepository;
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }
    public Patron getPatronById(int id) {
        return patronRepository.findById(id).orElse(null);
    }
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }
    public Patron updatePatron(int id, Patron newPatron) {
        Patron patron = patronRepository.findById(id).orElse(null);
        patron.setAge(newPatron.getAge());
        patron.setName(newPatron.getName());
        patron.setPhoneNumber(newPatron.getPhoneNumber());
        patron.setEmail(newPatron.getEmail());
        patron.setAddress(newPatron.getAddress());
        return patronRepository.save(patron);
    }
    public void deletePatron(int id) {
        patronRepository.deleteById(id);
    }
}

package org.example.librarymanagementsystem.repositories;
import org.example.librarymanagementsystem.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PatronRepository extends JpaRepository<Patron, Integer> {
}

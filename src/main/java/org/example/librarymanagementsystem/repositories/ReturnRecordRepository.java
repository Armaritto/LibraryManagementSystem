package org.example.librarymanagementsystem.repositories;
import org.example.librarymanagementsystem.entities.ReturnRecord;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReturnRecordRepository extends JpaRepository<ReturnRecord, Integer> {
}

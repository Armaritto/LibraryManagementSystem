package org.example.librarymanagementsystem.repositories;

import org.example.librarymanagementsystem.entities.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowRecord, Integer> {
    List<BorrowRecord> findByBookIdAndPatronId(int bookId, int patronId);
}

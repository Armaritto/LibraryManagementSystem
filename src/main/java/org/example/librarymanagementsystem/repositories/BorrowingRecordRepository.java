package org.example.librarymanagementsystem.repositories;

import org.example.librarymanagementsystem.entities.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowRecord, Integer> {
}

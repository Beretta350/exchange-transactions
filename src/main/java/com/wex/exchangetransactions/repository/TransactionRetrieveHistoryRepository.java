package com.wex.exchangetransactions.repository;

import com.wex.exchangetransactions.model.TransactionRetrieveHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRetrieveHistoryRepository extends JpaRepository<TransactionRetrieveHistoryModel, Long> {
}

package com.wex.exchangetransactions.repository;

import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransactionModel, UUID> {
}

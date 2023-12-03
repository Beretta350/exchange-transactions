package com.wex.exchangetransactions.repository;

import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransactionModel, UUID> {
}

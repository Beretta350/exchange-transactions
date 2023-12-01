package com.wex.exchangetransactions.repository;

import com.wex.exchangetransactions.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository  extends JpaRepository<TransactionModel, UUID> {
}

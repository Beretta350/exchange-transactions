package com.wex.exchangetransactions.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Table(name = "transaction")
@RequiredArgsConstructor
public class TransactionModel {
}

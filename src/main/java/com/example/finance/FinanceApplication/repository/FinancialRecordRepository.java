package com.example.finance.FinanceApplication.repository;

import com.example.finance.FinanceApplication.model.FinancialRecords;
import com.example.finance.FinanceApplication.model.TransactionType;
import com.example.finance.FinanceApplication.model.User;

import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface FinancialRecordRepository  extends JpaRepository<FinancialRecords, Long> {

    List<FinancialRecords> findByDate(LocalDate date);
    List<FinancialRecords> findByCategory(String category);
    List<FinancialRecords> findByTransactionType(TransactionType transactionType);
    List<FinancialRecords> findByCreatedBy(User createdBy);


}

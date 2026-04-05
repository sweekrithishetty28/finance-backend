package com.example.finance.FinanceApplication.service;

import com.example.finance.FinanceApplication.model.FinancialRecords;
import com.example.finance.FinanceApplication.model.TransactionType;
import com.example.finance.FinanceApplication.model.User;
import com.example.finance.FinanceApplication.repository.FinancialRecordRepository;

import com.example.finance.FinanceApplication.repository.UserRepository;

import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialRecordService {
    private final FinancialRecordRepository financialRecordRepository;
    private final UserRepository userRepository;

    public FinancialRecordService(FinancialRecordRepository financialRecordRepository, UserRepository userRepository){
        this.financialRecordRepository=financialRecordRepository;
        this.userRepository = userRepository;
    }

    public FinancialRecords createRecords(FinancialRecords financialRecords){
        User user=userRepository.findById(
                financialRecords.getCreatedBy().getId()
        ).orElse(null);
        financialRecords.setCreatedBy(user);
        return financialRecordRepository.save(financialRecords);

    }
    public List<FinancialRecords> getAllRecords(){

        List<FinancialRecords> allRecords=financialRecordRepository.findAll();
        List<FinancialRecords>  active=new ArrayList<>();
        for(FinancialRecords record:allRecords){
            if(record.isDeleted()==false){
                active.add(record);
            }
        }
        return active;
    }
    public FinancialRecords getRecordsById(Long id){
        return financialRecordRepository.findById(id).orElse(null);
    }
    public FinancialRecords updateRecords(Long id,FinancialRecords financialRecords){
        FinancialRecords existing=financialRecordRepository.findById(id).orElse(null);
        if(existing==null){
            return null;
        }
        existing.setAmount(financialRecords.getAmount());
        existing.setCategory(financialRecords.getCategory());
        existing.setDate(financialRecords.getDate());
        existing.setDescription(financialRecords.getDescription());
        existing.setTransactionType(financialRecords.getTransactionType());
        return financialRecordRepository.save(existing);

    }
    public String deleteRecordsById(Long id){
       FinancialRecords records=financialRecordRepository.findById(id).orElse(null);
       if(records==null){
           return "Record not Found";
       }
       records.setDeleted(true);
       financialRecordRepository.save(records);
       return "Record Successfully deleted";
    }
    public List<FinancialRecords> getRecordByDate(LocalDate date){
        return financialRecordRepository.findByDate(date);
    }
    public List<FinancialRecords> getRecordByCategory(String category){
        return financialRecordRepository.findByCategory(category);
    }
    public List<FinancialRecords> getRecordByTransactionType(TransactionType transactionType){
        return financialRecordRepository.findByTransactionType(transactionType);
    }

    public List<FinancialRecords> getRecordByUser(User user){

        return financialRecordRepository.findByCreatedBy(user);
    }



}

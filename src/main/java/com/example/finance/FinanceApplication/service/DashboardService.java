package com.example.finance.FinanceApplication.service;

import com.example.finance.FinanceApplication.controller.FinancialRecordController;
import com.example.finance.FinanceApplication.model.FinancialRecords;
import com.example.finance.FinanceApplication.model.TransactionType;
import com.example.finance.FinanceApplication.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class DashboardService {
    private final FinancialRecordRepository financialRecordRepository;
    public DashboardService(FinancialRecordRepository financialRecordRepository){
        this.financialRecordRepository=financialRecordRepository;
    }
    public Double getTotalIncome(){
        List<FinancialRecords> all= financialRecordRepository.findAll();
        double totalIncome = 0.0;
        for (FinancialRecords r : all) {
            if (r.getTransactionType() == TransactionType.INCOME) {
                double amount = r.getAmount();
                totalIncome += amount;
            }
        }
        return totalIncome;

    }

    public Double getTotalExpense(){
        List<FinancialRecords> all=financialRecordRepository.findAll();
        double totalExpense = 0.0;
        for (FinancialRecords r : all) {
            if (r.getTransactionType() == TransactionType.EXPENSE) {
                double amount = r.getAmount();
                totalExpense += amount;
            }
        }
        return totalExpense;
    }

    public Double getNetBalance(){
        return getTotalIncome()-getTotalExpense();
    }
    public Map<String,Double> getCategoryTotals(){
    List<FinancialRecords> all=financialRecordRepository.findAll();
    Map<String,Double> categoryTotals=new HashMap<>();
       for(FinancialRecords r:all){
            String category=r.getCategory();
            Double amount=r.getAmount();
            if(categoryTotals.containsKey(category)){
                categoryTotals.put(category,categoryTotals.get(category)+amount);
            }
            else{
                categoryTotals.put(category,amount);
            }
       }
        return categoryTotals;
    }

    public List<FinancialRecords> getRecentransactions() {
        List<FinancialRecords> all = financialRecordRepository.findAll();
        all.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        List<FinancialRecords> recentTransactions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i < all.size()) {
                recentTransactions.add(all.get(i));
            }
        }
        return recentTransactions;
    }
    public Map<String,Double> getMonthlyTrends(){
        List<FinancialRecords> all=financialRecordRepository.findAll();
        Map<String,Double> monthlyTotals=new HashMap<>();
        for(FinancialRecords r:all){
            String month=r.getDate().getMonth().toString();
            Double amount=r.getAmount();

            if(monthlyTotals.containsKey(month)){
                monthlyTotals.put(month,monthlyTotals.get(month)+amount);
            }
            else{
                monthlyTotals.put(month,amount);
            }
        }
        return monthlyTotals;
    }
    public Map<String,Object> getSummary(){
        Map<String,Object> summary=new HashMap<>();
        summary.put("totalIncome",getTotalIncome());
        summary.put("totalExpense",getTotalExpense());
        summary.put("netBalance",getNetBalance());
        summary.put("categoryTotals",getCategoryTotals());
        summary.put("recentTransactions",getRecentransactions());
        return summary;
    }
}


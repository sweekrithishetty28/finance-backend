package com.example.finance.FinanceApplication.controller;

import com.example.finance.FinanceApplication.model.FinancialRecords;
import com.example.finance.FinanceApplication.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
  private final DashboardService dashboardService;
  public DashboardController(DashboardService dashboardService){
      this.dashboardService=dashboardService;
  }
  @GetMapping("/summary")
  public ResponseEntity<Map<String,Object>> getSummary(){
    return ResponseEntity.ok(dashboardService.getSummary());
  }
  @GetMapping("/total-income")
    public ResponseEntity<Double> totalIncome(){
    return ResponseEntity.ok(dashboardService.getTotalIncome());
  }
  @GetMapping("/total-expenses")
  public ResponseEntity<Double> totalExpense(){
    return ResponseEntity.ok(dashboardService.getTotalExpense());
  }
  @GetMapping("/net-balance")
  public ResponseEntity<Double> netBalance(){
    return ResponseEntity.ok(dashboardService.getNetBalance());
  }

  @GetMapping("/category-totals")
    public ResponseEntity<Map<String,Double>> getCategoryTotals(){
      return ResponseEntity.ok( dashboardService.getCategoryTotals());
    }
  @GetMapping("/recent-transactions")
  public ResponseEntity<List<FinancialRecords>> getRecentTransactions(){
    return ResponseEntity.ok(dashboardService.getRecentransactions());
  }
  @GetMapping("/monthly-trends")
  public ResponseEntity<Map<String,Double>> getMonthlyTrends(){

    return ResponseEntity.ok(dashboardService.getMonthlyTrends());
  }

}

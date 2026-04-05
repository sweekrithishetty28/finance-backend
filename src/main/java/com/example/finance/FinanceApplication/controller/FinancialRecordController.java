package com.example.finance.FinanceApplication.controller;

import com.example.finance.FinanceApplication.model.FinancialRecords;
import com.example.finance.FinanceApplication.model.TransactionType;

import com.example.finance.FinanceApplication.model.User;
import com.example.finance.FinanceApplication.repository.UserRepository;
import com.example.finance.FinanceApplication.service.FinancialRecordService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/financial-records")
public class FinancialRecordController {
    private final FinancialRecordService financialRecordService;
    private final UserRepository userRepository;
    public FinancialRecordController(FinancialRecordService financialRecordService,UserRepository userRepository){
        this.financialRecordService=financialRecordService;
        this.userRepository=userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createRecord(@Valid @RequestBody FinancialRecords financialRecords){

        FinancialRecords records=financialRecordService.createRecords(financialRecords);
        return ResponseEntity.status(HttpStatus.CREATED).body(records);

    }

    @GetMapping
    public ResponseEntity<?> getAlRecords(){
        List<FinancialRecords> financialRecords=financialRecordService.getAllRecords();
        return ResponseEntity.ok(financialRecords);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  getRecordById(@PathVariable Long id){
       FinancialRecords record=financialRecordService.getRecordsById(id);
       if(record==null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found with id: "+id);
       }
       return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecord(@PathVariable Long id,@RequestBody FinancialRecords financialRecords){
        FinancialRecords updated=financialRecordService.updateRecords(id,financialRecords);
        if(updated==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not Found with id: "+id);

        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteById(@PathVariable Long id){
        FinancialRecords records=financialRecordService.getRecordsById(id);
        if(records==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not Found with id: "+id);

        }
        financialRecordService.deleteRecordsById(id);
        return ResponseEntity.ok("Deleted record with id :"+id+"  Sucessfully");

    }
    @GetMapping("/date/{date}")
    public ResponseEntity<List<FinancialRecords>> getRecordByDate(@PathVariable LocalDate date){

       return ResponseEntity.ok(financialRecordService.getRecordByDate(date));
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<FinancialRecords>> getRecordByCategory(@PathVariable String category){
        return  ResponseEntity.ok(financialRecordService.getRecordByCategory(category));
    }
    @GetMapping("/type/{transactionType}")
    public ResponseEntity<List<FinancialRecords>> getRecordByTranscationType(@PathVariable TransactionType transactionType){
        return ResponseEntity.ok(financialRecordService.getRecordByTransactionType(transactionType));
    }

    @GetMapping("/my-records")
    public ResponseEntity<List<FinancialRecords>> getMyRecords(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userRepository.findByEmail(email);
        return ResponseEntity.ok(financialRecordService.getRecordByUser(user));
    }
}

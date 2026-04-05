package com.example.finance.FinanceApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="financial_records")
public class FinancialRecords {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount Cannot   be Null")
    @Positive(message = "Amount must be positive")
    private Double amount;


    @NotNull(message = "Transaction type cannot be null")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotBlank(message ="Category cannot be empty" )
    private String category;
    @NotNull(message ="Date cannot be empty" )
    private LocalDate date;
    private String description;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"password"})
    private User createdBy;

    private boolean isDeleted;


}

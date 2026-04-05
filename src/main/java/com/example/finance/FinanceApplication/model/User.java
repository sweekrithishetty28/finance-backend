package com.example.finance.FinanceApplication.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Name Cannot be empty")
    private String name;

    @NotBlank(message ="Email cannot be Empty")
    @Email(message = "Email should be valid")
    @Column(unique=true)
    private String email;

    @NotBlank(message = "Password cannot be Empty")
    private String password;

    @NotNull(message = "Role Cannot be Null")
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull(message = "Status Cannot be Null")
    @Enumerated(EnumType.STRING)
    private Status status;
}

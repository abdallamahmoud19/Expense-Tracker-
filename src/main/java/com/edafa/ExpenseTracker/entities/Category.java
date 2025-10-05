package com.edafa.ExpenseTracker.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private BaseTransaction.TransactionType type; // INCOME or EXPENSE

    // Category belongs to a user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    // Transactions under this category
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BaseTransaction> transactions;

//    public enum TransactionType {
//        INCOME, EXPENSE
//    }
}

package com.edafa.ExpenseTracker.Repository;

import com.edafa.ExpenseTracker.entities.Expense;
import com.edafa.ExpenseTracker.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser_Id(Long userId);



}

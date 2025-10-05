package com.edafa.ExpenseTracker.Repository;

import com.edafa.ExpenseTracker.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUser_Id(Long userId);



}

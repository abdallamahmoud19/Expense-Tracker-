package com.edafa.ExpenseTracker.services;

import com.edafa.ExpenseTracker.Repository.IncomeRepository;
import com.edafa.ExpenseTracker.Repository.ExpenseRepository;
import com.edafa.ExpenseTracker.dto.*;
import com.edafa.ExpenseTracker.dto.response.*;
import com.edafa.ExpenseTracker.entities.Income;
import com.edafa.ExpenseTracker.entities.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public DashboardResponse getDashboard(Long userId) {
        List<Income> incomes = incomeRepository.findByUser_Id(userId);
        List<Expense> expenses = expenseRepository.findByUser_Id(userId);

        // totals
        BigDecimal totalIncome = incomes.stream()
                .map(i -> BigDecimal.valueOf(i.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(e -> BigDecimal.valueOf(e.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        LocalDate now = LocalDate.now();
        LocalDate last30Days = now.minusDays(30);
        LocalDate last60Days = now.minusDays(60);

        // income filters
        List<IncomeResponseDto> last30DaysIncome = incomes.stream()
                .filter(i -> !i.getDate().toInstant()
                        .isBefore(last30Days.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()))
                .map(this::mapToIncomeDto)
                .collect(Collectors.toList());

        List<IncomeResponseDto> last60DaysIncome = incomes.stream()
                .filter(i -> !i.getDate().toInstant()
                        .isBefore(last60Days.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()))
                .map(this::mapToIncomeDto)
                .collect(Collectors.toList());

        List<IncomeResponseDto> allIncome = incomes.stream()
                .map(this::mapToIncomeDto)
                .collect(Collectors.toList());

        // expense filters
        List<ExpenseResponseDto> last30DaysExpense = expenses.stream()
                .filter(e -> !e.getDate().toInstant()
                        .isBefore(last30Days.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()))
                .map(this::mapToExpenseDto)
                .collect(Collectors.toList());

        List<ExpenseResponseDto> last60DaysExpense = expenses.stream()
                .filter(e -> !e.getDate().toInstant()
                        .isBefore(last60Days.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()))
                .map(this::mapToExpenseDto)
                .collect(Collectors.toList());

        List<ExpenseResponseDto> allExpense = expenses.stream()
                .map(this::mapToExpenseDto)
                .collect(Collectors.toList());

        // build responses
        IncomeDashboardResponse incomeResponse = IncomeDashboardResponse.builder()
                .totalIncome(totalIncome)
                .last30DaysIncomes(last30DaysIncome)
                .last60DaysIncomes(last60DaysIncome)
                .allIncomes(allIncome)
                .build();

        ExpenseDashboardResponse expenseResponse = ExpenseDashboardResponse.builder()
                .totalExpense(totalExpense)
                .last30DaysExpenses(last30DaysExpense)
                .last60DaysExpenses(last60DaysExpense)
                .allExpenses(allExpense)
                .build();

        return DashboardResponse.builder()
                .income(incomeResponse)
                .expense(expenseResponse)
                .netBalance(netBalance)
                .build();
    }

    private IncomeResponseDto mapToIncomeDto(Income income) {
        return IncomeResponseDto.builder()
                .id(income.getId())
                .userId(income.getUser().getId())
                .description(income.getDescription())
                .icon(income.getIcon())
                .amount(income.getAmount())
                .source(income.getSource())
                .date(income.getDate())
                .createdAt(income.getCreatedAt())
                .updatedAt(income.getUpdatedAt())
                .build();
    }

    private ExpenseResponseDto mapToExpenseDto(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .userId(expense.getUser().getId())
                .description(expense.getDescription())
                .icon(expense.getIcon())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .build();
    }
}

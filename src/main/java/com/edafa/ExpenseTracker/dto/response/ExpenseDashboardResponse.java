package com.edafa.ExpenseTracker.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ExpenseDashboardResponse {
    private BigDecimal totalExpense;
    private List<ExpenseResponseDto> last30DaysExpenses;
    private List<ExpenseResponseDto> last60DaysExpenses;
    private List<ExpenseResponseDto> allExpenses;
}

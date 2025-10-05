package com.edafa.ExpenseTracker.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class IncomeDashboardResponse {
    private BigDecimal totalIncome;
    private List<IncomeResponseDto> last30DaysIncomes;
    private List<IncomeResponseDto> last60DaysIncomes;
    private List<IncomeResponseDto> allIncomes;
}

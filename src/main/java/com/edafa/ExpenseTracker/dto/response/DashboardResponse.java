package com.edafa.ExpenseTracker.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DashboardResponse {
    private IncomeDashboardResponse income;
    private ExpenseDashboardResponse expense;
    private BigDecimal netBalance;
}

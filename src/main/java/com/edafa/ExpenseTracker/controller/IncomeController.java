package com.edafa.ExpenseTracker.controller;

import com.edafa.ExpenseTracker.dto.ErrorResponse;

import com.edafa.ExpenseTracker.dto.response.IncomeResponseDto;
import com.edafa.ExpenseTracker.entities.Income;
import com.edafa.ExpenseTracker.entities.User;
import com.edafa.ExpenseTracker.security.AppUserDetails;
import com.edafa.ExpenseTracker.services.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;

    // POST /income/add
    @PostMapping("/add")
    public ResponseEntity<?> addIncome(@RequestBody Income income,
                                        Authentication authentication) {
        try {
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();

            User user = new User();
            user.setId(userId);
            income.setUser(user);

            if (income.getDate() == null) {
                income.setDate(new Date());
            }

            IncomeResponseDto savedIncome = incomeService.saveIncome(income);
            return ResponseEntity.ok(savedIncome);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Server error: " + e.getMessage())
                            .build());
        }
    }

    // GET /income/getall
    @GetMapping("/getall")
    public ResponseEntity<List<IncomeResponseDto>> getAllIncome() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    // DELETE /income/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Long id) {
        try {
            incomeService.deleteIncome(id);
            return ResponseEntity.ok("Deleted income with id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .build()
                    );
        }
    }

    // GET /income/export
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportIncome() throws IOException {
        ByteArrayInputStream inputStream = incomeService.exportToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=incomes.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}

package com.edafa.ExpenseTracker.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edafa.ExpenseTracker.dto.ErrorResponse;
import com.edafa.ExpenseTracker.dto.response.ExpenseResponseDto;
import com.edafa.ExpenseTracker.entities.Expense;
import com.edafa.ExpenseTracker.entities.User;
import com.edafa.ExpenseTracker.security.AppUserDetails;
import com.edafa.ExpenseTracker.services.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    // POST /expense/add
    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody Expense expense,
                                        Authentication authentication) {
        try {
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();

            User user = new User();
            user.setId(userId);
            expense.setUser(user);

            if (expense.getDate() == null) {
                expense.setDate(new Date());
            }

            ExpenseResponseDto savedExpense = expenseService.saveExpense(expense);
            return ResponseEntity.ok(savedExpense);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Server error: " + e.getMessage())
                            .build());
        }
    }

    // GET /expense/getall
    @GetMapping("/getall")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpense(Authentication authentication) {
        try {
            return ResponseEntity.ok(expenseService.getAllExpenses());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // DELETE /expense/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok("Deleted expense with id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .build()
                    );
        }
    }

    // PUT /expense/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody Expense expense,
                                         Authentication authentication) {
        try {
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();

            User user = new User();
            user.setId(userId);
            expense.setUser(user);
            expense.setId(id);

            ExpenseResponseDto updatedExpense = expenseService.saveExpense(expense);
            return ResponseEntity.ok(updatedExpense);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Server error: " + e.getMessage())
                            .build());
        }
    }

    // GET /expense/export
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportExpense() throws IOException {
        ByteArrayInputStream inputStream = expenseService.exportToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}

package com.edafa.ExpenseTracker.services;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.edafa.ExpenseTracker.Repository.ExpenseRepository;
import com.edafa.ExpenseTracker.dto.response.ExpenseResponseDto;
import com.edafa.ExpenseTracker.entities.Expense;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;


    private ExpenseResponseDto mapToDto(Expense expense) {
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

    public ExpenseResponseDto saveExpense(Expense expense) {
        Expense saved = expenseRepository.save(expense);
        return mapToDto(saved);
    }

    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDto> findAllByUserId(Long userId) {
        return expenseRepository.findByUser_Id(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid ID");
        }
        expenseRepository.deleteById(id);
    }

    public ByteArrayInputStream exportToExcel() throws IOException {
        List<Expense> expenses = expenseRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expenses");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("User ID");
        headerRow.createCell(2).setCellValue("Amount");
        headerRow.createCell(3).setCellValue("Date");
        headerRow.createCell(4).setCellValue("category");
        headerRow.createCell(5).setCellValue("Description");

        // Data
        int rowIdx = 1;
        for (Expense expense : expenses) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(expense.getId());
            row.createCell(1).setCellValue(expense.getUser().getId());
            row.createCell(2).setCellValue(expense.getAmount());
            row.createCell(3).setCellValue(expense.getDate().toString());
            row.createCell(4).setCellValue(expense.getCategory());
            row.createCell(5).setCellValue(expense.getDescription());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream exportToExcelByUserId(Long userId) throws IOException {
        List<Expense> expenses = expenseRepository.findByUser_Id(userId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expenses");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Amount");
        headerRow.createCell(2).setCellValue("Date");
        headerRow.createCell(3).setCellValue("Category");
        headerRow.createCell(4).setCellValue("Description");
        headerRow.createCell(5).setCellValue("Icon");

        // Data
        int rowIdx = 1;
        for (Expense expense : expenses) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(expense.getId());
            row.createCell(1).setCellValue(expense.getAmount());
            row.createCell(2).setCellValue(expense.getDate().toString());
            row.createCell(3).setCellValue(expense.getCategory());
            row.createCell(4).setCellValue(expense.getDescription());
            row.createCell(5).setCellValue(expense.getIcon());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}

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

import com.edafa.ExpenseTracker.Repository.IncomeRepository;
import com.edafa.ExpenseTracker.dto.response.IncomeResponseDto;
import com.edafa.ExpenseTracker.entities.Income;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;


    private IncomeResponseDto mapToDto(Income income) {
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

    public IncomeResponseDto saveIncome(Income income) {
        Income saved = incomeRepository.save(income);
        return mapToDto(saved);
    }

    public List<IncomeResponseDto> getAllIncomes() {
        return incomeRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<IncomeResponseDto> findAllByUserId(Long userId) {
        return incomeRepository.findByUser_Id(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public void deleteIncome(Long id) {
        if (!incomeRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid ID");
        }
        incomeRepository.deleteById(id);
    }

    public ByteArrayInputStream exportToExcel() throws IOException {
        List<Income> incomes = incomeRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Incomes");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("User ID");
        headerRow.createCell(2).setCellValue("Amount");
        headerRow.createCell(3).setCellValue("Date");
        headerRow.createCell(4).setCellValue("Source");
        headerRow.createCell(5).setCellValue("Description");

        // Data
        int rowIdx = 1;
        for (Income income : incomes) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(income.getId());
            row.createCell(1).setCellValue(income.getUser().getId());
            row.createCell(2).setCellValue(income.getAmount());
            row.createCell(3).setCellValue(income.getDate().toString());
            row.createCell(4).setCellValue(income.getSource());
            row.createCell(5).setCellValue(income.getDescription());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream exportToExcelByUserId(Long userId) throws IOException {
        List<Income> incomes = incomeRepository.findByUser_Id(userId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Incomes");

        // Header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Amount");
        headerRow.createCell(2).setCellValue("Date");
        headerRow.createCell(3).setCellValue("Source");
        headerRow.createCell(4).setCellValue("Description");
        headerRow.createCell(5).setCellValue("Icon");

        // Data
        int rowIdx = 1;
        for (Income income : incomes) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(income.getId());
            row.createCell(1).setCellValue(income.getAmount());
            row.createCell(2).setCellValue(income.getDate().toString());
            row.createCell(3).setCellValue(income.getSource());
            row.createCell(4).setCellValue(income.getDescription());
            row.createCell(5).setCellValue(income.getIcon());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}

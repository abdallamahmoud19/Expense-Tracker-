package com.edafa.ExpenseTracker.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login/view")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard/view")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/incomes/view")
    public String incomesPage() {
        return "incomes";
    }

    @GetMapping("/expenses/view")
    public String expensesPage() {
        return "expenses";
    }

    @GetMapping("/admin/view")
    public String adminPage() {
        return "admin";
    }
}

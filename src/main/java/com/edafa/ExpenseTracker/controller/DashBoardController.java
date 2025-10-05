package com.edafa.ExpenseTracker.controller;

import com.edafa.ExpenseTracker.Repository.UserRepository;
import com.edafa.ExpenseTracker.dto.response.DashboardResponse;
import com.edafa.ExpenseTracker.security.AppUserDetails;
import com.edafa.ExpenseTracker.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        Long userId;

        if (principal instanceof AppUserDetails) {
            AppUserDetails userDetails = (AppUserDetails) principal;
            userId = userDetails.getId();
        } else if (principal instanceof String) {
            String email = (String) principal;
            userId = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email))
                    .getId();
        } else {
            throw new RuntimeException("Invalid authentication principal type: " + principal.getClass());
        }

        return ResponseEntity.ok(dashboardService.getDashboard(userId));
    }

}

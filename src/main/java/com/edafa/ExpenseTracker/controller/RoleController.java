package com.edafa.ExpenseTracker.controller;


import com.edafa.ExpenseTracker.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
private final RoleService roleService;

@GetMapping("")
public ResponseEntity<?> findAll(){
    return ResponseEntity.ok(roleService.findAll());
}
@GetMapping("/{id}")
public ResponseEntity<?> findById(@PathVariable Long id){
    return ResponseEntity.ok(roleService.findById(id));
}


}

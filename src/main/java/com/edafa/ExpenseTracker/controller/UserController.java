package com.edafa.ExpenseTracker.controller;

import com.edafa.ExpenseTracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
private final UserService userService;

@GetMapping("")
public ResponseEntity<?> findAll(){
    return ResponseEntity.ok(userService.findAll());
}
@GetMapping("/{id}")
public ResponseEntity<?> findById(@PathVariable Long id){
    return ResponseEntity.ok(userService.findById(id));
}


}

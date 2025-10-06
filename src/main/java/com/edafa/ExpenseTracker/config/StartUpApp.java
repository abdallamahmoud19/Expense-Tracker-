package com.edafa.ExpenseTracker.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.edafa.ExpenseTracker.entities.Role;
import com.edafa.ExpenseTracker.entities.User;
import com.edafa.ExpenseTracker.services.RoleService;
import com.edafa.ExpenseTracker.services.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartUpApp implements CommandLineRunner {

    private final UserService userService;

    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {


        if (roleService.findAll().isEmpty()) {
            roleService.save(new Role(null, "admin"));
            roleService.save(new Role(null, "user"));
            roleService.save(new Role(null, "employee"));
        }


        if (userService.findAll().isEmpty()) {

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleService.findByName("admin"));

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleService.findByName("user"));

            Set<Role>  empRoles = new HashSet<>();
            empRoles.add(roleService.findByName("employee"));

            // Create admin user
            userService.save(new User(null, "Admin", "User", "admin@admin.com", "admin123", adminRoles));
            
            // Create regular users
            userService.save(new User(null, "Ali", "Mohamed", "ali.mohamed@gmail.com", "123", userRoles));
            userService.save(new User(null, "Sara", "Ahmed", "sara.ahmed@gmail.com", "123", userRoles));
            userService.save(new User(null, "Omar", "Hassan", "omar.hassan@gmail.com", "123", userRoles));
            userService.save(new User(null, "Mona", "Khaled", "mona.khaled@gmail.com", "123", userRoles));
            userService.save(new User(null, "Youssef", "Ibrahim", "youssef.ibrahim@gmail.com", "123", userRoles));
            userService.save(new User(null, "Fatma", "Sayed", "fatma.sayed@gmail.com", "123", userRoles));
            userService.save(new User(null, "Khaled", "Mahmoud", "khaled.mahmoud@gmail.com", "123", userRoles));
            userService.save(new User(null, "Nour", "Adel", "nour.adel@gmail.com", "123", userRoles));
            userService.save(new User(null, "Hassan", "Ali", "hassan.ali@gmail.com", "123", userRoles));
            userService.save(new User(null, "Layla", "Mostafa", "layla.mostafa@gmail.com", "123", userRoles));
        }

    }

}
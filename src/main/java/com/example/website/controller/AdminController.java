package com.example.website.controller;


import com.example.website.entity.User;
import com.example.website.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/sellers/{sellerId}/approve")
    public ResponseEntity<User> approveSeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(adminService.approveSeller(sellerId));
    }

    @DeleteMapping("/sellers/{sellerId}/reject")
    public ResponseEntity<Void> rejectSeller(@PathVariable Long sellerId) {
        adminService.rejectSeller(sellerId);
        return ResponseEntity.noContent().build();
    }
}

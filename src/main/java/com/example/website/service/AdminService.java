package com.example.website.service;





import com.example.website.entity.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    User approveSeller(Long sellerId);
    void rejectSeller(Long sellerId);
}

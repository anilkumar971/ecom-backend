package com.example.website.service;

import com.example.website.entity.User;
import com.example.website.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User approveSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        if (!"SELLER".equals(seller.getRole())) {
            throw new IllegalArgumentException("User is not a seller");
        }

        // Perform approval actions (e.g., setting a status flag, sending a notification)
        // For simplicity, we'll assume a `status` field exists and update it.
        seller.setRole("APPROVED_SELLER");
        return userRepository.save(seller);
    }

    @Override
    public void rejectSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        if (!"SELLER".equals(seller.getRole())) {
            throw new IllegalArgumentException("User is not a seller");
        }

        // Delete seller or mark as rejected
        userRepository.delete(seller);
    }
}

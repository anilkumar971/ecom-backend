package com.example.website.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 500)
    private String description;
    @Column(nullable = false)
    private Integer availableStock;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
}

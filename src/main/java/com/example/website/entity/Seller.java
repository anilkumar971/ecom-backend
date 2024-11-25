package com.example.website.entity;



import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products;
}

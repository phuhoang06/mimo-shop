package org.mimoshop.model;


import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Tên danh mục nên là duy nhất
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // cascade: Khi xóa Category, xóa các Product liên quan. orphanRemoval: Xóa Product nếu nó không thuộc Category nào.
    private List<Product> products;
}
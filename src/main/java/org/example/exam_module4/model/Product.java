package org.example.exam_module4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 5, max = 50, message = "Tên sản phẩm từ 5 đến 50 ký tự")
    private String name;

    @NotNull(message = "Giá tiền không được để trống")
    @DecimalMin(value = "10000.0", message = "Giá tối thiểu 10000 VND")
    private double price;

    @NotBlank(message = "Tình trạng không được để trống")
    private String status;

    @ManyToOne
    @NotNull(message = "Phải chọn loại sản phẩm")
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package org.example.exam_module4.service;

import org.example.exam_module4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Page<Product> search(String name, Long categoryId, Double price, Pageable pageable);
    Optional<Product> findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
    void deleteAllByIds(List<Long> ids);
}

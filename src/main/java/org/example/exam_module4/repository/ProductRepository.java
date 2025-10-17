package org.example.exam_module4.repository;

import org.example.exam_module4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<Product> findByPrice(Double price, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategory_Id(String name, Long categoryId, Pageable pageable);
}

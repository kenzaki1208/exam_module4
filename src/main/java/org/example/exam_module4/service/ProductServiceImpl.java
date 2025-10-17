package org.example.exam_module4.service;

import org.example.exam_module4.model.Product;
import org.example.exam_module4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> search(String name, Long categoryId, Double price, Pageable pageable) {
        if (name != null && !name.isEmpty() && categoryId != null && price != null) {
            return productRepository.findAll((root, query, cb) -> cb.and(
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"),
                    cb.equal(root.get("category").get("cid"), categoryId),
                    cb.equal(root.get("price"), price)
            ), pageable);
        }

        if (name != null && !name.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        if (categoryId != null) {
            return productRepository.findByCategory_Id(categoryId, pageable);
        }

        if (price != null) {
            return productRepository.findByPrice(price, pageable);
        }

        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }
}

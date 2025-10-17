package org.example.exam_module4.controller;

import jakarta.validation.Valid;
import org.example.exam_module4.model.Product;
import org.example.exam_module4.service.CategoryService;
import org.example.exam_module4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String showList(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double price,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Product> products = productService.search(name, categoryId, price, pageable);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("price", price);
        return "product_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "product_create";
    }

    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            // nếu là sửa thì trả về trang sửa, nếu là thêm thì trả về trang thêm
            if (product.getId() != null) {
                return "product_edit";
            } else {
                return "product_create";
            }
        }

        // Kiểm tra category null (phòng lỗi null pointer)
        if (product.getCategory() == null) {
            bindingResult.rejectValue("category", "error.category", "Vui lòng chọn loại sản phẩm");
            model.addAttribute("categories", categoryService.findAll());
            if (product.getId() != null) {
                return "product_edit";
            } else {
                return "product_create";
            }
        }

        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm có id = " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "product_edit";
    }

    @PostMapping("/delete")
    public String deleteProducts(@RequestParam("ids") List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            productService.deleteAllByIds(ids);
        }
        return "redirect:/products";
    }
}
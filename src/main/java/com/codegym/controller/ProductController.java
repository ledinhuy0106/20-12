package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String showList(Model model, String key) {
        List<Product> productList;
        if (key == null) {
            productList = (List<Product>) productService.findAll();
        } else {
            productList = productService.findByName(key);
        }
        model.addAttribute("products", productList);
        return "/product/list";
    }

    @GetMapping("/create")
    public String showFormCreate() {
        return "/product/create";
    }

    @PostMapping("/create")
    public String create(Product product, @RequestParam MultipartFile img) {
        String fileName = img.getOriginalFilename();
        try {
            FileCopyUtils.copy(img.getBytes(),
                    new File( "C:\\Users\\UyLe\\IdeaProjects\\DemoJPAS\\src\\main\\img\\"+ fileName)); // coppy ảnh từ ảnh nhận được vào thư mục quy định,
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     product.setImage(fileName);
        productService.save(product);
        return "redirect:/products/list";
    }

    @GetMapping("sort")
    public String showSort(Model model) {
        List<Product> productList;
        productList = (List<Product>) productService.findByOrderPrice();
        model.addAttribute("product", productList);
        return "/product/list";
    }
}

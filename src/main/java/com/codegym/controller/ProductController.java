package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String showList(Model model, String key) {
        List<Product> productList ;

        if (key == null) {

            productList = (List<Product>) productService.findAll();
        } else {
            productList= productService.findByName(key);

        }
        model.addAttribute("products", productList);
        return "/product/list";

    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(Product product) {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        productService.save(product);
        return "/product/list";
    }


}

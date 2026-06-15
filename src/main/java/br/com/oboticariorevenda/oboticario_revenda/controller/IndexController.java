package br.com.oboticariorevenda.oboticario_revenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.service.ProductService;


@Controller
public class IndexController {
    private ProductService productService;

    public IndexController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getIndex(@RequestParam(required = false, defaultValue = "all") String filter,
                           @RequestParam(required = false, defaultValue = "") String nameFilter, Model model) {

        List<Product> products = new ArrayList<>();

        if (!nameFilter.isBlank()) {
            products = productService.getProductsByCriteria(nameFilter);
            model.addAttribute("nameFilter", nameFilter);
            model.addAttribute("products", products);
            return "index";
        }

        switch (filter) {
            case "MALE", "FEMALE" -> {
                GenderEnum genderEnum = GenderEnum.valueOf(filter);
                products = productService.getProductsByGender(genderEnum);
            } 
        
            case "higherPrice" -> products = productService.getProductsWithHigherPrice();

            case "lowerPrice" -> products = productService.getProductsWithLowerPrice();

            case "higherDiscount" -> products = productService.getProductsWithHigherDiscount();

            case "higherQuantity" -> products = productService.getProductsWithHigherQuantity();

            case "lowerQuantity" -> products = productService.getProductsWithLowerQuantity();

            default -> products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        return "index";
    }
}

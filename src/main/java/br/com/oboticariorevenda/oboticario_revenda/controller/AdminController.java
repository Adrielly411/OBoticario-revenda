package br.com.oboticariorevenda.oboticario_revenda.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.oboticariorevenda.oboticario_revenda.dto.ProductRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.service.ProductService;
import jakarta.validation.Valid;




@Controller
public class AdminController {
    private ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String getAdminIndex(@RequestParam(required = false) String gender, Model model) {
        List<Product> products = new ArrayList<>();

        if (gender == null || gender.equals("Todos os gêneros")) {
            products = productService.getAllProducts();
        } else {
            List<Product> allProducts = productService.getAllProducts();
            GenderEnum genderEnum = GenderEnum.valueOf(gender);

            for (Product product : allProducts) {
                if (product.getGender() == genderEnum) {
                    products.add(product);
                }
            }
        }

        model.addAttribute("products", products);
        return "/admin/index";
    }

    @GetMapping("/admin/relatorio")
    public String getAdminReport(Model model) {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("numberProducts", productService.getNumberOfProducts());
        model.addAllAttributes(attributes);
        return "/admin/report";
    }

    @GetMapping("/admin/criar-produto")
    public String getAdminCreateProduct(ProductRequestDto productDto) {
        return "/admin/create-product";
    }

    @PostMapping("/admin/criar-produto")
    public String createProduct(@Valid @ModelAttribute("productRequestDto") ProductRequestDto productDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
			return "/admin/create-product";
		}

        if (productDto.getGender().equals("Selecione o gênero")) {
            bindingResult.addError(null);
        }

        productService.saveProduct(productDto);

        return "redirect:/admin";
    }

    @GetMapping("/admin/editar-produto")
    public String getAdminEditProduct() {
        return "/admin/edit-product";
    }

    @GetMapping("/admin/deletar-produto")
    public String deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
    
}

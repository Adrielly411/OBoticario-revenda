package br.com.oboticariorevenda.oboticario_revenda.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.oboticariorevenda.oboticario_revenda.dto.ProductEditRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.dto.ProductCreateRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.model.SocialAnalytics;
import br.com.oboticariorevenda.oboticario_revenda.service.PagingProductService;
import br.com.oboticariorevenda.oboticario_revenda.service.ProductService;
import jakarta.validation.Valid;

@Controller
public class AdminController {
    private ProductService productService;
    private PagingProductService pagingProductService;
    private SocialAnalytics socialAnalytics;

    public AdminController(ProductService productService, PagingProductService pagingProductService, SocialAnalytics socialAnalytics) {
        this.productService = productService;
        this.pagingProductService = pagingProductService;
        this.socialAnalytics = socialAnalytics;
    }

    @GetMapping("/admin")
    public String getAdminIndex(@RequestParam(required = false, defaultValue = "all") String filter,
                                @RequestParam(required = false, defaultValue = "") String nameFilter, Model model,
                                @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);

        Page<Product> productPage;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        if (!nameFilter.isBlank()) {
            productPage = pagingProductService.getPaginatedProductsByCriteria(pageable, nameFilter);
            model.addAttribute("nameFilter", nameFilter);

            model.addAttribute("productPage", productPage);
            int totalPages = productPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

                model.addAttribute("pageNumbers", pageNumbers);
            }
            return "admin/index";
        }

        switch (filter) {
            case "MALE", "FEMALE" -> {
                GenderEnum genderEnum = GenderEnum.valueOf(filter);
                productPage = pagingProductService.getPaginatedProductsByGender(pageable, genderEnum);
            } 
        
            case "higherPrice" -> productPage = pagingProductService.getPaginatedProductsWithHigherPrice(pageable);

            case "lowerPrice" -> productPage = pagingProductService.getPaginatedProductsWithLowerPrice(pageable);

            case "higherDiscount" -> productPage = pagingProductService.getPaginatedProductsWithHigherDiscount(pageable);

            case "higherQuantity" -> productPage = pagingProductService.getPaginatedProductsWithHigherQuantity(pageable);

            case "lowerQuantity" -> productPage = pagingProductService.getPaginatedProductsWithLowerQuantity(pageable);

            default -> productPage = pagingProductService.getAllPaginatedProducts(pageable);
        }

        model.addAttribute("productPage", productPage);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/index";    
    }

    @GetMapping("/admin/relatorio")
    public String getAdminReport(Model model) {
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("numberProducts", productService.getNumberOfProducts());
        attributes.put("instagramClicks", socialAnalytics.getInstagram_clicks());
        attributes.put("whatsappClicks", socialAnalytics.getWhatsapp_clicks());
        model.addAllAttributes(attributes);
        return "admin/report";
    }

    @GetMapping("/admin/criar-produto")
    public String getAdminCreateProduct(ProductCreateRequestDto productDto) {
        return "admin/create-product";
    }

    @PostMapping("/admin/criar-produto")
    public String createProduct(@Valid @ModelAttribute("productCreateRequestDto") ProductCreateRequestDto productDto, BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
			return "admin/create-product";
		}

        productService.saveProduct(productDto);

        return "redirect:/admin";
    }

    @GetMapping("/admin/editar-produto")
    public String getAdminEditProduct(@RequestParam String id, Model model) {
        Product product = productService.getProductById(id);

        ProductEditRequestDto productDto = new ProductEditRequestDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setGender(product.getGender().toString());
        productDto.setImageUrl(product.getImageUrl());

        model.addAttribute("productEditRequestDto", productDto);
        return "admin/edit-product";
    }

    @PostMapping("/admin/editar-produto")
    public String editProduct(@RequestParam String id, @Valid @ModelAttribute ProductEditRequestDto productEditRequestDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/edit-product";
        }

        productService.editProduct(id, productEditRequestDto);

        return "redirect:/admin";
    }

    @GetMapping("/admin/deletar-produto")
    public String deleteProduct(@RequestParam String id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }
    
}

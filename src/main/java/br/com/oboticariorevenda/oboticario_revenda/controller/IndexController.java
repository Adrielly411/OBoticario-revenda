package br.com.oboticariorevenda.oboticario_revenda.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.service.PagingProductService;

@Controller
public class IndexController {
    private PagingProductService pagingProductService;

    public IndexController(PagingProductService pagingProductService) {
        this.pagingProductService = pagingProductService;
    }

    @GetMapping("/")
    public String getIndex(@RequestParam(required = false, defaultValue = "all") String filter,
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
            return "index";
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

        return "index";
    }
}

package br.com.oboticariorevenda.oboticario_revenda.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.repository.ProductRepository;

@Service
public class PagingProductService {
    private ProductRepository productRepository;

    public PagingProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getPaginatedProductsByCriteria(Pageable pageable, String name) {
        return productRepository.findAllBy(name, pageable);
    }

    public Page<Product> getPaginatedProductsByGender(Pageable pageable, GenderEnum genderEnum) {
        return productRepository.findByGender(genderEnum, pageable);
    }

    public Page<Product> getPaginatedProductsWithHigherPrice(Pageable pageable) {
        Pageable sorted = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Direction.DESC, "discountedPrice")
        );

        return productRepository.findAll(sorted);
    }

    public Page<Product> getPaginatedProductsWithLowerPrice(Pageable pageable) {
        Pageable sorted = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Direction.ASC, "discountedPrice")
        );

        return productRepository.findAll(sorted);   
    }

    public Page<Product> getPaginatedProductsWithHigherDiscount(Pageable pageable) {
        Pageable sorted = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Direction.DESC, "discountPercentage")
        );

        return productRepository.findAll(sorted);
    }

    public Page<Product> getPaginatedProductsWithHigherQuantity(Pageable pageable) {
        Pageable sorted = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Direction.DESC, "quantity")
        );

        return productRepository.findAll(sorted);
    }

    public Page<Product> getPaginatedProductsWithLowerQuantity(Pageable pageable) {
        Pageable sorted = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Direction.ASC, "quantity")
        );

        return productRepository.findAll(sorted);
    }
}

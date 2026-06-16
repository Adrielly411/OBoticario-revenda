package br.com.oboticariorevenda.oboticario_revenda.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.oboticariorevenda.oboticario_revenda.dto.ProductEditRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.dto.ProductCreateRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.exception.ImageKitUploadException;
import br.com.oboticariorevenda.oboticario_revenda.exception.ProductNotFoundException;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.repository.ProductRepository;
import io.imagekit.client.ImageKitClient;
import io.imagekit.client.okhttp.ImageKitOkHttpClient;
import io.imagekit.models.files.FileUploadParams;
import io.imagekit.models.files.FileUploadResponse;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private String imagekit_private;

    public ProductService(ProductRepository productRepository, @Value("${imagekit.private}") String imagekit_private) {
        this.productRepository = productRepository;
        this.imagekit_private = imagekit_private;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Não foi possível encontrar o produto"));
    }

    public List<Product> getProductsByCriteria(String name) {
        List<Product> products = productRepository.findAllBy(name);
        return products;
    }

    public List<Product> getProductsByGender(GenderEnum genderEnum) {
        return productRepository.findByGender(genderEnum);
    }

    public List<Product> getProductsWithHigherPrice() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getDiscountedPrice).reversed());
        return products;
    }

    public List<Product> getProductsWithLowerPrice() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getDiscountedPrice));
        return products;
    }

    public List<Product> getProductsWithHigherDiscount() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getDiscountPercentage).reversed());
        return products;
    }

    public List<Product> getProductsWithHigherQuantity() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getQuantity).reversed());
        return products;
    }

    public List<Product> getProductsWithLowerQuantity() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getQuantity));
        return products;
    }

    public long getNumberOfProducts() {
        return productRepository.count();
    }

    public void saveProduct(ProductCreateRequestDto productDto) throws IOException {
        GenderEnum gender = GenderEnum.valueOf(productDto.getGender());

        Product product = Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .discountedPrice(productDto.getDiscountedPrice())
            .discountPercentage(calculateDiscount(productDto.getPrice(), productDto.getDiscountedPrice()))
            .quantity(productDto.getQuantity())
            .gender(gender)
            .imageUrl(createImageUrlAndStoreOnImagekit(productDto.getImageFile()))
            .build();

        productRepository.save(product);
    }

    public void editProduct(String id, ProductEditRequestDto productEditRequestDto) throws IOException {
        Product product = this.getProductById(id);
        product.setName(productEditRequestDto.getName());
        product.setPrice(productEditRequestDto.getPrice());
        product.setDiscountedPrice(productEditRequestDto.getDiscountedPrice());
        product.setDiscountPercentage(this.calculateDiscount(productEditRequestDto.getPrice(), productEditRequestDto.getDiscountedPrice()));
        product.setGender(GenderEnum.valueOf(productEditRequestDto.getGender()));
        product.setQuantity(productEditRequestDto.getQuantity());
        
        if (!productEditRequestDto.getImageFile().isEmpty()) {
            product.setImageUrl(createImageUrlAndStoreOnImagekit(productEditRequestDto.getImageFile()));
        }

        productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    private long calculateDiscount(double price, double discountedPrice) {
        long discountPercentage;

        discountPercentage = Math.abs(Math.round((discountedPrice * 100 / price) - 100));

        return discountPercentage;
    }

    private String createImageUrlAndStoreOnImagekit(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString();

        ImageKitClient client = ImageKitOkHttpClient.builder()
            .privateKey(imagekit_private)
            .build();

        FileUploadParams params = FileUploadParams.builder()
            .file(imageFile.getBytes())
            .fileName(fileName + ".jpg")
            .build();

        FileUploadResponse response = client.files().upload(params);

        return response.url().orElseThrow(() -> new ImageKitUploadException("O ImageKit concluiu o upload, mas não retornou a URL da imagem."));
    }
}

package br.com.oboticariorevenda.oboticario_revenda.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.oboticariorevenda.oboticario_revenda.dto.ProductRequestDto;
import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import br.com.oboticariorevenda.oboticario_revenda.repository.ProductRepository;
import io.imagekit.client.ImageKitClient;
import io.imagekit.client.okhttp.ImageKitOkHttpClient;
import io.imagekit.models.files.FileUploadParams;
import io.imagekit.models.files.FileUploadResponse;

@Service
public class ProductService {
    private ProductRepository productRepository;
    
    @Value("${imagekit.private}")
    private String imagekit_private;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public long getNumberOfProducts() {
        return productRepository.count();
    }

    public void saveProduct(ProductRequestDto productDto) throws IOException {
        GenderEnum gender;

        if (productDto.getGender().equals("Masculino")) {
            gender = GenderEnum.MALE;
        } else {
            gender = GenderEnum.FEMALE;
        }

        Product product = Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .discountedPrice(productDto.getDiscountedPrice())
            .discountPercentage(calculateDiscount(productDto.getPrice(), productDto.getDiscountedPrice()))
            .quantity(productDto.getQuantity())
            .gender(gender)
            .imageUrl(createImageUrl(productDto.getImageFile()))
            .build();

        productRepository.save(product);
    }

    public void editProduct(String id) {
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    private long calculateDiscount(double price, double discountedPrice) {
        long discountPercentage;

        discountPercentage = Math.abs(Math.round((discountedPrice * 100 / price) - 100));

        return discountPercentage;
    }

    private String createImageUrl(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID().toString();

        ImageKitClient client = ImageKitOkHttpClient.builder()
            .privateKey(imagekit_private)
            .build();

        FileUploadParams params = FileUploadParams.builder()
            .file(imageFile.getBytes())
            .fileName(fileName + ".jpg")
            .build();

        FileUploadResponse response = client.files().upload(params);

        return response.url().orElseThrow(() -> new IOException("O ImageKit concluiu o upload, mas não retornou a URL da imagem."));
    }
}

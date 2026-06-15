package br.com.oboticariorevenda.oboticario_revenda.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEditRequestDto {
    private String id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço do produto deve ser maior que 0")
    private Double price;

    @NotNull(message = "O desconto do produto é obrigatório")
    @Positive(message = "O desconto do produto deve ser maior que 0")
    private Double discountedPrice;

    @NotNull(message = "A quantidade do produto é obrigatória")
    @PositiveOrZero(message = "A quantidade do produto não pode ser negativa")
    private Integer quantity;

    @NotNull(message = "O gênero do produto é obrigatório")
    private String gender;

    private MultipartFile imageFile;
    private String imageUrl;

    @AssertTrue(message = "O preço com desconto não pode ser maior que o preço original")
    public boolean isDiscountedPriceHigherThanPrice() {
        return (price >= discountedPrice);
    }   

    @AssertTrue()
    public boolean isImageUrlOrFileProvided() {
        return (imageFile != null && !imageFile.isEmpty()) || (imageUrl != null && !imageUrl.isBlank());
    }   
}

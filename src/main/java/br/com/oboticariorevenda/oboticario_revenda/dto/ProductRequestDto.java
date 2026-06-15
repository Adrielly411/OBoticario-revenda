package br.com.oboticariorevenda.oboticario_revenda.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductRequestDto {
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
    @NotBlank(message = "O gênero do produto é obrigatório")
    private String gender;

    private MultipartFile imageFile;

    @AssertTrue(message = "A imagem do produto é obrigatória")
    public boolean isImageNull() {
        return (imageFile != null && !imageFile.isEmpty());
    }   

    @AssertTrue(message = "O preço com desconto não pode ser maior que o preço original")
    public boolean isDiscountedPriceHigherThanPrice() {
        if (price == null || discountedPrice == null) {
            return true; 
        }
        return (price >= discountedPrice);
    }   
}

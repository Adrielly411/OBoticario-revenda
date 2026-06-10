package br.com.oboticariorevenda.oboticario_revenda.model;

import java.util.UUID;

import org.bson.types.Binary;

import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private double price;
    private double discountedPrice;
    private long discountPercentage;
    private int quantity;
    private GenderEnum gender;
    private String imageUrl;

    public static class ProductBuilder {
        public ProductBuilder() {
            this.id = UUID.randomUUID().toString();
        }
    }
}

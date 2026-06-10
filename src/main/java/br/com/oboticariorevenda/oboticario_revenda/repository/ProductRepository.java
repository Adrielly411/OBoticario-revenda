package br.com.oboticariorevenda.oboticario_revenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.oboticariorevenda.oboticario_revenda.model.Product;
import java.util.List;
import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;


public interface ProductRepository extends MongoRepository<Product, String>{
    List<Product> findByGender(GenderEnum gender);
}

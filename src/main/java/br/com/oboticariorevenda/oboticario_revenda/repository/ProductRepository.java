package br.com.oboticariorevenda.oboticario_revenda.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;


public interface ProductRepository extends MongoRepository<Product, String>{
    List<Product> findByGender(GenderEnum gender);

    @Query("{ 'name': { '$regex': ?0, '$options': 'i' } }")
    List<Product> findAllBy(String name);
}

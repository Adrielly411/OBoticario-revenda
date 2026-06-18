package br.com.oboticariorevenda.oboticario_revenda.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.oboticariorevenda.oboticario_revenda.enums.GenderEnum;
import br.com.oboticariorevenda.oboticario_revenda.model.Product;


public interface ProductRepository extends MongoRepository<Product, String>{
    Page<Product> findByGender(GenderEnum gender, Pageable pageable);

    @Query("{ 'name': { '$regex': ?0, '$options': 'i' } }")
    Page<Product> findAllBy(String name, Pageable pageable);
}

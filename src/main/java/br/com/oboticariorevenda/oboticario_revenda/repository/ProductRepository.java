package br.com.oboticariorevenda.oboticario_revenda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.oboticariorevenda.oboticario_revenda.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    
}

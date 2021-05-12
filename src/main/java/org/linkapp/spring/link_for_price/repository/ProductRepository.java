package org.linkapp.spring.link_for_price.repository;

import org.linkapp.spring.link_for_price.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}

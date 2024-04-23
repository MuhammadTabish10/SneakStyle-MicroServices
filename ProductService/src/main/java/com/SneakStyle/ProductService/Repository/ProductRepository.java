package com.SneakStyle.ProductService.Repository;

import com.SneakStyle.ProductService.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndStatusIsTrue(String name);
    List<Product> findAllByStatusOrderByIdDesc(Boolean status);
    List<Product> findAllByCategories_NameAndStatusIsTrue(String categoryName);

    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}

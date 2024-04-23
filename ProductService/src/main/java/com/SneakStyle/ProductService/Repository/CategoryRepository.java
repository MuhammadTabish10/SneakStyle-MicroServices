package com.SneakStyle.ProductService.Repository;

import com.SneakStyle.ProductService.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameAndStatusIsTrue(String name);
    List<Category> findAllByStatusOrderByIdDesc(Boolean status);
    @Modifying
    @Query("UPDATE Category c SET c.status = :status WHERE c.id = :id")
    void setStatusWhereId(Long id, Boolean status);
}

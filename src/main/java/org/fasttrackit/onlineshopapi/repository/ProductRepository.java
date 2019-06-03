package org.fasttrackit.onlineshopapi.repository;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Long is wrapper for primitive type long
public interface ProductRepository extends JpaRepository<Product, Long> {

    //queries derives from method names springBoot
    Page<Product> findByNameContaining(String partialName, Pageable pageable);
    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName, int minQuantity, Pageable pageable);

    // named query using JPQL (java persistence query language
    @Query("SELECT product FROM Product product WHERE name LIKE '%?1'") // '%?1' primul parametru
    // named query using native sql
//    @Query(value = "SELECT * FROM Product product WHERE name LIKE '%?1'", nativeQuery = true)
    Page<Product> findByPartialName(String partialName, Pageable pageable);


}

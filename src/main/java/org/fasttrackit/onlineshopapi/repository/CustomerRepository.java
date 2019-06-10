package org.fasttrackit.onlineshopapi.repository;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//Long is wrapper for primitive type long
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //queries derives from method names springBoot
    Page<Customer> findByIdContaining(Long partialId, Pageable pageable);
    Page<Customer> findByFirstNameContaining(String partialFirstName, Pageable pageable);
    Page<Customer> findByLastNameContaining(String partialLastName, Pageable pageable);
    Page<Customer> findByPhoneNumberContaining(String partialPhoneNumber, Pageable pageable);



//    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName, int minQuantity, Pageable pageable);

//    // named query using JPQL (java persistence query language
//    @Query("SELECT product FROM Product product WHERE name LIKE '%?1'") // '%?1' primul parametru
//    // named query using native sql
////    @Query(value = "SELECT * FROM Product product WHERE name LIKE '%?1'", nativeQuery = true)
//    Page<Product> findByPartialName(String partialName, Pageable pageable);


}

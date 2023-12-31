package com.jb.caas.repos;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // :)
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // save / saveAndFlush / delete / findById / findAll / existsById

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmail(String email);

}

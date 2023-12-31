package com.jb.caas.repos;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // :)
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // save / saveAndFlush / delete / findById / findAll / existsById

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Company findByEmail(String email);

}

package com.jb.caas.beans;

/*
 * copyrights @ fadi
 */

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name cannot be null or whitespace")
    @Size(min = 3, max = 45, message = "Name should be 3-45 chars length")
    @Column(nullable = false, length = 45)
    private String name;

    @NotNull(message = "Email cannot be null")
    @Size(max = 45, message = "Email cannot exceed 45 char length")
    @Email(message = "Email should be in a valid pattern")
    @Column(nullable = false, length = 45)
    private String email;

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(min = 4, max = 8, message = "Password should be 4-8 char length")
    @Column(nullable = false, length = 8)
    private String password;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "company")
    // cascade types also serve repos testings
    @Singular
    private List<Coupon> coupons = new ArrayList<>();

}

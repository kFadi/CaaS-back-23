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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "First Name cannot be null or whitespace")
    @Size(min = 3, max = 45, message = "First Name should be 3-45 chars length")
    @Column(nullable = false, length = 45)
    private String firstName;

    @NotBlank(message = "Last Name cannot be null or whitespace")
    @Size(min = 3, max = 45, message = "Last Name should be 3-45 chars length")
    @Column(nullable = false, length = 45)
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Size(max = 45, message = "Email cannot exceed 45 char length")
    @Email(message = "Email should be in a valid pattern")
    @Column(nullable = false, length = 45)
    private String email;

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(min = 4, max = 8, message = "Password should be 4-8 char length")
    @Column(nullable = false, length = 8)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "customers_coupons",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    @Singular
    private Set<Coupon> coupons = new HashSet<>();

}

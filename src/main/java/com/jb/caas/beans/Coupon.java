package com.jb.caas.beans;

/*
 * copyrights @ fadi
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO custom validation?
    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @NotBlank(message = "Title cannot be null or whitespace")
    @Size(min = 3, max = 45, message = "Title should be 3-45 chars length")
    @Column(nullable = false, length = 45)
    private String title;

    @NotBlank(message = "Description cannot be null or whitespace")
    @Size(min = 3, max = 45, message = "Description should be 3-45 chars length")
    @Column(nullable = false, length = 45)
    private String description;

    @JsonFormat(pattern = "dd/MM/yyy") //TODO??
    @NotNull(message = "Start Date cannot be null")
    @FutureOrPresent(message = "Start Date cannot be set to the past")
    @Column(nullable = false)
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyy") //TODO??
    @NotNull(message = "End Date cannot be null")
    @FutureOrPresent(message = "End Date cannot be set to the past")
    @Column(nullable = false)
    private Date endDate;

    //TODO - still called null for int?
    @NotNull(message = "Amount cannot be null")
    @PositiveOrZero(message = "Amount cannot be negative")
    @Column(nullable = false)
    private int amount;

    //TODO - still called null for double?
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price cannot be negative")
    @Column(nullable = false)
    private double price;

    @NotBlank(message = "Image cannot be null or whitespace")
    @Size(min = 3, max = 45, message = "Image should be 3-45 chars length")
    @Column(nullable = false, length = 45)
    private String image;

    @ManyToOne
    @ToString.Exclude
    private Company company;

}

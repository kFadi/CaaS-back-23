package com.jb.caas.repos;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Category;
import com.jb.caas.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository // :)
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    // save / saveAndFlush / delete / findById / findAll / existsById

    boolean existsByIdAndCompanyId(int id, int companyId);

    boolean existsByCompanyIdAndTitle(int companyId, String title);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM `caas`.`customers_coupons` WHERE (`customer_id`= :customerId AND `coupon_id`= :couponId)) THEN 'true' ELSE 'false' END", nativeQuery = true)
    boolean isCouponPurchasedByCustomer(int customerId, int couponId);

    //////////////////////////

    List<Coupon> findByCompanyId(int companyId);

    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

    @Query(value = "SELECT * FROM `caas`.`coupons` WHERE (`company_id` = :companyId AND `price` <= :maxPrice)", nativeQuery = true)
    List<Coupon> findByCompanyIdAndMaxPrice(int companyId, double maxPrice);

    //////////////////////////

    @Query(value = "SELECT * FROM `caas`.`coupons` WHERE `id` IN (SELECT `coupon_id` FROM `caas`.`customers_coupons` WHERE `customer_id`= :customerId)", nativeQuery = true)
    List<Coupon> findByCustomerId(int customerId);

    @Query(value = "SELECT * FROM `caas`.`coupons` WHERE `category` = :category AND `id` IN (SELECT `coupon_id` FROM `caas`.`customers_coupons` WHERE `customer_id`= :customerId)", nativeQuery = true)
    List<Coupon> findByCustomerIdAndCategory(int customerId, String category);

    @Query(value = "SELECT * FROM `caas`.`coupons` WHERE `price` <= :maxPrice AND `id` IN (SELECT `coupon_id` FROM `caas`.`customers_coupons` WHERE `customer_id`= :customerId)", nativeQuery = true)
    List<Coupon> findByCustomerIdAndMaxPrice(int customerId, double maxPrice);

    //////////////////////////

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `caas`.`coupons` WHERE `id` > 0 AND `end_date` < current_date()", nativeQuery = true)
    void deleteExpiredCoupons();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `caas`.`customers_coupons` WHERE `coupon_id` NOT IN (SELECT `id` FROM `caas`.`coupons`)", nativeQuery = true)
    void updatePurchasesJoin();

}

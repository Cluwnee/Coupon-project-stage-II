package com.example.demo.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	// Adding a custom command to get all coupons by category for the company.
	List<Coupon> findByCategoryAndCompany(CategoryType category, Company company);

	// Adding a custom command to get all coupons by max price for the company.
	List<Coupon> findByPriceLessThanEqualAndCompany(double maxPrice, Company company);

}
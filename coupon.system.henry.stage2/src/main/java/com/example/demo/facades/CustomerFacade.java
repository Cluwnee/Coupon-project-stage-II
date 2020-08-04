package com.example.demo.facades;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.db.CouponRepository;
import com.example.demo.db.CustomerRepository;
import com.example.demo.exceptions.BoughtCouponException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.NoCouponsException;
import com.example.demo.exceptions.OutOfStockException;

@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade {

	// We will use this for the login method to set the logged in customer to the
	// customer facade.
	private Customer loginCust;

	// Repositories commands we are going to use here.
	private CustomerRepository custRepo;
	private CouponRepository coupRepo;

	// Constructor instead of "@Autowire" to avoid errors, safer.
	public CustomerFacade(CustomerRepository custRepo, CouponRepository coupRepo) {
		super();
		this.custRepo = custRepo;
		this.coupRepo = coupRepo;
	}

	// Login method, checks all existing customers email and password to the user
	// entered email and password, if correct then it will set the entered customer
	// email and password as the logged in user ID(customer ID).
	@Override
	public boolean login(String email, String password) {
		for (Customer customer : custRepo.findAll()) {
			if (customer.getCustomerEmail().equals(email) && customer.getCustomerPassword().equals(password)) {
				loginCust = customer;
				return true;
			}
		}
		return false;
	}

	// Lets the customer purchase a coupon. We run a three checks before allowing
	// the purchase to proceed.
	// 1) Check if the customer already bought this coupon.
	// 2) Check if coupon is in stock.
	// 3) Check if coupon is not expired.
	// If everything checks out, allow the purchase, add the desired coupon to the
	// coupon list of the customer.
	public void purchaseCoupon(Coupon coupon) throws BoughtCouponException, OutOfStockException, CouponExpiredException,
			CouponNotFoundException, NoCouponsException {
		Coupon couponPurchase = coupRepo.findById(coupon.getCouponId()).orElseThrow(CouponNotFoundException::new);
		Customer customer = custRepo.findById(loginCust.getCustomerId()).orElse(null);
		Calendar cal = Calendar.getInstance();
		Date now = new Date(cal.getTimeInMillis());
		if (customer.getCoupons() != null) { // Added because of null exceptions - if no coupons don't run first check
			for (Coupon coup : customer.getCoupons()) {
				if (coup.getCouponId() == couponPurchase.getCouponId())
					throw new BoughtCouponException();
			}
		}
		if (couponPurchase.getAmount() <= 0) {
			throw new OutOfStockException();
		} else if (couponPurchase.getEndDate().before(now)) {
			throw new CouponExpiredException();
		} else {
			customer.getCoupons().add(couponPurchase);
			custRepo.save(customer);
			couponPurchase.setAmount(couponPurchase.getAmount() - 1);
			coupRepo.save(couponPurchase);
		}
	}

	// Return all the coupons of the logged in customer.
	public List<Coupon> getCustomerCoupons() throws NoCouponsException {
		Customer cust = custRepo.findById(loginCust.getCustomerId()).orElse(null);
		if (cust.getCoupons().isEmpty())
			throw new NoCouponsException();
		else
			return cust.getCoupons();
	}

	// Return all customer coupons of the logged in customer based on the desired
	// category input. We run a new list and compare customer coupons.
	// Not the most efficient but I had problems with the repository throwing
	// errors.
	public List<Coupon> getCustomerCoupons(CategoryType category) throws NoCouponsException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Customer customer = custRepo.findById(loginCust.getCustomerId()).orElse(null);
		for (Coupon coup : customer.getCoupons()) {
			if (coup.getCategory().ordinal() == category.ordinal()) {
				coupons.add(coup);
			}
		}
		return coupons;
	}

	// Return all customer coupons of the logged in customer based on the desired
	// max price input. We use a new list and compare prices to customer coupons.
	// Not the most efficient but I had problems with the repository throwing
	// errors.
	public List<Coupon> getCustomerCoupons(double maxPrice) throws NoCouponsException {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Customer customer = custRepo.findById(loginCust.getCustomerId()).orElse(null);
		for (Coupon coup : customer.getCoupons()) {
			if (coup.getPrice() <= maxPrice) {
				coupons.add(coup);
			}
		}
		return coupons;
	}

	// Return the details of the logged in customer.
	// We don't throw an exception because the login itself is a checker for this
	// method.
	public Customer getCustomerDetails() {
		return custRepo.findById((loginCust.getCustomerId())).orElse(null);
	}

}

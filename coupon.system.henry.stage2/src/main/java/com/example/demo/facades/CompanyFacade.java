package com.example.demo.facades;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.db.CompanyRepository;
import com.example.demo.db.CouponRepository;
import com.example.demo.db.CustomerRepository;
import com.example.demo.exceptions.CouponExistsException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.NoCouponsException;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {

	// We will use this for the login method to set the logged in company to the
	// company facade.
	private Company loginComp;

	// Repositories commands we are going to use here.
	private CompanyRepository compRepo;
	private CouponRepository coupRepo;
	private CustomerRepository custRepo;

	// Constructor instead of "@Autowire" to avoid errors, safer.
	public CompanyFacade(CompanyRepository compRepo, CouponRepository coupRepo, CustomerRepository custRepo) {
		super();
		this.compRepo = compRepo;
		this.coupRepo = coupRepo;
		this.custRepo = custRepo;
	}

	// Login method, checks all existing companies email and password to the user
	// entered email and password, if correct then it will set the entered company
	// email and password as the logged in one(Company object).
	@Override
	public boolean login(String email, String password) {
		for (Company company : compRepo.findAll()) {
			if (company.getCompanyEmail().equals(email) && company.getCompanyPassword().equals(password)) {
				loginComp = company;
				return true;
			}
		}
		return false;
	}

	// Create a new coupon using the save command. Before creating a coupon we check
	// to see if the coupon already exists for the logged in company.
	public void addCoupon(Coupon coupon) throws CouponExistsException, NoCouponsException {
		Company comp = compRepo.findById(loginComp.getCompanyId()).orElse(null);
		for (Coupon coup : comp.getCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle()) && coup.getCouponId() != coupon.getCouponId()) {
				throw new CouponExistsException();
			}
		}
		coupon.setCompany(loginComp);
		coupRepo.save(coupon);
		compRepo.save(loginComp);
		this.loginComp = compRepo.findById(loginComp.getCompanyId()).orElse(null);
	}

	// Update an existing coupon. We also check if it actually exists. (No need to
	// check if the company has permission to change the coupon because we run only
	// on company owned coupons!)
	// Furthermore we make sure that the company is not updating to an existing
	// coupon.
	public void updateCoupon(Coupon coupon) throws CouponNotFoundException, CouponExistsException, NoCouponsException {
		Coupon couponUp = coupRepo.findById(coupon.getCouponId()).orElseThrow(CouponNotFoundException::new);
		Company comp = compRepo.findById(loginComp.getCompanyId()).orElse(null);
		for (Coupon coup : comp.getCoupons()) {
			if (coup.getTitle().equals(coupon.getTitle()) && coup.getCouponId() != coupon.getCouponId()) {
				throw new CouponExistsException();
			}
		}
		coupRepo.save(couponUp);
	}

	// Delete an existing coupon. We run a loop to delete the purchase history from
	// the customers and only after that delete the specific coupon.
	public void deleteCoupon(int id) throws CouponNotFoundException {
		coupRepo.findById(id).orElseThrow(CouponNotFoundException::new);
		for (Customer customer : custRepo.findAll()) {
			for (Coupon coupon : customer.getCoupons()) {
				if (coupon.getCouponId() == id) {
					customer.getCoupons().remove(coupon);
					custRepo.save(customer);
					break;
				}
			}
		}
		coupRepo.deleteById(id);
	}

	// Return all the coupons of the logged in company.
	// Added null check because if it is empty it throws null pointer exception.
	public List<Coupon> getCompanyCoupons() throws NoCouponsException {
		Company comp = compRepo.findById(loginComp.getCompanyId()).orElse(null);
		if (comp.getCoupons().isEmpty())
			throw new NoCouponsException();
		else
			return comp.getCoupons();
	}

	// Return a single coupon to the user based on their input, if the coupon is not
	// found throw an exception.
	public Coupon getOneCompanyCoupon(int couponId) throws CouponNotFoundException {
		return coupRepo.findById(couponId).orElseThrow(CouponNotFoundException::new);
	}

	// Return all company coupons of the logged in company based on the desired
	// category input(we use a custom command of findByCategory).
	public List<Coupon> getCompanyCoupons(CategoryType category, Company company) {
		return coupRepo.findByCategoryAndCompany(category, loginComp);
	}

	// Return all company coupons of the logged in company based on the desired
	// max price input(we use a custom command of findByPriceLessThanEqual).
	public List<Coupon> getCompanyCoupons(double maxPrice, Company company) {
		return coupRepo.findByPriceLessThanEqualAndCompany(maxPrice, loginComp);
	}

	// Return the details of the logged in company.
	// We don't throw an exception because the login itself is a checker for this
	// method.
	public Company getCompanyDetails() {
		return compRepo.findById(loginComp.getCompanyId()).orElse(null);
	}

}

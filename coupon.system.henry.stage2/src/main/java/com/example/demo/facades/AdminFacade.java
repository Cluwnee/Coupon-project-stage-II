package com.example.demo.facades;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.db.CompanyRepository;
import com.example.demo.db.CouponRepository;
import com.example.demo.db.CustomerRepository;
import com.example.demo.exceptions.CompanyExistsException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerExistsException;
import com.example.demo.exceptions.CustomerNotFoundException;

@Service
public class AdminFacade extends ClientFacade {

	// Repositories hold the commands we are going to use here.
	private CompanyRepository compRepo;
	private CustomerRepository custRepo;
	private CouponRepository coupRepo;

	// Constructor instead of "@Autowire" to avoid errors, safer.
	public AdminFacade(CompanyRepository compRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		super();
		this.compRepo = compRepo;
		this.custRepo = custRepo;
		this.coupRepo = coupRepo;
	}

	// ADMIN facade login details are coded in to be entered exactly as below.
	@Override
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		}
		return false;
	}

	// Create a new company using repository save command. We also check if the
	// company already exists before creating a new one by comparing email or name
	// which are both unique.
	public void addCompany(Company company) throws CompanyExistsException {
		for (Company comp : compRepo.findAll()) {
			if (comp.getCompanyEmail().equals(company.getCompanyEmail()) || comp.getName().equals(company.getName())) {
				throw new CompanyExistsException();
			}
		}
		compRepo.save(company);
	}

	// Update an existing company but before that check if it exists by comparing ID
	// and then check if we are updating to an already existing company by comparing
	// email and name which are unique.
	public void updateCompany(Company company) throws CompanyExistsException, CompanyNotFoundException {
		compRepo.findById(company.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
		for (Company comp : compRepo.findAll()) {
			if (comp.getCompanyEmail().equals(company.getCompanyEmail())
					&& comp.getCompanyId() != company.getCompanyId()
					|| comp.getName().equals(company.getName()) && comp.getCompanyId() != company.getCompanyId()) {
				throw new CompanyExistsException();
			}
		}
		compRepo.save(company);
	}

	// Delete a company, also delete their coupons and history.
	// First run a loop to remove the coupon purchase history,
	// then a second loop to disconnect the company from the coupon and after that
	// delete the coupon.
	public void deleteCompany(int id) throws CompanyNotFoundException {
		compRepo.findById(id).orElseThrow(CompanyNotFoundException::new);
		for (Customer customer : custRepo.findAll()) {
			for (Coupon coupon : customer.getCoupons()) {
				if (coupon.getCompany().getCompanyId() == id) {
					customer.getCoupons().remove(coupon);
					custRepo.save(customer);
				}
			}
		}
		for (Coupon coupon : getOneCompany(id).getCoupons()) {
			coupon.setCompany(null);
			coupRepo.delete(coupon);
		}
		compRepo.deleteById(id);
	}

	// Returns all companies, using findAll command.
	public List<Company> getAllCompanies() {
		return compRepo.findAll();
	}

	// Returns a specific company by using ID as input (if company doesn't exist
	// throw exception).
	public Company getOneCompany(int id) throws CompanyNotFoundException {
		return compRepo.findById(id).orElseThrow(CompanyNotFoundException::new);
	}

	// NOTE: CUSTOMER METHODS ARE THE SAME EXPLINATION AS COMPANY METHODS.

	public void addCustomer(Customer customer) throws CustomerExistsException {
		for (Customer cust : custRepo.findAll()) {
			if (cust.getCustomerEmail().equals(customer.getCustomerEmail())) {
				throw new CustomerExistsException();
			}
		}
		custRepo.save(customer);
	}

	public void updateCustomer(Customer customer) throws CustomerExistsException, CustomerNotFoundException {
		custRepo.findById(customer.getCustomerId()).orElseThrow(CustomerNotFoundException::new);
		for (Customer cust : custRepo.findAll()) {
			if (cust.getCustomerEmail().equals(customer.getCustomerEmail())
					&& cust.getCustomerId() != customer.getCustomerId())
				throw new CustomerExistsException();
		}
		custRepo.save(customer);
	}

	public void deleteCustomer(int id) throws CustomerNotFoundException {
		Customer customer = getOneCustomer(id);
		customer.getCoupons().clear();
		custRepo.save(customer);
		custRepo.delete(customer);
	}

	public List<Customer> getAllCustomers() {
		return custRepo.findAll();
	}

	public Customer getOneCustomer(int id) throws CustomerNotFoundException {
		return custRepo.findById(id).orElseThrow(CustomerNotFoundException::new);
	}

}

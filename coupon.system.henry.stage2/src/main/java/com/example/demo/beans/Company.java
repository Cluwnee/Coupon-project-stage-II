package com.example.demo.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int companyId;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false, unique = true)
	private String companyEmail;
	@Column(nullable = false, length = 12)
	private String companyPassword;
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
	private List<Coupon> coupons;

	public Company() {
	}

	public Company(String name, String companyEmail, String companyPassword) {
		super();
		this.name = name;
		this.companyEmail = companyEmail;
		this.companyPassword = companyPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public int getCompanyId() {
		return companyId;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", companyEmail=" + companyEmail
				+ ", companyPassword=" + companyPassword + "]";
	}

}

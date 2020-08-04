package com.example.demo;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;
import com.example.demo.job.DailyJob;
import com.example.demo.loginManager.ClientType;
import com.example.demo.loginManager.LoginManager;

@SuppressWarnings("unused")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

		LoginManager LM = ctx.getBean(LoginManager.class);
		DailyJob job = ctx.getBean(DailyJob.class);

		// THREAD TESTING:

		Thread t1 = new Thread(job);
		t1.start();

		try {

			// ADMIN FACADE TESTING:
			AdminFacade af = (AdminFacade) LM.login("admin@admin.com", "admin", ClientType.Administrator); // login works

//			af.addCompany(new Company("Boba-Bola", "Boba@Bola.com", "test1234")); // creating company works + cannot create same company with name or email
//			af.addCompany(new Company("Gucci", "Gucci@USA.com", "Gucciwow"));
//			af.addCompany(new Company("DeleteTest", "some@some.com", "delete"));

//			Company company = af.getOneCompany(2); // updating works + cannot update to existing company name or email
//			company.setCompanyPassword("WowGucci");
//			af.updateCompany(company);

//		    af.deleteCompany(6); // deleting + deleting coupons works + deleting from customers works

//			System.out.println(af.getAllCompanies()); // works
//			System.out.println(af.getOneCompany(2)); // works

//			af.addCustomer(new Customer("Joe", "Baby", "Joe@Baby.com", "test1234")); // creating customer works + cannot create same customer with email
//			af.addCustomer(new Customer("Lisa", "Pizza", "Lisa@Pizza.com", "original"));
//			af.addCustomer(new Customer("delete", "me", "delete@this.com", "delete"));

//			Customer customer = af.getOneCustomer(2); // updating works + cannot update to existing email
//			customer.setCustomerEmail("Pizza@Lisa.com");
//			af.updateCustomer(customer);

//			af.deleteCustomer(3); // deleting + coupon purchases works

//			System.out.println(af.getAllCustomers()); // works
//			System.out.println(af.getOneCustomer(3)); // works
			
//			--------------------------------------------------------------------------------------------------------------------------------------------------

			// COMPANY FACADE TESTING:
//			CompanyFacade cof = (CompanyFacade) LM.login("Boba@Bola.com", "test1234", ClientType.Company); // login works

			Calendar calStart = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();

			calStart.set(2020, Calendar.AUGUST, 20);
			calEnd.set(2021, Calendar.APRIL, 20);

//			cof.addCoupon(new Coupon(cof.getCompanyDetails(), CategoryType.Restaurants, "Boring Test", "Testyyyy",
//					new Date(calStart.getTimeInMillis()), new Date(calEnd.getTimeInMillis()), 1000, 20.0,
//					"imagehere.jpg"));
//			cof.addCoupon(new Coupon(cof.getCompanyDetails(), CategoryType.Clothing, "Boring Test2", "Testyyyy",
//					new Date(calStart.getTimeInMillis()), new Date(calEnd.getTimeInMillis()), 1000, 20.0,
//					"imagehere.jpg"));
//			cof.addCoupon(new Coupon(cof.getCompanyDetails(), CategoryType.Electronics, "Delete test", "Testyyyy",
//					new Date(calStart.getTimeInMillis()), new Date(calEnd.getTimeInMillis()), 1000, 20.0,
//					"imagehere.jpg"));

//			Coupon coupon = cof.getOneCompanyCoupon(5);
//			coupon.setPrice(35);
//			cof.updateCoupon(coupon); // works

//			cof.deleteCoupon(3); // works

//			System.out.println(cof.getCompanyCoupons());
//			System.out.println(cof.getOneCompanyCoupon(2));
//			System.out.println(cof.getCompanyCoupons(CategoryType.Electronics, cof.getCompanyDetails()));
//			System.out.println(cof.getCompanyCoupons(25, cof.getCompanyDetails()));
//			System.out.println(cof.getCompanyDetails());
			
//			-------------------------------------------------------------------------------------------------------------------------------------------------

			// CUSTOMER FACADE TESTING:
//			CustomerFacade cuf = (CustomerFacade) LM.login("Joe@Baby.com", "test1234", ClientType.Customer); // login works

//			Coupon coupon = cof.getOneCompanyCoupon(1);
//			cuf.purchaseCoupon(coupon); // works + cannot purchase same coupon twice
//			System.out.println(cuf.getCustomerCoupons()); // get all coupons works
//			System.out.println(cuf.getCustomerCoupons(CategoryType.Restaurants)); // get coupons by category works
//			System.out.println(cuf.getCustomerCoupons(30)); // get all coupons by max price works
//			System.out.println(cuf.getCustomerDetails()); // works

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		job.stopJob();
		t1.interrupt();

	}

}

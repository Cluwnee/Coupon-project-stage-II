package com.example.demo.job;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.db.CouponRepository;
import com.example.demo.db.CustomerRepository;

@Component
public class DailyJob extends Thread {

	private CouponRepository coupRepo;
	private CustomerRepository custRepo;
	private boolean quit;

	public DailyJob(CouponRepository coupRepo, CustomerRepository custRepo) {
		this.coupRepo = coupRepo;
		this.custRepo = custRepo;
	}

	@Override
	public void run() {
		while (quit == false) {
			Calendar cal = Calendar.getInstance();
			Date now = new Date(cal.getTimeInMillis()); // Set today date for check
			try {
				for (Coupon coup : coupRepo.findAll()) {
					if (coup.getEndDate().before(now)) { // Find all coupons that are expired
						for (Customer customer : custRepo.findAll()) {
							customer.getCoupons().remove(coup);
							custRepo.save(customer); // After removing the coupon update the customer
						}
						coupRepo.delete(coup); // Finally delete the coupon
					}
				}
				Thread.sleep(86400000); // Sleep in the background for 24 hours and then start again
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void stopJob() { // the command that will force the thread to shut down and not remain sleeping
							// in the background
		quit = true;
		interrupt();
	}

}

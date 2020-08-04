package com.example.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	

}

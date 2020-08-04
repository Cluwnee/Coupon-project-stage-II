package com.example.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.beans.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}

package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

}

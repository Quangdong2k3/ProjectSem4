package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher,Long>{
    
}

package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.ShippingDetail;
import com.StoreBook.entity.User;

public interface ShippingRepository extends JpaRepository<ShippingDetail,Long>{

    ShippingDetail findByUser(User u);   
}

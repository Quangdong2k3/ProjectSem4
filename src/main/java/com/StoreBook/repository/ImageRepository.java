package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
	boolean existsByImg1(String img1);
    boolean existsByImg2(String img2);
    boolean existsByImg3(String img3);
    boolean existsByImg4(String img4);
    boolean existsByImg5(String img5);
}

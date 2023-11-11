package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
	boolean existsByImgone(String imgone);
    boolean existsByImgtwo(String imgtwo);
    boolean existsByImgthree(String imgthree);
    boolean existsByImgfour(String imgfour);
    boolean existsByImgfive(String imgfive);
}

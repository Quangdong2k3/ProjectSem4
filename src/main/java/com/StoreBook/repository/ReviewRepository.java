package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Book;
import com.StoreBook.entity.Review;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review,Long>{
    List<Review> findByBook_IdAndStatus(Long bookId,int status);
    


}

package com.StoreBook.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.StoreBook.entity.Book;
public interface BookRepository extends JpaRepository<Book,Long>{
    
}

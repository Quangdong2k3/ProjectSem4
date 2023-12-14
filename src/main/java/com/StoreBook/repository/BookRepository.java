package com.StoreBook.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.StoreBook.entity.Book;
public interface BookRepository extends JpaRepository<Book,Long>{
   
    @Query("SELECT b FROM Book b JOIN b.authors a")
    List<Book> findAllBooksWithAuthors();
}

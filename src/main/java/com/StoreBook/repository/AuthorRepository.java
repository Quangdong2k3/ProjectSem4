package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Author;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    void deleteByBooksId(Long bookId);
}

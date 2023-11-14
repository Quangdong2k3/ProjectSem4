package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Persistent_logins;

public interface PersistentLoginRepository extends JpaRepository<Persistent_logins, String> {
    Persistent_logins deleteByUsername(String username);
}

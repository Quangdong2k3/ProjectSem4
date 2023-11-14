package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

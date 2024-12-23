package com.ngocha.ecommerce.repository;

import com.ngocha.ecommerce.entity.Role;
import com.ngocha.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(String role);
}

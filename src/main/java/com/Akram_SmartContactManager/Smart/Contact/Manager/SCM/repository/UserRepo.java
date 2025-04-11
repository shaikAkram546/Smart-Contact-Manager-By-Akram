package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;

// This Interfase is used to connect with database
@Repository
public interface UserRepo extends JpaRepository<User, String> {
    
    // This methods are known as custom finder methods, these implementations are done by Spring data jpa    
    
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    
}

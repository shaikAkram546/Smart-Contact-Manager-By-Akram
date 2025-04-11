package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services;

 
 
import java.util.*;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;

 

 @Component
public interface UserServices {
// The Optional may get the null value it is usefull in if and any other condition.

   User saveUser(User user);

  Optional<User> getUserById(String id);

  Optional<User> updateUser(User user);

  void deleteUser(String id);

  boolean isUserExist(String userId);

  boolean isUserExistByEmail(String email);

  List<User> getAllUsers();

  User getUserByEmail(String email);
 
}
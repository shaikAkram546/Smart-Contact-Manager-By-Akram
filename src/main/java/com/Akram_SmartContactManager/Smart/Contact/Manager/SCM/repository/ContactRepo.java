package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.Contact;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;

 

 

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    List<Contact> findByUser(User user); //The impletentation of this method is given by JpaRepository itself.


    List<Contact> findByUserAndName(User user, String name);  // The findByUserAndName is predefined by the spring boot that need to be in camel case. Similarly the other for email and PhoneNumber.

    List<Contact> findByUserAndEmail(User user, String email);

    List<Contact> findByUserAndPhoneNumber(User user, String phoneNumber);
    //Custom Query method..
    @Query("select c from Contact c where c.user.id = :userId")
    List<Contact> findByUserid(@Param("userId") String userId);//The implementation of this method is not given by JpaRepository. need to write a query 
}

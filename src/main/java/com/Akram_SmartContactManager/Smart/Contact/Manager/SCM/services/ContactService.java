package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services;

import java.util.*;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.Contact;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;


public interface ContactService {


     // save the user
    Contact save(Contact contact);


    //update the user
    Contact update(Contact contact);

    //find All the contacts
    List<Contact> getAll();

    //find the contact by id 
    Contact getById(String id);

    //Delete contact by id
    void deleteContact(String id);
    
    //search contact
    List<Contact> search(String name, String email, String phoneNumber);

    List<Contact> findByName(User user, String name);

    List<Contact> findByEmail(User user, String email);

    List<Contact> findByPhoneNumber(User user, String phone);
    
    Contact findById(String id);

    //find the contact by userid
    List<Contact> getByUserId(String userId);

    List<Contact> getByUser(User user);
}

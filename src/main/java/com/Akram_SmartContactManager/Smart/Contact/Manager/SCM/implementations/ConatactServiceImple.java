package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.Contact;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.repository.ContactRepo;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.ContactService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ConatactServiceImple implements ContactService {

    @Autowired
    ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact existingContact = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + contact.getId()));
        existingContact.setName(contact.getName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhoneNumber(contact.getPhoneNumber());
        existingContact.setUser(contact.getUser());
        existingContact.setDescription(contact.getDescription());
        existingContact.setAddress(contact.getAddress());
        existingContact.setWebsiteLink(contact.getWebsiteLink());
        existingContact.setLinkedInLink(contact.getLinkedInLink());
        existingContact.setFavorite(contact.isFavorite());

        return contactRepo.save(existingContact);
    }

    @Override
    public List<Contact> getAll() {
       return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new RuntimeException("The Contact is not exists By the given ID  "+id)); // In this case we can also use ResourceNotFoundException.
    }

    @Override
    public void deleteContact(String id) {
       var contact = contactRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("The Contact is not exists By the given ID  " + id));
       contactRepo.delete(contact);
            
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return  contactRepo.findByUserid(userId);
    }

    @Override
    public List<Contact> getByUser(User user) {
        return contactRepo.findByUser(user);
    }

    @Override
    public List<Contact> findByName(User user, String name) {
        return contactRepo.findByUserAndName(user, name);
    }

    @Override
    public List<Contact> findByEmail(User user, String email) {
        return contactRepo.findByUserAndEmail(user, email);
    }

    @Override
    public List<Contact> findByPhoneNumber(User user, String phone) {
         return contactRepo.findByUserAndPhoneNumber(user, phone);
    }

    @Override
   public Contact findById(String id) {
    return contactRepo.findById(id)
                      .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + id));
}


   


}

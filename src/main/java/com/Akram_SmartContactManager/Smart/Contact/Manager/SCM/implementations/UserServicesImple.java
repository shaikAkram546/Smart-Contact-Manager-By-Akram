package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.exceptions.ResourcesNotFound;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.AppConstants;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.repository.UserRepo;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.UserServices;

@Service
public class UserServicesImple implements UserServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setUserId(UUID.randomUUID().toString());

        // Encode the password.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set user role.
        user.setRoleList(List.of(AppConstants.User_Roles));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        // Fetch user by ID and throw exception if not found.
        User userToUpdate = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourcesNotFound("User not found with ID: " + user.getUserId()));
        
        // Update user fields.
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setAbout(user.getAbout());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());
        userToUpdate.setProfilePic(user.getProfilePic());
        userToUpdate.setEnabled(user.isEnabled());
        userToUpdate.setEmailVerified(user.isEmailVerified());
        userToUpdate.setPhoneVerified(user.isPhoneVerified());
        userToUpdate.setProvider(user.getProvider());
        userToUpdate.setProviderUserId(user.getProviderUserId());

        // Save updated user to the database.
        User updatedUser = userRepo.save(userToUpdate);
        return Optional.ofNullable(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        User userToDelete = userRepo.findById(id)
                .orElseThrow(() -> new ResourcesNotFound("User not found with ID: " + id));
        userRepo.delete(userToDelete);
    }

    @Override
    public boolean isUserExist(String userId) {
        return userRepo.findById(userId).isPresent();
    }

       
    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        // Return null if user not found.
        return userRepo.findByEmail(email).orElseThrow(() -> 
            new ResourcesNotFound("User not found with email: " + email));
    }
}

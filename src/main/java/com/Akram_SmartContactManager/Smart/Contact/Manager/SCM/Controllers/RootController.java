package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.Helper;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.UserServices;

@ControllerAdvice
public class RootController {

    @Autowired
    UserServices userServices;

    @ModelAttribute
    public void returnUserNameForAllPages(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("loggedInUser", null); // Ensure it's null when no user is logged in
            return;
        }

        String loggedInEmail = Helper.getEmailOfLoggedInUser(authentication);
        if (loggedInEmail == null) {
            model.addAttribute("loggedInUser", null);
            return;
        }

        User user = userServices.getUserByEmail(loggedInEmail);
        if (user != null) {
            model.addAttribute("loggedInUser", user); // Pass user object if available
            System.out.println("This is the user => " + user.getName());
        } else {
            model.addAttribute("loggedInUser", null);
        }
    }
}

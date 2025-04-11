package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.Controllers;

import org.springframework.ui.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.UserServices;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserServices userServices;
   

    Logger logger = LoggerFactory.getLogger(UserController.class); //To log the messages.

    @RequestMapping(value = "/profile")
    public String profile(Model model, Authentication authentication) {
        
        return "user/profile";
    }
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "user/dashboard";
    }

 

    //view contacts page
    @RequestMapping(value = "/viewcontacts", method = RequestMethod.GET)
    public String viewContacts() {
        return "user/viewcontacts";
    }

    //fav page
    @RequestMapping(value = "/fav", method = RequestMethod.GET)
    public String fav() {
        return "user/fav";
    }
    
    //edit contacts
    @RequestMapping(value = "/editcontacts", method = RequestMethod.GET)
    public String editContacts() {
        return "user/editcontacts";
    }

    ///delete contacts
    @RequestMapping(value = "/deletecontacts", method = RequestMethod.GET)
    public String deleteContacts() {
        return "user/deletecontacts";
    }



}

package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.Controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.Contact;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.forms.ContactForm;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.Helper;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.Message;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.MessageType;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.ContactService;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.UserServices;
import org.springframework.security.core.Authentication;
import java.util.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
// import lombok.val; 




@Controller
@RequestMapping(value = "/user/contacts")

public class ContactController {



     private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
    @Autowired
    UserServices userService;
    
    @Autowired
    ContactService contactService;

    
    @RequestMapping()
    public String showContacts(Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact> contacts = contactService.getByUser(user);
        model.addAttribute("contacts", contacts);
        // System.out.println(contacts);
        return "user/contacts";
    }

 //To process the get requests
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddContactForm(ContactForm contactForm) {
        return "user/add_contacts"; // Name of the Thymeleaf template (view)
    }


    //add contacts page
      @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data

        // 1 validate form,the valid in the above parameter will validate the form. and bindResult  will bind the result, the result is a bean.

        if (result.hasErrors()) {

            // result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contacts";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);
        // There is no image processing done. and the image is not displaying in the project.
        // To display the image we can use Cloud image service, but for now we are not using that...
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        
        contactService.save(contact);
        System.out.println(contactForm);

        // 3 set the contact picture url

        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";

    }

    @RequestMapping(value ="/search")
    public String searchContact(
        Authentication authentication,
        @RequestParam ("value") String field,
        @RequestParam("keyword") String value,
        Model model
    ) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        // logger.info("This is value {} This is keyword {}",field,value);
        if (field.equalsIgnoreCase("name")) {
            List<Contact> contacts = contactService.findByName(user, value);
            // logger.info("Searching data by name");
            // System.out.println(contacts);
            Boolean isEmpty = contacts.isEmpty();
            model.addAttribute("isEmpty", isEmpty);
            model.addAttribute("contacts", contacts);
          
        }
        else if (field.equalsIgnoreCase("phone")) {
            List<Contact> contacts = contactService.findByPhoneNumber(user, value);
            //    System.out.println(contacts);
            Boolean isEmpty = contacts.isEmpty();
             model.addAttribute("isEmpty", isEmpty);
            model.addAttribute("contacts", contacts);
        }
        else if (field.equalsIgnoreCase("email")) {
            List<Contact> contacts = contactService.findByEmail(user, value);
            // System.out.println(contacts);
            Boolean isEmpty = contacts.isEmpty();
             model.addAttribute("isEmpty", isEmpty);
            model.addAttribute("contacts", contacts);
        }
      
        return "/user/search";
    }

    @RequestMapping("/view/{id}")
    public String viewUser(@PathVariable("id") String id, Model model) {
        // Fetch the user data using the ID
        Contact contact = contactService.findById(id);
        // Add user data to the model to pass it to the view
        model.addAttribute("contact", contact);
        // System.out.println(contact.getEmail()); 
        // Return the name of the view page (e.g., "userView")
        return "user/view";
    }
    
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        contactService.deleteContact(id);
        return "redirect:/user/contacts";
    }




//To process the get requests
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit_contact_form(@PathVariable("id") String id, Model model) {
        var contactFrom = new ContactForm();
        Contact contact = contactService.findById(id);
        contactFrom.setName(contact.getName());
        contactFrom.setEmail(contact.getEmail());
        contactFrom.setPhoneNumber(contact.getPhoneNumber());
        contactFrom.setAddress(contact.getAddress());
        contactFrom.setDescription(contact.getDescription());
        contactFrom.setFavorite(contact.isFavorite());
        contactFrom.setWebsiteLink(contact.getWebsiteLink());
        contactFrom.setLinkedInLink(contact.getLinkedInLink());

        model.addAttribute("contactFrom", contactFrom);

        System.out.println("This is edit section in contact controller");



     
        return "user/edit_contact"; // Name of the Thymeleaf template (view)

    }






   @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session, @PathVariable("id") String id) {

        // process the form data

        // 1 validate form,the valid in the above parameter will validate the form. and bindResult  will bind the result, the result is a bean.

        if (result.hasErrors()) {

            // result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/edit_contact";
        }

   
        Contact editedContact = contactService.findById(id);
        editedContact.setName(contactForm.getName());
        editedContact.setEmail(contactForm.getEmail());
        editedContact.setPhoneNumber(contactForm.getPhoneNumber());
        editedContact.setAddress(contactForm.getAddress());
        editedContact.setDescription(contactForm.getDescription());
        editedContact.setFavorite(contactForm.isFavorite());
        editedContact.setWebsiteLink(contactForm.getWebsiteLink());
        editedContact.setLinkedInLink(contactForm.getLinkedInLink());

        contactService.update(editedContact);
  

        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have edited successfully..")
                        .type(MessageType.green)
                        .build());

        
        return "redirect:/user/contacts/edit/"+ id; // by this it will redirect to the same function and the same page
       
    }


}

package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.Controllers;

 
// import org.hibernate.mapping.MetaAttributable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.entities.User;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.forms.UserForm;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.Message;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper.MessageType;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.EmailSenderService;
// import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.implementations.UserServicesImple;
import com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
 





@Controller
public class ViewController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private EmailSenderService emailSenderService;

    //landing page of login 
    @GetMapping("/")
    public String index() {
        return new String("/home");
    }
    
    


    //home 
    @RequestMapping("home")
    public String home(Model model) {
        model.addAttribute("name", "This is name of this page, the name is Home page");
        return  "home";
    }
    
    //about
    @RequestMapping("about")
    public String about(Model model) {

        return "about";
    }
    
    //login
    @GetMapping("login")
    public String login() {
        return "login";
    }

    

    // contact
    @GetMapping("contact")
    public String contact() {
        return "contact";
    }
    
    // services
    @GetMapping("services")
    public String services() {
        return "services";
    }
    
    // register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    //forms
    
      @RequestMapping(value = "/do-register", method = RequestMethod.POST)
      public String procesRegestration(@Valid @ModelAttribute UserForm userForm, HttpSession session,BindingResult bindingResult) {
          System.out.println(userForm); // In This place the values from the userFrom will store in the object of userForm.
          System.out.println("The form..");
          
          //validation part   The BindingResult is responsible to take all the errors when we validate.
          if (bindingResult.hasErrors()) {
              return "register";
        }
        





          //Passing the data to the data base
          User user = new User();
          user.setName(userForm.getName());
          user.setEmail(userForm.getEmail());
          user.setPassword(userForm.getPassword());
          user.setAbout(userForm.getAbout());
          user.setPhoneNumber(userForm.getPhoneNumber());

          userServices.saveUser(user);
        
          //   message content area
          Message message = Message.builder().content("Registration success").type(MessageType.green).build();
          session.setAttribute("message", message);
            
          
            
        return "redirect:/register";
        }

    @GetMapping("/feedback")
    public String showFeedbackForm() {
        return "user/feedback";  // Make sure user/feedback.html exists
    }

   @PostMapping("/feedback")
public String processFeedback(@RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("message") String message,HttpSession session) {
    
    System.out.println("Name: " + name);
    System.out.println("Email: " + email);
    System.out.println("Message: " + message);
    // System.out.println("This is the feedback controller");
    String subject = "Feedback from "+name;
 

    
    emailSenderService.sendMail(email, subject, message);
    session.setAttribute("message", Message.builder()
                    .content("Message sent sucessfully..")
                    .type(MessageType.green)
                    .build());

    return "user/feedback";  // Ensure you have a feedback.html in user folder
}








}





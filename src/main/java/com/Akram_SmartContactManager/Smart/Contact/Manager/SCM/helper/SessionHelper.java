package com.Akram_SmartContactManager.Smart.Contact.Manager.SCM.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    /**
     * 
     */
    // @SuppressWarnings("null")
    public static void removeMessage() {
        try {
            //System.out.println("removing message from session");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                    .getSession(); // The way to get session reference.
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("The error is " + e);
        }
    }
}

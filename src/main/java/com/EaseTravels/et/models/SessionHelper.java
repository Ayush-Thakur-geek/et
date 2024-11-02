package com.EaseTravels.et.models;


import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Log4j2
public class SessionHelper {
    public static void messageRemover() {
        try {
            log.info("Removing message from session");
            HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("Error in messageRemover: " + e.getMessage());
        }

    }
}

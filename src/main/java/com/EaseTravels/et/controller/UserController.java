package com.EaseTravels.et.controller;

import com.EaseTravels.et.forms.FlightForm;
import com.EaseTravels.et.services.UserService;
import io.ipinfo.api.IPinfo;
import io.ipinfo.api.model.IPResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private IPinfo ipinfo;  // Use IPinfo client to fetch data from API

    @Autowired
    private UserService userService;

    @GetMapping("/location")
    public String getClientLocation(HttpServletRequest request) {
        // Extract the client's IP address from the request
        String clientIp = userService.getClientIp(request);

        if (clientIp == null) {
            return "Unable to get IP address.";
        }

        try {
            // Get IP info details using the IPinfo API
            IPResponse ipResponse = ipinfo.lookupIP(clientIp);

            if (ipResponse != null) {
                // Return location details or other info available in IPResponse
                return ipResponse.toString();
            } else {
                return "No data found for IP: " + clientIp;
            }

        } catch (Exception e) {
            return "Error fetching IP info: " + e.getMessage();
        }
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        log.info("Inside dashboard handler");
        FlightForm flightForm = new FlightForm();
        model.addAttribute("flightForm", flightForm);
        return "user/dashboard";
    }


}

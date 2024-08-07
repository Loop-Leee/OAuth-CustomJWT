package com.alec.oauthshushengdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName: TestController
 * Package: com.alec.oauthshushengdemo.controller
 * Description:
 *
 * @Author Alec
 * @Create 2024/7/19 16:31
 * @Version 1.0
 */

@RestController
public class HomePageController {

    @GetMapping("/home")
    public ModelAndView toHomePage(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("No authentication found");
        } else {
            System.out.println("Authentication: " + authentication);
            System.out.println("Principal: " + authentication.getPrincipal());
            System.out.println("Authorities: " + authentication.getAuthorities());
        }
        String name =authentication.getName();
        ModelAndView homePage = new ModelAndView("homePage");
        homePage.addObject("message", "Hello From Message");
        homePage.addObject("name", name);
        return homePage;
    }


}

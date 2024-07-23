package com.alec.oauthshushengdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
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
        ModelAndView homePage = new ModelAndView("homePage");
        homePage.addObject("message", "Hello From Message");
        return homePage;
    }


}

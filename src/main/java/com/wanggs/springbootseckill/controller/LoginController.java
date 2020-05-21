package com.wanggs.springbootseckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Wgs
 * @version 1.0
 * @create：2020/05/21
 */
@Controller
@Slf4j
public class LoginController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/login")
    @ResponseBody
    public String login(String u, WebRequest request) {
        request.setAttribute("user", u, WebRequest.SCOPE_SESSION);
        return "port:" + port + ",login success";
    }

    @GetMapping("/check")
    @ResponseBody
    public String checkUser(WebRequest request) {
        String user = (String) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (user != null) {
            return "port:" + port + ",user=" + user;
        } else {
            return "port:" + port + ", redirect to login!";
        }
    }
}

package com.yh.tx.server1.controller;

import com.yh.tx.server1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/update/{email}")
    public String insertAndUpdate(@PathVariable("email") String email) {
        int i = userService.insertAndUpdate(email);
        return "success" + i;
    }
}

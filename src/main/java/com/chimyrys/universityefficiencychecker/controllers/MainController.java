package com.chimyrys.universityefficiencychecker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "index";
    }
}

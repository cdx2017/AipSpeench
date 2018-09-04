package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author cdx
 */
@Controller
public class OneController {

    @GetMapping("/")
    public String main() {
        return "main";
    }

}

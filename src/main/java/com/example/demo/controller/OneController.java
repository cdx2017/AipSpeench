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
    @GetMapping("/main")
    public String main1() {
        return "main";
    }
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

}

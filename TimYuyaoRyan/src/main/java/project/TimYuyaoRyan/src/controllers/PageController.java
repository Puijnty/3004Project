package project.TimYuyaoRyan.src.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    @GetMapping("/test")
    public String test(){
        return "test.html";
    }

}

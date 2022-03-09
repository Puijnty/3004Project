package project.TimYuyaoRyan.src.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/game")
    public String game() {
        return "game.html";
    }
}
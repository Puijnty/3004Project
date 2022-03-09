package project.TimYuyaoRyan.src.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import project.TimYuyaoRyan.src.models.*;

@RestController
public class RestServiceController {
@GetMapping("/id")
    public int createId(){
        PlayerId user = new PlayerId();
        return user.getId();
    }

}

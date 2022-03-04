package project.TimYuyaoRyan.src.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.TimYuyaoRyan.src.models.*;

@Controller
public class SocketMessagingController {
    @MessageMapping("/test")
    @SendTo("/test/Reply")
    public PlayerInfo playerInfo(){
           return new PlayerInfo("hello");
    }
}

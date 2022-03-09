package project.TimYuyaoRyan.src.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.TimYuyaoRyan.src.models.*;

@Controller
public class SocketMessagingController {
    @MessageMapping("/test")
    @SendTo("/test/Reply")
    public PlayerInfo hello(){return new PlayerInfo(1,"hello");}

    @MessageMapping("/join")
    @SendTo("/game/playerInfo")//need to make logic for id and  status
    public PlayerInfo lobby(){return new PlayerInfo(2,"waiting");}


}

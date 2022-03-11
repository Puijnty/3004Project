package project.TimYuyaoRyan.src.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.TimYuyaoRyan.src.models.*;
import project.TimYuyaoRyan.src.services.GameMaster;

@Controller
public class SocketMessagingController {
    @Autowired
    private GameMaster gameMaster;

    @MessageMapping("/test")
    @SendTo("/test/Reply")
    public PlayerInfo hello(){return new PlayerInfo(1,"hello");}

    @MessageMapping("/join")
    @SendTo("/game/playerInfo")//need to make logic for id and  status
    public PlayerInfo lobby(){return new PlayerInfo(2,"waiting");}

    @MessageMapping("/start")
    @SendTo("/game/Reply")
    public String start() {
        if (gameMaster.getNumPlayers() > 2) {
            gameMaster.initialize();
            return "Game Started!";
        }
        return "Not Enough Players!";
    }
}

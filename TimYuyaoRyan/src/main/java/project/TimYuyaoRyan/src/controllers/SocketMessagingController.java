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

    @MessageMapping("/join")
    public void joined(String message){
        if (Integer.parseInt(message)<5) {
            gameMaster.playerJoins(new PlayerInfo(Integer.parseInt(message), false));
        }
    }

    @MessageMapping("/start")
    @SendTo("/game/Reply")
    public String start() {
        if (gameMaster.getNumPlayers() >= 2) {
            System.out.println("here");
            gameMaster.initialize();
            return "Game Started!";
        }
        return "Not Enough Players!";
    }
}

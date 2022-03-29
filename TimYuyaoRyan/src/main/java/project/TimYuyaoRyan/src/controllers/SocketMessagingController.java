package project.TimYuyaoRyan.src.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.TimYuyaoRyan.src.models.CardDeck;
import project.TimYuyaoRyan.src.models.PlayerInfo;
import project.TimYuyaoRyan.src.services.GameMaster;


@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Controller
public class SocketMessagingController {
    @Autowired
    private GameMaster gameMaster;

    private static SimpMessagingTemplate template;

    @Autowired
    public SocketMessagingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/Continue")
    public void updateCont(boolean message){
        gameMaster.setCont(message);
    }

    @MessageMapping("/playedCard")
    public void playedCard(String message){
        gameMaster.setPlayedCard(message);
    }

    @MessageMapping("/join")
    public void joined(String message){
        if (Integer.parseInt(message)<5) {
            if(!gameMaster.isLaunched()) {
                gameMaster.playerJoins(new PlayerInfo(Integer.parseInt(message), false));
            }
        }
    }

    @MessageMapping("/start")
    @SendTo("/game/Reply")
    public String start() {
        if (!gameMaster.isLaunched()) {
            if (gameMaster.getNumPlayers() >= 2) {
                System.out.println("Game initializing");
                gameMaster.initialize();
                return "Game Ended!";
            }
            return "Not Enough Players!";
        }
        return "Already Started!";
    }

    public static void sendHand(PlayerInfo p){
        template.convertAndSend("/game/Turn",
               "{\"id\":" +p.getId()+ ",\"turn\":" +p.isTurn()+ ",\"cards\":"+p.getHand()+"}");
    }

    public static void sendDiscard(CardDeck pile){
        template.convertAndSend("/game/Discard",pile.toString());
    }

    public static void playTurn(PlayerInfo p,String message,int type){
        template.convertAndSend("/game/Turn",
                "{\"id\":" +p.getId()+ ",\"turn\":" +p.isTurn() + ",\"type\":"+type+",\"message\":\""+message + "\",\"cards\":"+p.getHand()+"}");
    }

    public static void StartMessage(){
        template.convertAndSend("/game/Reply","Game Started!");
    }

}

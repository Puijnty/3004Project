package project.TimYuyaoRyan.src.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import project.TimYuyaoRyan.src.models.*;
import java.util.ArrayList;
import java.util.Scanner;

@Service
@Scope("application")
public class GameMaster {
    ArrayList<PlayerInfo> players=new ArrayList<PlayerInfo>();
    int currentTurn;
    CardDeck advDeck = new CardDeck();
    CardDeck storyDeck = new CardDeck();
    CardDeck discardDeck = new CardDeck();

    public void playerJoins(PlayerInfo p){
        players.add(p);
    }

    public int getNumPlayers() {
        return players.size();
    }

    public void discard(String s){
        //Expected format: {"player":"1","cardname":"sword"}
        s.substring(2, s.length()-2);
        String[] arrStr = s.split(",");
        String[] s2 = arrStr[0].split(":");
        String[] s3 = arrStr[1].split(":");
       Card removedCard = players.get(Integer.parseInt(s2[1])).remove(s3[1]);
        if(removedCard != null) {
            discardDeck.add(removedCard);
        }


    }

    public void initialize(){
        System.out.println("Generating card decks");
        CreateDecks();

        System.out.println("Distributing cards to players");
        for (int i = 0; i < players.size(); i++) {

            //Give each player the squire card
            Card c = new RankCard("Squire");
            players.get(i).give(c);

            //Give each player 12 adventure cards
            for (int j = 0; j < 12; j++) {
                players.get(i).give(advDeck.draw());
            }
        }
        currentTurn = 0;
        //Game setup complete, begin main loop.
        run();
    }

    public void nextTurn(){
        currentTurn+=1;
        if(currentTurn > players.size()){
            currentTurn = 0;
        }
    }

    public void run(){
        while(true) {
            //draw 3 adventure cards at the start of your turn
            //players.get(currentTurn).give(advDeck.draw());
            Scanner input = new Scanner(System.in);

            Card c = storyDeck.draw();
            CardDeck stageDeck = new CardDeck();
            int sponsorCards = 0;

            if(c.getType() == "quest"){
                //Do quest things
                //Should enter the questing loop and ask if players want to sponsor quests
                //While we figure this out, it waits on a text input to avoid an infinite loop of drawing cards and emptying the deck
                //NOTE: When a deck runs out, create method to return all cards to it from the discard pile

                //Ask every player if they wish to sponsor the quest starting with the player whose turn it is
                //Sponsoring player sets down a foe with weapons for each stage or a test for each stage
                //All non-sponsors attempt to complete the quest
                int sponsor = -1;
                int stages = c.getPotency();
                boolean testBool = false;

                for (int i = 0; i < players.size(); i++) {
                    int sponsorRequest = i + currentTurn;
                    if(sponsorRequest > players.size()){
                        sponsorRequest -= players.size();
                    }
                    players.get(sponsorRequest);
                    System.out.println("Asking player " + sponsorRequest + " if they wish to sponsor the quest...");
                    String temp = input.nextLine();
                    if(temp == "Y" && players.get(sponsorRequest).countQuestComponents() >= stages){
                        sponsor = sponsorRequest;
                        i = 10; //Will exit the loop due to being above max players
                    }
                    else if(temp == "Y"){
                        System.out.println("This player does not have enough foe and tests to sponsor this quest!");
                    }
                }

                if(sponsor != -1){
                    for (int i = 0; i < stages; i++) {
                        //Pick a card for each stage
                        Card stageCard = c;
                        //Ensure that the card is the correct type and that no more than one test is played
                        while(stageCard.getType() != "foe" || (stageCard.getType() != "test" || stageCard.getType() == "test" && testBool == true)){
                            System.out.println("What would you like to play for stage " + i + "?");
                            String temp = input.nextLine();
                            stageCard = players.get(sponsor).getCard(temp);
                            if(stageCard.getType() == "test" && testBool == true){
                                System.out.println("Two tests cannot be played in the same quest!");

                            }
                        }
                        stageDeck.add(stageCard);
                        players.get(sponsor).remove(stageCard.getTitle());
                        sponsorCards++;
                        if(stageCard.getType() == "test"){
                            testBool = true;
                        }

                        //If the card is a foe, ask if you want to add a weapon
                        if(stageCard.getType() == "foe"){
                            System.out.println("Would you like to attach a weapon?");
                            String temp = input.nextLine();
                            if(temp == "Y" && players.get(sponsor).countWeapons() >= 1) {
                                //Allows the attachment of multiple weapons
                                while (temp == "Y" && players.get(sponsor).countWeapons() >= 1) {
                                    while (stageCard.getType() != "weapon") {
                                        System.out.println("What weapon would you like to attach?");
                                        temp = input.nextLine();
                                        stageCard = players.get(sponsor).getCard(temp);
                                    }
                                    stageDeck.add(stageCard);
                                    players.get(sponsor).remove(stageCard.getTitle());
                                    sponsorCards++;
                                    System.out.println("Would you like to add another weapon?");
                                    temp = input.nextLine();
                                }
                            }
                            if(temp=="Y"){
                                //Will only fire if the player wishes to play a weapon and has none in hand
                                System.out.println("No weapons left in hand!");
                            }
                        }
                    }

                    //Quest is fully set up
                    //At each stage of the quest, each participant still in the quest draws a card
                    sponsorCards += stages;

                }
                else{
                    System.out.println("Nobody sponsors the quest!");
                }

                //Sponsor gains x + y cards where x is the number of stages and y is the number of cards they used to set up the quest
                for (int i = 0; i < sponsorCards; i++) {
                    players.get(sponsor).give(advDeck.draw());
                }
            }
            else if(c.getType() == "tournament"){
                //Do tournament things
                //Everyone draws 1 adventure card
                for (int i = 0; i < players.size(); i++) {
                  players.get(i).give(advDeck.draw());
                }
            }
            else if(c.getType() == "event"){
                //Do event things
            }
            nextTurn();
        }
    }

    private void CreateDecks() {
        //RANK CARDS are not included in any decks, and should be generated dynamically when needed
        CardGenerator advGen = new AdventureCardGenerator();
        CardGenerator storyGen = new StoryCardGenerator();

        //Foes spawn in large number and are thus generated with
        advDeck.add(advGen.publish("enemy_dragon", "foe"));
        for(int i = 0; i < 2; i++){
            advDeck.add(advGen.publish("enemy_giant", "foe"));
            advDeck.add(advGen.publish("enemy_green", "foe"));
        }
        for(int i = 0; i < 4; i++){
            advDeck.add(advGen.publish("enemy_mordred", "foe"));
            advDeck.add(advGen.publish("enemy_boar", "foe"));
        }
        for(int i = 0; i < 5; i++){
            advDeck.add(advGen.publish("enemy_saxons", "foe"));
        }
        for(int i = 0; i < 6; i++){
            advDeck.add(advGen.publish("enemy_evil", "foe"));
        }
        for(int i = 0; i < 7; i++){
            advDeck.add(advGen.publish("enemy_robber", "foe"));
        }
        for(int i = 0; i < 8; i++){
            advDeck.add(advGen.publish("enemy_saxKnight", "foe"));
            advDeck.add(advGen.publish("enemy_thieves", "foe"));
        }

        //Weapons spawn in large number and are thus generated with loops
        for(int i = 0; i < 11; i++){
            advDeck.add(advGen.publish("weapon_horse", "weapon"));
        }
        for(int i = 0; i < 16; i++) {
            advDeck.add(advGen.publish("weapon_sword", "weapon"));
        }
        for(int i = 0; i < 6; i++){
            advDeck.add(advGen.publish("weapon_dagger", "weapon"));
            advDeck.add(advGen.publish("weapon_lance", "weapon"));
        }
        for(int i = 0; i < 2; i++){
            advDeck.add(advGen.publish("weapon_excalibur", "weapon"));
        }
        for(int i = 0; i < 8; i++){
            advDeck.add(advGen.publish("weapon_battleax", "weapon"));
        }

        advDeck.add(advGen.publish("ally_gawain", "ally"));
        advDeck.add(advGen.publish("ally_pellinore", "ally"));
        advDeck.add(advGen.publish("ally_percival", "ally"));
        advDeck.add(advGen.publish("ally_tristan", "ally"));
        advDeck.add(advGen.publish("ally_arthur", "ally"));
        advDeck.add(advGen.publish("ally_guinevere", "ally"));
        advDeck.add(advGen.publish("ally_merlin", "ally"));
        advDeck.add(advGen.publish("ally_iseult", "ally"));
        advDeck.add(advGen.publish("ally_lancelot", "ally"));
        advDeck.add(advGen.publish("ally_galahad", "ally"));

        for(int i = 0; i < 8; i++){
            advDeck.add(advGen.publish("ally_amour", "amour"));
        }

        advDeck.add(advGen.publish("test_quest", "test"));
        advDeck.add(advGen.publish("test_quest", "test"));
        advDeck.add(advGen.publish("test_temptation", "test"));
        advDeck.add(advGen.publish("test_temptation", "test"));
        advDeck.add(advGen.publish("test_valor", "test"));
        advDeck.add(advGen.publish("test_valor", "test"));
        advDeck.add(advGen.publish("test_morgan", "test"));
        advDeck.add(advGen.publish("test_morgan", "test"));

        storyDeck.add(storyGen.publish("quest_forest", "quest"));
        storyDeck.add(storyGen.publish("quest_vanquish", "quest"));
        storyDeck.add(storyGen.publish("quest_vanquish", "quest"));
        storyDeck.add(storyGen.publish("quest_repel", "quest"));
        storyDeck.add(storyGen.publish("quest_repel", "quest"));
        storyDeck.add(storyGen.publish("quest_hunt", "quest"));
        storyDeck.add(storyGen.publish("quest_hunt", "quest"));
        storyDeck.add(storyGen.publish("quest_quest", "quest"));
        storyDeck.add(storyGen.publish("quest_defend", "quest"));
        storyDeck.add(storyGen.publish("quest_slay", "quest"));
        storyDeck.add(storyGen.publish("quest_rescue", "quest"));
        storyDeck.add(storyGen.publish("quest_search", "quest"));
        storyDeck.add(storyGen.publish("quest_test", "quest"));

        /*
        storyDeck.add(storyGen.publish("tournament_camelot", "tournament"));
        storyDeck.add(storyGen.publish("tournament_orkney", "tournament"));
        storyDeck.add(storyGen.publish("tournament_tintagel", "tournament"));
        storyDeck.add(storyGen.publish("tournament_york", "tournament"));

        storyDeck.add(storyGen.publish("event_deed", "event"));
        storyDeck.add(storyGen.publish("event_pox", "event"));
        storyDeck.add(storyGen.publish("event_plague", "event"));
        storyDeck.add(storyGen.publish("event_recognition", "event"));
        storyDeck.add(storyGen.publish("event_recognition", "event"));
        storyDeck.add(storyGen.publish("event_queen", "event"));
        storyDeck.add(storyGen.publish("event_queen", "event"));
        storyDeck.add(storyGen.publish("event_court", "event"));
        storyDeck.add(storyGen.publish("event_court", "event"));
        storyDeck.add(storyGen.publish("event_calltoarms", "event"));
        storyDeck.add(storyGen.publish("event_prosperity", "event"));
        */
    }
}

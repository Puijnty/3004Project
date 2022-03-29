package project.TimYuyaoRyan.src.services;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;
import project.TimYuyaoRyan.src.controllers.SocketMessagingController;
import project.TimYuyaoRyan.src.models.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Service
@Scope("application")
public class GameMaster {
    public boolean isLaunched() {
        return launched;
    }

    public void setPlayedCard(String playedCard) {this.playedCard = playedCard;}

    public void setCont(boolean cont) {
        this.cont = cont;
        this.changed=true;
        System.out.println("finished set cont. cont="+this.cont+" changed="+changed);
    }


    String playedCard="";
    boolean cont=false;
    boolean changed=false;
    boolean launched=false;

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
        SocketMessagingController.sendDiscard(discardDeck);
    }


    public void initialize(){
        launched=true;
        System.out.println("Generating card decks");
        CreateDecks();

        System.out.println("Distributing cards to players");
        for (int i = 0; i < players.size(); i++) {

            //Give each player the squire card
            Card c = new RankCard("Squire");
            players.get(i).give(c);

            //Give each player 12 adventure cards
            System.out.println("Giving players starting cards. If there is a crash here, you have more players than there are cards in the deck for them");
            for (int j = 0; j < 12; j++) {
                players.get(i).give(advDeck.draw());
            }

            SocketMessagingController.sendHand(players.get(i));


        }
        currentTurn = 0;
        //Game setup complete, begin main loop.
        SocketMessagingController.StartMessage();
        run();

    }

    public void nextTurn(){
        currentTurn+=1;
        if(currentTurn > players.size()){
            currentTurn = 0;
        }
    }

    public void run(){
        boolean recognition = false;
        while(true) {
            //draw 3 adventure cards at the start of your turn
            //players.get(currentTurn).give(advDeck.draw());
            Scanner input = new Scanner(System.in);

            CheckDeck(storyDeck);
            Card c = storyDeck.draw();
            CardDeck stageDeck = new CardDeck();

            if(c.getType() == "quest"){
                int sponsorCards = 0;
                //Do quest things
                //Should enter the questing loop and ask if players want to sponsor quests
                //While we figure this out, it waits on a text input to avoid an infinite loop of drawing cards and emptying the deck

                //Ask every player if they wish to sponsor the quest starting with the player whose turn it is
                //Sponsoring player sets down a foe with weapons for each stage or a test for each stage
                //All non-sponsors attempt to complete the quest
                int sponsor = -1;
                int stages = c.getPotency();
                boolean testBool = false;

                for (int i = 0; i < players.size(); i++) {
                    int sponsorRequest = i + currentTurn;
                    if(sponsorRequest >= players.size()){
                        sponsorRequest -= players.size();
                    }
                    //players.get(sponsorRequest);
                    String message = ("Player " + sponsorRequest + ", would you like to sponsor this quest?");
                    boolean playerSponsors = GetCont(message, players.get(sponsorRequest));
                    if(playerSponsors && players.get(sponsorRequest).countQuestComponents() >= stages){
                        sponsor = sponsorRequest;
                        i = 10; //Will exit the loop due to being above max players
                    }
                    else if(playerSponsors){
                        System.out.println("This player does not have enough foe and tests to sponsor this quest!");
                    }
                }

                if(sponsor != -1){
                    for (int i = 0; i < stages; i++) {
                        //Pick a card for each stage
                        Card stageCard = c;
                        //Ensure that the card is the correct type and that no more than one test is played
                        while(stageCard.getType() != "foe" || (stageCard.getType() != "test" || stageCard.getType() == "test" && testBool)){
                            String message = "What would you like to play for stage " + i + "?";
                            String temp = GetPlayedCardName(message, players.get(sponsor));
                            stageCard = players.get(sponsor).getCard(temp);
                            if(stageCard.getType() == "test" && testBool){
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
                            String message = ("Would you like to attach a weapon?");
                            boolean temp = GetCont(message, players.get(sponsor));
                            if(temp && players.get(sponsor).countWeapons() >= 1) {
                                //Allows the attachment of multiple weapons
                                while (temp && players.get(sponsor).countWeapons() >= 1) {
                                    while (stageCard.getType() != "weapon") {
                                        message = ("What weapon would you like to attach?");
                                        message = GetPlayedCardName(message, players.get(sponsor));
                                        stageCard = players.get(sponsor).getCard(message);
                                    }
                                    stageDeck.add(stageCard);
                                    players.get(sponsor).remove(stageCard.getTitle());
                                    sponsorCards++;
                                    message = ("Would you like to attach another weapon?");
                                    temp = GetCont(message, players.get(sponsor));
                                }
                            }
                            if(temp){
                                //Will only fire if the player wishes to play a weapon and has none in hand
                                System.out.println("No weapons left in hand!");
                            }
                        }
                    }

                    //Quest is fully set up, the number of stages that the quest contains are added to the
                    // amount of cards the sponsor will draw when the quest is complete
                    sponsorCards += stages;

                    //Players will now attempt to clear the quest one stage at a time
                    //Create the list of participants
                    ArrayList<Integer> questers = new ArrayList<Integer>();
                    for (int i = 0; i < players.size(); i++) {
                        if(players.get(i).getId() != sponsor){
                            questers.add(players.get(i).getId());
                        }
                    }

                    for (int i = 0; i < stages; i++) {
                        Card stageCard = stageDeck.next();
                        stageDeck.remove(stageCard.getTitle());

                        for (int j = 0; j < questers.size(); j++) {
                            //At each stage of the quest, each participant still in the quest draws a card
                            CheckDeck(advDeck);
                            players.get(questers.get(j)).give(advDeck.draw());
                        }

                        if(stageCard.getType() == "foe"){
                            //Read the full strength of the foe based on conditions and weapons
                            int foeStrength = stageCard.getPotency();
                            if(stageCard.getTitle() == c.getCondition() || "all" == c.getCondition()){
                               foeStrength = stageCard.getConditionalPotency();
                            }
                            while(stageDeck.next().getType() == "weapon"){
                                foeStrength += stageDeck.next().getPotency();
                                stageDeck.remove(stageDeck.next().getTitle());
                            }
                            //Loop through players and determine if they can defeat the foe for this stage
                            for (int j = 0; j < questers.size(); j++) {
                                int questerStrength = 5;
                                if(players.get(questers.get(j)).getAmour()){
                                    questerStrength += 10;
                                }
                                String message = ("Your strength is " + questerStrength + ", would you like to play a card to enhance this?");
                                Card questerCard = stageCard;
                                boolean temp = GetCont(message, players.get(questers.get(j)));
                                while(temp && players.get(questers.get(j)).countBattle() >= 1) {
                                    //Allows the attachment of multiple weapons
                                    while (questerCard.getType() != "weapon" && questerCard.getType() != "amour" && questerCard.getType() != "ally") {
                                        message = ("What would you like to play?");
                                        message = GetPlayedCardName(message, players.get(questers.get(j)));
                                        questerCard = players.get(questers.get(j)).getCard(message);
                                    }
                                    if(questerCard.getType() == "weapon") {
                                        questerStrength += questerCard.getPotency();
                                    }
                                    else if(questerCard.getType() == "ally"){
                                        //To be implemented
                                    }
                                    else if(questerCard.getType() == "amour" && !players.get(questers.get(j)).getAmour()){
                                        questerStrength += 10;
                                        players.get(questers.get(j)).activateAmour();
                                    }
                                    else{
                                        System.out.println("Cannot play an amour card while amour is already active!");
                                    }
                                    players.get(questers.get(j)).remove(stageCard.getTitle());
                                    sponsorCards++;
                                    message = ("Would you like to add another weapon?");
                                    temp = GetCont(message, players.get(questers.get(j)));
                                }
                                if(temp){
                                    //Will only fire if the player wishes to play a weapon and has none in hand
                                    System.out.println("No playable cards left in hand!");
                                }
                                if(questerStrength >= foeStrength){
                                    System.out.println("The quester is victorious!");
                                }
                                else{
                                    System.out.println("The foe is victorious!");
                                    questers.remove(j);
                                }
                            }
                        }
                        else if(stageCard.getType() == "test"){
                            int minBid = 0;
                            boolean temp;

                            //Set a defined min bid when player is alone in a quest
                            if(questers.size() == 1){
                                //Questing beast is the only test that will assign a min bid that is not 3
                                if(c.getTitle() == "quest_quest" && stageCard.getTitle() == "test_quest"){
                                    minBid = 4;
                                }
                                else {
                                    minBid = 3;
                                }
                                //Does the player want to win the test if it is possible?
                                String message = ("Will you sacrifice " + minBid + " cards to pass this test?");
                                temp = GetCont(message, players.get(questers.get(0)));
                                if (temp && players.get(questers.get(0)).countMaxBid() > minBid) {
                                    int removedCards = players.get(questers.get(0)).countFreeBid();
                                    while(removedCards <= minBid){
                                        message = (removedCards + "/" + (minBid+1) + " bids complete.\n");
                                        message+=("Please select a card to remove, player " + players.get(questers.get(0)));
                                        message = GetPlayedCardName(message, players.get(questers.get(0)));
                                        players.get(questers.get(0)).remove(message);
                                        removedCards++;
                                        //Add something to verify that the cards are being properly removed here
                                    }
                                    minBid++;
                                } else {
                                    System.out.println("The player has failed the test due to lacking possible bids or lack of will.");
                                    questers.remove(0);
                                }
                            }

                            while(questers.size() > 1) {
                                for (int j = 0; j < questers.size(); j++) {
                                    String message = ("Player " + players.get(questers.get(j)) + ", will you bid at least " + (minBid + 1) + " cards to overcome this test?\n");
                                    message += ("Please play any cards that will grant free bids for the next component before responding.");
                                    //ALLOW PLAYERS TO PLAY ALLY CARDS OR AMOUR CARDS HERE
                                    temp = GetCont(message, players.get(questers.get(j)));
                                    if (temp && players.get(questers.get(j)).countMaxBid() > minBid) {
                                        int removedCards = players.get(questers.get(j)).countFreeBid();
                                        while(removedCards <= minBid){
                                            message = (removedCards + "/" + (minBid+1) + " bids complete.\n");
                                            message += ("Please select a card to remove, player " + players.get(questers.get(j)));
                                            message = GetPlayedCardName(message, players.get(questers.get(j)));
                                            players.get(questers.get(j)).remove(message);
                                        }
                                        minBid++;
                                    } else {
                                        System.out.println("The player has failed the test due to lacking possible bids or lack of will.");
                                        questers.remove(j);
                                    }
                                }
                            }
                            if(questers.size() == 1) {
                                System.out.println("The test is complete, and only one player remains.");
                            }
                            else{
                                System.out.println("No knight in the realm was worthy of passing this test...");
                            }
                        }
                    }
                    //Quest is completed! All players have been eliminated or the quest is done
                    System.out.println("Quest complete!");
                    for (int j = 0; j < questers.size(); j++) {
                        players.get(questers.get(j)).award(stages);
                        if(recognition){
                            players.get(questers.get(j)).award(2);
                        }
                    }
                }
                else{
                    System.out.println("Nobody sponsors the quest!");
                }

                //Sponsor gains x + y cards where x is the number of stages and y is the number of cards they used to set up the quest
                for (int i = 0; i < sponsorCards; i++) {
                    CheckDeck(advDeck);
                    players.get(sponsor).give(advDeck.draw());
                }
                //Disable amour buff at the end of the quest
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).disableAmour();
                }
                //Disable recognition event at the end of the quest
                recognition = false;
            }


            else if(c.getType() == "tournament"){
                //Do tournament things
                //Everyone draws 1 adventure card
                ArrayList<Integer> participants = new ArrayList<Integer>();

                //Ask if players wish to compete
                for (int i = 0; i < players.size(); i++) {
                    int joinRequest = i + currentTurn;
                    if(joinRequest >= players.size()){
                        joinRequest -= players.size();
                    }
                    //players.get(joinRequest);
                    String message = ("Asking player " + joinRequest + " if they wish to join the tourney...");
                    boolean temp = GetCont(message, players.get(joinRequest));
                    if(temp){
                        participants.add(joinRequest);
                    }
                    else{
                        System.out.println("This player does not join the tournament!");
                    }
                }
                //Award all participating players 1 card
                for (int i = 0; i < participants.size(); i++) {
                    CheckDeck(advDeck);
                    players.get(participants.get(i)).give(advDeck.draw());
                }
                if(participants.size() > 1){
                    //All participants choose which cards to play and then reveal to one another at the same time
                    int playerStrength[] = new int[participants.size()];

                    int originalParticipants = participants.size();
                    Card tourneyCard = c;

                    //Everyone plays their cards
                    for (int i = 0; i < participants.size(); i++) {
                        String message = ("Would you like to play a card? (player " + players.get(participants.get(i)) + ")");
                        boolean temp = GetCont(message, players.get(participants.get(i)));
                        while(temp && players.get(participants.get(i)).countBattle() >= 1) {
                            //Allows the attachment of multiple weapons
                            while (tourneyCard.getType() != "weapon" && tourneyCard.getType() != "amour" && tourneyCard.getType() != "ally") {
                                message = ("What would you like to play?");
                                message = GetPlayedCardName(message, players.get(participants.get(i)));
                                tourneyCard = players.get(participants.get(i)).getCard(message);
                            }
                            if(tourneyCard.getType() == "weapon") {
                                playerStrength[i] += tourneyCard.getPotency();
                            }
                            else if(tourneyCard.getType() == "ally"){
                                //To be implemented
                            }
                            else if(tourneyCard.getType() == "amour" && !players.get(participants.get(i)).getAmour()){
                                playerStrength[i] += 10;
                                players.get(participants.get(i)).activateAmour();
                            }
                            else{
                                System.out.println("Cannot play an amour card while amour is already active!");
                            }
                            players.get(participants.get(i)).remove(tourneyCard.getTitle());
                            message = ("Would you like to add another weapon?");
                            temp = GetCont(message, players.get(participants.get(i)));
                        }
                        if(temp){
                            //Will only fire if the player wishes to play a weapon and has none in hand
                            System.out.println("No playable cards left in hand!");
                        }
                    }

                    //Reveal the strongest participant
                    int topStrength = -1;
                    for (int i = 0; i < participants.size(); i++) {
                        if(playerStrength[i] > topStrength){
                            topStrength = playerStrength[i];
                        }
                    }
                    //Remove all participants that are defeated in round 1
                    for (int i = 0; i < participants.size(); i++) {
                        if(playerStrength[i] < topStrength){
                            participants.remove(i);
                        }
                    }
                    if(participants.size() == 1){
                        System.out.println("Player " + players.get(participants.get(0)) + " is victorious! They earn " + (c.getPotency() + originalParticipants) + " shields!");
                    }
                    else{
                        //Tiebreaker

                        //Do the whole 'play your cards' thing again
                        for (int i = 0; i < participants.size(); i++) {
                            String message = ("Would you like to play a card? (player " + players.get(participants.get(i)) + ")");
                            boolean temp = GetCont(message, players.get(participants.get(i)));
                            while(temp && players.get(participants.get(i)).countBattle() >= 1) {
                                //Allows the attachment of multiple weapons
                                while (tourneyCard.getType() != "weapon" && tourneyCard.getType() != "amour" && tourneyCard.getType() != "ally") {
                                    message = ("What would you like to play?");
                                    message = GetPlayedCardName(message, players.get(participants.get(i)));
                                    tourneyCard = players.get(participants.get(i)).getCard(message);
                                }
                                if(tourneyCard.getType() == "weapon") {
                                    playerStrength[i] += tourneyCard.getPotency();
                                }
                                else if(tourneyCard.getType() == "ally"){
                                    //To be implemented
                                }
                                else if(tourneyCard.getType() == "amour" && !players.get(participants.get(i)).getAmour()){
                                    playerStrength[i] += 10;
                                    players.get(participants.get(i)).activateAmour();
                                }
                                else{
                                    System.out.println("Cannot play an amour card while amour is already active!");
                                }
                                players.get(participants.get(i)).remove(tourneyCard.getTitle());
                                message = ("Would you like to add another weapon?");
                                temp = GetCont(message, players.get(participants.get(i)));
                            }
                            if(temp){
                                //Will only fire if the player wishes to play a weapon and has none in hand
                                System.out.println("No playable cards left in hand!");
                            }
                        }

                        topStrength = -1;
                        for (int i = 0; i < participants.size(); i++) {
                            if(playerStrength[i] > topStrength){
                                topStrength = playerStrength[i];
                            }
                        }
                        //Remove all participants that are defeated in round 2
                        for (int i = 0; i < participants.size(); i++) {
                            if(playerStrength[i] < topStrength){
                                participants.remove(i);
                            }
                        }
                        if(participants.size() == 1){
                            System.out.println("Player " + players.get(participants.get(0)) + " is victorious! They earn " + (c.getPotency() + originalParticipants) + " shields!");
                        }
                        else{
                            System.out.println("The tiebreaker round is still a tie! All participants that won this round earn " + originalParticipants + " shields!");
                            for (int i = 0; i < participants.size(); i++) {
                                players.get(participants.get(i)).award(originalParticipants);
                            }
                        }
                    }
                }
                else if(participants.size() == 1){
                    System.out.println("The sole participant gains " + (1 + c.getPotency()) + " shields!");
                    players.get(participants.get(0)).award(1 + c.getPotency());
                }
                else{
                    System.out.println("Noone dares to compete in the tournament!");
                }
            }
            else if(c.getType() == "event"){
                //Display event to players
                System.out.println(c.getSummary());
                switch(c.getTitle()){
                    case "event_deed":
                        //Players with the least shields receive 3 shields
                        int min_shields = 100;
                        for (int i = 0; i < players.size(); i++) {
                            if(players.get(i).getShields() < min_shields){
                                min_shields = players.get(i).getShields();
                            }
                        }
                        for (int i = 0; i < players.size(); i++) {
                            if(players.get(i).getShields() == min_shields){
                                players.get(i).award(3);
                            }
                        }
                        break;
                    case "event_pox":
                        //Everyone except the player drawing the card loses 1 shield
                        for (int i = 0; i < players.size(); i++) {
                            if(players.get(i).getShields() > 0 && i != currentTurn){
                                players.get(i).award(-1);
                            }
                        }
                        break;
                    case "event_plague":
                        //The player drawing loses 2 shields, cannot go below 0
                        players.get(currentTurn).award(-2);
                        if(players.get(currentTurn).getShields() < 0){
                            players.get(currentTurn).award(0 - players.get(currentTurn).getShields());
                        }
                        break;
                    case "event_recognition":
                        //The next quest awards 2 extra shields
                        recognition = true;
                        break;
                    case "event_queen":
                    case "event_prosperity":
                        //All players draw 2 adventure cards
                        //Lowest rank players receive 2 adventure cards (but everyone is the lowest rank)
                        for (int i = 0; i < players.size(); i++) {
                            CheckDeck(advDeck);
                            players.get(i).give(advDeck.draw());
                            CheckDeck(advDeck);
                            players.get(i).give(advDeck.draw());
                        }
                        break;
                    case "event_court":
                        //All allies are removed
                        //There are no allies at the moment so the card does nothing
                        break;
                    case "event_calltoarms":
                        //Highest ranked player (all players) must discard 1 weapon, or 2 foes if unable
                        //TBD
                        break;
                }
            }
            nextTurn();
        }
    }

    private void CheckDeck(CardDeck c){
        if(c.getSize() == 0){
            reshuffle();
        }
    }

    private void reshuffle(){
        System.out.println("Reshuffling the deck using the discard pile!");
        for (int i = 0; i < discardDeck.getSize(); i++) {
            Card c = discardDeck.draw();
            if(c.getType() == "tournament" || c.getType() == "quest" || c.getType() == "event"){
                storyDeck.add(c);
            }
            else{
                advDeck.add(c);
            }
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

    }

    private String GetPlayedCardName(String message,PlayerInfo player){
        player.setTurn(true);
        SocketMessagingController.playTurn(player,message,1);
        while(playedCard.equals("")){ try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}
        String result = playedCard;
        SanitiseCard();
        player.setTurn(false);
        return result;
    }

    private Boolean GetCont(String message,PlayerInfo player) {
        System.out.println("in GetCont waiting for change");
        player.setTurn(true);
        SocketMessagingController.playTurn(player,message,2);
        while(changed==false){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean result = cont;
        SanitiseCont();
        player.setTurn(false);
        System.out.println("in GetCont changed occurred");
        return result;
    }

    private void SanitiseCard(){
        playedCard="";
    }

    private void SanitiseCont(){
        cont=false;
        changed=false;
    }
}

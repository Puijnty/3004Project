package project.TimYuyaoRyan.src.models;

import project.TimYuyaoRyan.src.controllers.SocketMessagingController;

public class PlayerInfo {

    public PlayerInfo(int id,boolean turn){
        this.id = id;
        this.turn=turn;
    }

    private int id;


    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    private boolean turn;
    private boolean amour = false;
    private int shields = 0;

    public boolean isTurn(){return turn;}

    public String getHand() {
        return hand.toString();
      }

    public int getId(){
        return id;
    }

    CardDeck hand = new CardDeck();

    public Card remove(String s) {
        Card c = hand.remove(s);
        SocketMessagingController.sendHand(this);
        return c;
    }

    public void give(Card c){
        hand.add(c);
        SocketMessagingController.sendHand(this);
    }

    public int countQuestComponents(){
        int count = 0;
        boolean testBool = false;
        for (int i = 0; i < hand.data.size(); i++) {
            if(hand.data.get(i).getType() == "foe"){
                count++;
            }
            else if(hand.data.get(i).getType() == "test" && testBool == false){
                testBool = true;
                count++;
            }
        }
        return count;
    }

    public int countWeapons(){
        int count = 0;
        for (int i = 0; i < hand.data.size(); i++) {
            if(hand.data.get(i).getType() == "weapon"){
                count++;
            }
        }
        return count;
    }

    public int countMaxBid(){
        //Every card can be discarded for at least 1 big point
        int bidTotal = hand.data.size() + countFreeBid();

        return bidTotal;
    }

    public int countFreeBid(){
        int freeBids = 0;
        if(amour){
            freeBids++;
        }
        //Free bids to consider once allies are in:
        //Arthur = 2 bids
        //Queen guinevere = 3 bids
        //Queen Iseult = 2 bids (or 4 when tristan is in play)
        //Pellinore is 4 bids but only on the questing beast quest
        return freeBids;
    }

    public void activateAmour(){
        amour = true;
    }

    public void disableAmour(){
        amour = false;
    }

    public boolean getAmour(){
        return amour;
    }

    public Card getCard(String name){
        return hand.get(name);
    }

    public int countBattle() {
        int count = 0;
        for (int i = 0; i < hand.data.size(); i++) {
            if(hand.data.get(i).getType() == "weapon" || hand.data.get(i).getType() == "ally" || hand.data.get(i).getType() == "amour"){
                count++;
            }
        }
        return count;
    }

    public void award(int i) {
        shields += i;
    }

    public int getShields() {
        return shields;
    }
}

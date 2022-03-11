package project.TimYuyaoRyan.src.models;

public class PlayerInfo {

    public PlayerInfo(int id,boolean turn){
        this.id = id;
        this.turn=turn;
    }

    private int id;
    private boolean turn;

    public boolean isTurn(){return turn;}

    public int getId(){
        return id;
    }

    CardDeck hand = new CardDeck();

    public Card remove(String s) {
        return hand.remove(s);
    }

    public void give(Card c){
        hand.add(c);
    }
}

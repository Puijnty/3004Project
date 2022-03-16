package project.TimYuyaoRyan.src.models;

public class PlayerInfo {

    public PlayerInfo(int id,boolean turn){
        this.id = id;
        this.turn=turn;
    }

    private int id;
    private boolean turn;
    private boolean amour = false;
    private int shields = 0;

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
}

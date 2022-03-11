package project.TimYuyaoRyan.src.models;
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    ArrayList<Card> data;

    public void add(Card c){
        //Adds a new card to the deck
        data.add(c);
    }

    public Card remove(String cardName){
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).title == cardName){
                Card returnedCard = data.get(i);
                data.remove(i);
                return returnedCard;
            }
        }
        return null;
    }

    public Card draw(){
        //Draws a random card and removes it from the deck
        Collections.shuffle(data);
        Card c = data.get(0);
        data.remove(0);
        return c;
    }
}

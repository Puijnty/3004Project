package project.TimYuyaoRyan;
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    ArrayList<Card> data;

    public void add(Card c){
        //Adds a new card to the deck
        data.add(c);
    }

    public Card draw(){
        //Draws a random card and removes it from the deck
        Collections.shuffle(data);
        Card c = data.get(0);
        data.remove(0);
        return c;
    }
}

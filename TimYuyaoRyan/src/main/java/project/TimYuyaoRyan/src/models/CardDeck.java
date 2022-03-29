package project.TimYuyaoRyan.src.models;
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    ArrayList<Card> data = new ArrayList<Card>();

    public void add(Card c) {
        //Adds a new card to the deck
        data.add(c);
    }

    public Card remove(String cardName) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).title == cardName) {
                Card returnedCard = data.get(i);
                data.remove(i);
                return returnedCard;
            }
        }
        return null;
    }

    public Card draw() {
        //Draws a random card and removes it from the deck
        Collections.shuffle(data);
        Card c = data.get(0);
        //System.out.println(c.title);
        data.remove(0);
        return c;
    }

    public Card get(String s) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getTitle() == s) {
                return data.get(i);
            }
        }
        System.out.println("Returning a null value from a deck. There is an error in the name of a card send between server and client.");
        System.out.println("The specific name in question is: " + s);
        return null;
    }

    public Card next() {
        return data.get(0);
    }

    public int getSize() {
        return data.size();
    }

    @Override
    public String toString() {
        String result = "{";
        for (int i = 0; i < data.size(); i++) {
            result = result + "\"c" + i + "\":\"" + data.get(i).title + "\",";
        }
        result = result.substring(0, result.length() - 1) + "}";
        return result;
    }
}
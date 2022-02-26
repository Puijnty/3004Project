package project.TimYuyaoRyan;

public class Card {
    TitleGenerator tg = new TitleGenerator(); //The title of the card
    EffectGenerator eg = new EffectGenerator(); //Creates the effects that the card should have when played
    SummaryGenerator sg = new SummaryGenerator(); //Short summary for viewing when in discard pile
    String type;

    public void genEffect(){
        //To be used when played, prints the card name in use and starts the effect
        tg.create(this);
        eg.create(this);
    }

    public String getType(){
        //Used to determine if this type of card can be played in the current scenario
        return type;
    }

    public String getSummary(){
        //Prints a short summary of the card and what it does
        String str = sg.print();
        return str;
    }
}

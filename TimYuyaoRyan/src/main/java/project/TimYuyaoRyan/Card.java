package project.TimYuyaoRyan;

public class Card {
    EffectGenerator eg = new EffectGenerator(); //Creates the effects that the card should have when played
    SummaryGenerator sg = new SummaryGenerator(); //Short summary for viewing when in discard pile
    String cardType;
    String title;
    String effectType;
    String conditionalType;
    int effectPotency;
    int conditionalPotency;
    boolean conditionalCard = false;
    String conditionalText;


    public void genSummary(){
        //Sets up card summary using generator
        sg.create(this);
    }

    public int invoke(gamestate){
        //Will become the method through which the system determines the card effect
        if(conditionalCard){
            if(eg.getCondition(gamestate)) {
                //Must become much more complex
                return conditionalPotency;
            }
        }
        return effectPotency;
    }

    public String getType(){
        //Used to determine if this type of card can be played in the current scenario
        return cardType;
    }

    public String getSummary(){
        //Prints a short summary of the card and what it does
        String str = sg.print();
        return str;
    }
}

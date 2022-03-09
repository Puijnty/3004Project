package project.TimYuyaoRyan.src.models;

public class EffectGenerator {
    String condition; //Should be able to be used later to determine if a card is powered up. Maybe a method?
    public void create(Card card, String type, int potency, boolean conditional, String conditionalType, int conditionalPotency, String c) {
        card.effectType = type;
        card.effectPotency = potency;
        card.conditionalCard = conditional;
        card.conditionalType = conditionalType;
        card.conditionalPotency = conditionalPotency;
        condition = c;
        card.conditionalText = c;
    }
    /*
    public boolean getCondition(gamestate){
        //Checks if the condition to change potency is true and returns that result
        return false;
    }

     */
}



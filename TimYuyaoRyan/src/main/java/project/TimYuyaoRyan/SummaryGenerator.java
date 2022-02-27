package project.TimYuyaoRyan;

public class SummaryGenerator {
    String summary;

    void create(Card c){
        if(c.cardType == "event" || c.cardType == "test"){
            summary = c.title + " - " + c.conditionalText;
        }
        else {
            summary = c.title + " - " + c.effectType + " " + c.effectPotency;
            if (c.conditionalCard) {
                summary += "\t" + c.conditionalType + " " + c.conditionalPotency + "\t" + c.conditionalText;
            }
        }
        return;
    }

    public String print() {
        return summary;
    }
}

package project.TimYuyaoRyan;

public class AdventureCardGenerator implements CardGenerator {
    @Override
    public Card publish(String name, String effect) {
        Card card = null;
        if(effect == "foe"){
            card = new FoeCard();
        }
        else if(effect == "weapon"){
            card = new WeaponCard();
        }
        else if(effect == "ally"){
            card = new AllyCard();
        }
        else if(effect == "amour"){
            card = new AmourCard();
        }
        else if(effect == "test"){
            card = new TestCard("Test of " + name);
        }
        //Card.genValues();
        return card;
    }
}

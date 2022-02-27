package project.TimYuyaoRyan.src.models;

public class EventCard extends Card {
    //Event cards do not work as well with the standard format and will require custom events for when they are played in the game.
    public EventCard(String name) {
        cardType = "event";
        title = name;
        if(name == "Chivalrous Deed") {
            eg.create(this, "event", 0, false, "N/A", 0, "Player(s) with both the lowest rank and least amount of shields, receives 3 shields.");
        }
        else if(name == "Pos") {
            eg.create(this, "event", 0, false, "N/A", 0, "All players except the player drawing this card lose 1 shield.");
        }
        else if(name == "Plague") {
            eg.create(this, "event", 0, false, "N/A", 0, "Drawer loses 2 shields if possible.");
        }
        else if(name == "King's Recognition") {
            eg.create(this, "event", 0, false, "N/A", 0, "The next player(s) to complete a quest will receive 2 extra shields.");
        }
        else if(name == "Queen's Favor") {
            eg.create(this, "event", 0, false, "N/A", 0, "The lowest ranked player(s) immediately receive 2 Adventure Cards.");
        }
        else if(name == "Court Called to Camelot") {
            eg.create(this, "event", 0, false, "N/A", 0, "All Allies in play must be discarded.");
        }
        else if(name == "King's Call to Arms") {
            eg.create(this, "event", 0, false, "N/A", 0, "The highest ranked player(s) must place 1 weapon in the discard pile. If unable to do so, 2 Foe cards must be discarded.");
        }
        else if(name == "Prosperity Throughout the Realm") {
            eg.create(this, "event", 0, false, "N/A", 0, "All players may immediately draw 2 adventure cards.");
        }
    }
}

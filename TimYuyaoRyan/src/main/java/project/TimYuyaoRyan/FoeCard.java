package project.TimYuyaoRyan;

public class FoeCard extends Card {
    FoeCard(String name){
        cardType = "foe";
        title = name;
        if(name == "Robber Knight") {
            eg.create(this, "combat", 15, false, "N/A", 0, "N/A");
        }
        else if(name == "Saxons") {
            eg.create(this, "combat", 10, true, "combat", 20, "Foe matches quest");
        }
        else if(name == "Boar") {
            eg.create(this, "combat", 5, true, "combat", 15, "Foe matches quest");
        }
        else if(name == "Thieves") {
            eg.create(this, "combat", 5, false, "N/A", 0, "N/A");
        }
        else if(name == "Green Knight") {
            eg.create(this, "combat", 25, true, "combat", 40, "Foe matches quest");
        }
        else if(name == "Black Knight") {
            eg.create(this, "combat", 25, true, "combat", 35, "Foe matches quest");
        }
        else if(name == "Evil Knight") {
            eg.create(this, "combat", 20, true, "combat", 30, "Foe matches quest");
        }
        else if(name == "Saxon Knight") {
            eg.create(this, "combat", 15, true, "combat", 25, "Foe matches quest");
        }
        else if(name == "Dragon") {
            eg.create(this, "combat", 50, true, "combat", 70, "Foe matches quest");
        }
        else if(name == "Giant") {
            eg.create(this, "combat", 40, false, "N/A", 0, "N/A");
        }
        else if(name == "Mordred") {
            eg.create(this, "combat", 30, false, "N/A", 0, "N/A");
        }
    }
}

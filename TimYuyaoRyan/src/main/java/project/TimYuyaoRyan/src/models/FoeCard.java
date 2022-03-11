package project.TimYuyaoRyan.src.models;

public class FoeCard extends Card {
    FoeCard(String name){
        cardType = "foe";
        title = name;
        if(name == "enemy_robber") {
            eg.create(this, "combat", 15, false, "N/A", 0, "N/A");
        }
        else if(name == "enemy_saxons") {
            eg.create(this, "combat", 10, true, "combat", 20, "Foe matches quest");
        }
        else if(name == "enemy_boar") {
            eg.create(this, "combat", 5, true, "combat", 15, "Foe matches quest");
        }
        else if(name == "enemy_thieves") {
            eg.create(this, "combat", 5, false, "N/A", 0, "N/A");
        }
        else if(name == "enemy_green") {
            eg.create(this, "combat", 25, true, "combat", 40, "Foe matches quest");
        }
        else if(name == "enemy_black") {
            eg.create(this, "combat", 25, true, "combat", 35, "Foe matches quest");
        }
        else if(name == "enemy_evil") {
            eg.create(this, "combat", 20, true, "combat", 30, "Foe matches quest");
        }
        else if(name == "enemy_saxKnight") {
            eg.create(this, "combat", 15, true, "combat", 25, "Foe matches quest");
        }
        else if(name == "enemy_dragon") {
            eg.create(this, "combat", 50, true, "combat", 70, "Foe matches quest");
        }
        else if(name == "enemy_giant") {
            eg.create(this, "combat", 40, false, "N/A", 0, "N/A");
        }
        else if(name == "enemy_mordred") {
            eg.create(this, "combat", 30, false, "N/A", 0, "N/A");
        }
    }
}

package project.TimYuyaoRyan.src.models;

public class QuestCard extends Card {
    public QuestCard(String name) {
        cardType = "quest";
        title = name;
        if(name == "Journey Through the Enchanted Forest") {
            eg.create(this, "stages", 3, true, "stages", 3, "Foe: Evil Knight");
        }
        else if(name == "Vanquish King Arthur's Enemies") {
            eg.create(this, "stages", 3, false, "stages", 3, "N/A");
        }
        else if(name == "Repel the Saxon Raiders") {
            eg.create(this, "stages", 2, true, "stages", 2, "Foe: All Saxons");
        }
        else if(name == "Boar Hunt") {
            eg.create(this, "stages", 2, true, "stages", 2, "Foe: Boar");
        }
        else if(name == "Search for the Questing Beast") {
            eg.create(this, "stages", 4, false, "stages", 4, "N/A");
        }
        else if(name == "Defend the Queen's Honor") {
            eg.create(this, "stages", 4, true, "stages", 4, "Foe: All");
        }
        else if(name == "Slay the Dragon") {
            eg.create(this, "stages", 3, true, "stages", 3, "Foe: Dragon");
        }
        else if(name == "Rescue the Fair Maiden") {
            eg.create(this, "stages", 3, true, "stages", 3, "Foe: Black Knight");
        }
        else if(name == "Search for the Holy Grail") {
            eg.create(this, "stages", 5, true, "stages", 5, "Foe: All");
        }
        else if(name == "Test of the Green Knight") {
            eg.create(this, "stages", 4, true, "stages", 4, "Foe: Green Knight");
        }
    }
}

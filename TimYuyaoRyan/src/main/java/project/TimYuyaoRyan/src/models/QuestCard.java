package project.TimYuyaoRyan.src.models;

public class QuestCard extends Card {
    public QuestCard(String name) {
        cardType = "quest";
        title = name;
        if(name == "quest_forest") {
            eg.create(this, "stages", 3, true, "stages", 3, "Foe: Evil Knight");
        }
        else if(name == "quest_vanquish") {
            eg.create(this, "stages", 3, false, "stages", 3, "N/A");
        }
        else if(name == "quest_repel") {
            eg.create(this, "stages", 2, true, "stages", 2, "Foe: All Saxons");
        }
        else if(name == "quest_hunt") {
            eg.create(this, "stages", 2, true, "stages", 2, "Foe: Boar");
        }
        else if(name == "quest_quest") {
            eg.create(this, "stages", 4, false, "stages", 4, "N/A");
        }
        else if(name == "quest_defend") {
            eg.create(this, "stages", 4, true, "stages", 4, "Foe: All");
        }
        else if(name == "quest_slay") {
            eg.create(this, "stages", 3, true, "stages", 3, "Foe: Dragon");
        }
        else if(name == "quest_rescue") {
            eg.create(this, "stages", 3, true, "stages", 3, "Foe: Black Knight");
        }
        else if(name == "quest_search") {
            eg.create(this, "stages", 5, true, "stages", 5, "Foe: All");
        }
        else if(name == "quest_test") {
            eg.create(this, "stages", 4, true, "stages", 4, "Foe: Green Knight");
        }
    }
}

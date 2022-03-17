package project.TimYuyaoRyan.src.models;

public class QuestCard extends Card {
    public QuestCard(String name) {
        cardType = "quest";
        title = name;
        if(name == "quest_forest") {
            eg.create(this, "stages", 3, true, "stages", 3, "enemy_evil");
        }
        else if(name == "quest_vanquish") {
            eg.create(this, "stages", 3, false, "stages", 3, "N/A");
        }
        else if(name == "quest_repel") {
            eg.create(this, "stages", 2, true, "stages", 2, "enemy_saxons");
        }
        else if(name == "quest_hunt") {
            eg.create(this, "stages", 2, true, "stages", 2, "enemy_boar");
        }
        else if(name == "quest_quest") {
            eg.create(this, "stages", 4, false, "stages", 4, "N/A");
        }
        else if(name == "quest_defend") {
            eg.create(this, "stages", 4, true, "stages", 4, "all");
        }
        else if(name == "quest_slay") {
            eg.create(this, "stages", 3, true, "stages", 3, "enemy_dragon");
        }
        else if(name == "quest_rescue") {
            eg.create(this, "stages", 3, true, "stages", 3, "enemy_black");
        }
        else if(name == "quest_search") {
            eg.create(this, "stages", 5, true, "stages", 5, "all");
        }
        else if(name == "quest_test") {
            eg.create(this, "stages", 4, true, "stages", 4, "enemy_green");
        }
    }
}

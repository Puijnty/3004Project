package project.TimYuyaoRyan.src.models;

public class RankCard extends Card {
    public RankCard(String name){
        cardType = "rank";
        title = name;
        if(name == "rank_squire") {
            eg.create(this, "combat", 5, false, "combat", 0, "N/A");
        }
        else if(name == "rank_knight") {
            eg.create(this, "combat", 10, false, "combat", 0, "N/A");
        }
        else if(name == "rank_champ") {
            eg.create(this, "combat", 20, false, "combat", 0, "N/A");
        }
    }
}

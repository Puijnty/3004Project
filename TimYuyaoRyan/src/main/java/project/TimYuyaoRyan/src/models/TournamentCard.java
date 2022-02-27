package project.TimYuyaoRyan.src.models;

public class TournamentCard extends Card {
    public TournamentCard(String name) {
        cardType = "tournament";
        title = name;
        if(name == "Tournament at Camelot") {
            eg.create(this, "bonus shields", 3, false, "N/A", 0, "N/A");
        }
        else if(name == "Tournament at Orkney") {
            eg.create(this, "bonus shields", 2, false, "N/A", 0, "N/A");
        }
        else if(name == "Tournament at Tintagel") {
            eg.create(this, "bonus shields", 1, false, "N/A", 0, "N/A");
        }
        else if(name == "Tournament at York") {
            eg.create(this, "bonus shields", 0, false, "N/A", 0, "N/A");
        }
    }
}

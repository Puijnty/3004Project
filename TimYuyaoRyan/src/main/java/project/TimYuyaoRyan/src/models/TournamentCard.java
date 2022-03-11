package project.TimYuyaoRyan.src.models;

public class TournamentCard extends Card {
    public TournamentCard(String name) {
        cardType = "tournament";
        title = name;
        if(name == "tournament_camelot") {
            eg.create(this, "bonus shields", 3, false, "N/A", 0, "N/A");
        }
        else if(name == "tournament_orkney") {
            eg.create(this, "bonus shields", 2, false, "N/A", 0, "N/A");
        }
        else if(name == "tournament_tintagel") {
            eg.create(this, "bonus shields", 1, false, "N/A", 0, "N/A");
        }
        else if(name == "tournament_york") {
            eg.create(this, "bonus shields", 0, false, "N/A", 0, "N/A");
        }
    }
}

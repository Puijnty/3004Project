package project.TimYuyaoRyan.src.models;

public class AllyCard extends Card {
    public AllyCard(String name) {
        cardType = "ally";
        title = name;
        if(name == "ally_lancelot") {
            eg.create(this, "combat", 15, true, "combat", 25, "Quest = Quest to Defend the Queen's Honor");
        }
        else if(name == "ally_gawain") {
            eg.create(this, "combat", 10, true, "combat", 20, "Quest = Test of the Green Knight");
        }
        else if(name == "ally_pellinore") {
            eg.create(this, "combat", 10, true, "bid", 4, "Quest = Search for the Questing Beast");
        }
        else if(name == "ally_percival") {
            eg.create(this, "combat", 15, true, "combat", 20, "Quest = Search for the Holy Grail");
        }
        else if(name == "ally_tristan") {
            eg.create(this, "combat", 10, true, "combat", 20, "In play = Queen Iseult");
        }
        else if(name == "ally_arthur") {
            eg.create(this, "combat", 10, true, "bid", 2, "Always = Active");
        }
        else if(name == "ally_guinevere") {
            eg.create(this, "bid", 3, false, "N/A", 0, "N/A");
        }
        else if(name == "ally_merlin") {
            eg.create(this, "recon", 0, false, "N/A", 0, "N/A");
        }
        else if(name == "ally_iseult") {
            eg.create(this, "bid", 2, true, "bid", 4, "In play = Sir Tristan");
        }
        else if(name == "ally_galahad") {
            eg.create(this, "combat", 15, false, "N/A", 0, "N/A");
        }
    }
}

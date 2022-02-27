package project.TimYuyaoRyan.src.models;

public class AllyCard extends Card {
    public AllyCard(String name) {
        cardType = "ally";
        title = name;
        if(name == "Sir Lancelot") {
            eg.create(this, "combat", 15, true, "combat", 25, "Quest = Quest to Defend the Queen's Honor");
        }
        else if(name == "Sir Gawain") {
            eg.create(this, "combat", 10, true, "combat", 20, "Quest = Test of the Green Knight");
        }
        else if(name == "King Pellinore") {
            eg.create(this, "combat", 10, true, "bid", 4, "Quest = Search for the Questing Beast");
        }
        else if(name == "Sir Percival") {
            eg.create(this, "combat", 15, true, "combat", 20, "Quest = Search for the Holy Grail");
        }
        else if(name == "Sir Tristan") {
            eg.create(this, "combat", 10, true, "combat", 20, "In play = Queen Iseult");
        }
        else if(name == "King Arthur") {
            eg.create(this, "combat", 10, true, "bid", 2, "Always = Active");
        }
        else if(name == "Queen Guinevere") {
            eg.create(this, "bid", 3, false, "N/A", 0, "N/A");
        }
        else if(name == "Merlin") {
            eg.create(this, "recon", 0, false, "N/A", 0, "N/A");
        }
        else if(name == "Queen Iseult") {
            eg.create(this, "bid", 2, true, "bid", 4, "In play = Sir Tristan");
        }
        else if(name == "Sir Galahad") {
            eg.create(this, "combat", 15, false, "N/A", 0, "N/A");
        }
    }
}

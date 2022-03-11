package project.TimYuyaoRyan.src.models;

public class AmourCard extends Card {
    public AmourCard(String name) {
        cardType = "amour";
        title = name;
        if(name == "ally_amour") {
            eg.create(this, "combat", 10, true, "bid", 1, "Always = Active");
        }
    }
}

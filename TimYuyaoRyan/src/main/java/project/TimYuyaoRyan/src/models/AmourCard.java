package project.TimYuyaoRyan.src.models;

public class AmourCard extends Card {
    public AmourCard(String name) {
        cardType = "amour";
        title = name;
        if(name == "Amour") {
            eg.create(this, "combat", 10, true, "bid", 1, "Always = Active");
        }
    }
}

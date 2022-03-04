package project.TimYuyaoRyan;

public class WeaponCard extends Card {
    public WeaponCard(String name) {
        cardType = "weapon";
        title = name;
        if(name == "Horse") {
            eg.create(this, "combat", 10, false, "N/A", 0, "N/A");
        }
        else if(name == "Sword") {
            eg.create(this, "combat", 10, false, "N/A", 0, "N/A");
        }
        else if(name == "Dagger") {
            eg.create(this, "combat", 5, false, "N/A", 0, "N/A");
        }
        else if(name == "Excalibur") {
            eg.create(this, "combat", 30, false, "N/A", 0, "N/A");
        }
        else if(name == "Lance") {
            eg.create(this, "combat", 20, false, "N/A", 0, "N/A");
        }
        else if(name == "Battle-ax") {
            eg.create(this, "combat", 15, false, "N/A", 0, "N/A");
        }
    }
}

package project.TimYuyaoRyan.src.models;

public class WeaponCard extends Card {
    public WeaponCard(String name) {
        cardType = "weapon";
        title = name;
        if(name == "weapon_horse") {
            eg.create(this, "combat", 10, false, "N/A", 0, "N/A");
        }
        else if(name == "weapon_sword") {
            eg.create(this, "combat", 10, false, "N/A", 0, "N/A");
        }
        else if(name == "weapon_dagger") {
            eg.create(this, "combat", 5, false, "N/A", 0, "N/A");
        }
        else if(name == "weapon_excalibur") {
            eg.create(this, "combat", 30, false, "N/A", 0, "N/A");
        }
        else if(name == "weapon_lance") {
            eg.create(this, "combat", 20, false, "N/A", 0, "N/A");
        }
        else if(name == "weapon_battleax") {
            eg.create(this, "combat", 15, false, "N/A", 0, "N/A");
        }
    }
}

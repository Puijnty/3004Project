package project.TimYuyaoRyan.src.models;

public class TestCard extends Card {
    public TestCard(String name) {
        cardType = "test";
        title = name;
        if(name == "test_quest") {
            eg.create(this, "test", 0, true, "test", 4, "Minimum 4 bid on the Search for the Questing Beast Quest");
        }
        else if(name == "test_temptation") {
            eg.create(this, "test", 0, false, "N/A", 0, "Standard test.");
        }
        else if(name == "test_valor") {
            eg.create(this, "test", 0, false, "N/A", 0, "Standard test.");
        }
        else if(name == "test_morgan") {
            eg.create(this, "test", 3, false, "N/A", 0, "Minimum 3 bid.");
        }
    }
}

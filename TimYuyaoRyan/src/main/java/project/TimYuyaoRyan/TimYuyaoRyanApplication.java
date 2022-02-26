package project.TimYuyaoRyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimYuyaoRyanApplication {
	public static void main(String[] args) {
		SpringApplication.run(TimYuyaoRyanApplication.class, args);
		CreateDecks();
	}

	private static void CreateDecks() {
		//RANK CARDS are not included in any decks, and should be generated dynamically when needed
		CardGenerator advGen = new AdventureCardGenerator();
		CardGenerator storyGen = new StoryCardGenerator();
		CardDeck advDeck = new CardDeck();
		CardDeck storyDeck = new CardDeck();
		CardDeck discardDeck = new CardDeck();
		advDeck.add(advGen.publish("Robber Knight", "foe"));
		advDeck.add(advGen.publish("Saxons", "foe"));
		advDeck.add(advGen.publish("Boar", "foe"));
		advDeck.add(advGen.publish("Thieves", "foe"));
		advDeck.add(advGen.publish("Green Knight", "foe"));
		advDeck.add(advGen.publish("Evil Knight", "foe"));
		advDeck.add(advGen.publish("Saxon Knight", "foe"));
		advDeck.add(advGen.publish("Dragon", "foe"));
		advDeck.add(advGen.publish("Giant", "foe"));
		advDeck.add(advGen.publish("Mordred", "foe"));

		advDeck.add(advGen.publish("Horse", "weapon"));
		advDeck.add(advGen.publish("Sword", "weapon"));
		advDeck.add(advGen.publish("Dagger", "weapon"));
		advDeck.add(advGen.publish("Excalibur", "weapon"));
		advDeck.add(advGen.publish("Lance", "weapon"));
		advDeck.add(advGen.publish("Battle-ax", "weapon"));

		advDeck.add(advGen.publish("Sir Gawain", "ally"));
		advDeck.add(advGen.publish("King Pellinore", "ally"));
		advDeck.add(advGen.publish("Sir Percival", "ally"));
		advDeck.add(advGen.publish("Sir Tristan", "ally"));
		advDeck.add(advGen.publish("King Arthur", "ally"));
		advDeck.add(advGen.publish("Queen Guinevere", "ally"));
		advDeck.add(advGen.publish("Merlin", "ally"));
		advDeck.add(advGen.publish("Queen Iseult", "ally"));
		advDeck.add(advGen.publish("Sir Lancelot", "ally"));
		advDeck.add(advGen.publish("Sir Galahad", "ally"));

		advDeck.add(advGen.publish("Amour", "amour"));

		advDeck.add(advGen.publish("the Questing Beast", "test"));
		advDeck.add(advGen.publish("Temptation", "test"));
		advDeck.add(advGen.publish("Valor", "test"));
		advDeck.add(advGen.publish("Morgan Le Fey", "test"));

		storyDeck.add(storyGen.publish("Journey Through the Enchanted Forest", "quest"));
		storyDeck.add(storyGen.publish("Vanquish King Arthur's Enemies", "quest"));
		storyDeck.add(storyGen.publish("Repel the Saxon Raiders", "quest"));
		storyDeck.add(storyGen.publish("Boar Hunt", "quest"));
		storyDeck.add(storyGen.publish("Search for the Questing Beast", "quest"));
		storyDeck.add(storyGen.publish("Defend the Queen's Honor", "quest"));
		storyDeck.add(storyGen.publish("Slay the Dragon", "quest"));
		storyDeck.add(storyGen.publish("Rescue the Fair Maiden", "quest"));
		storyDeck.add(storyGen.publish("Search for the Holy Grail", "quest"));
		storyDeck.add(storyGen.publish("Test of the Green Knight", "quest"));

		storyDeck.add(storyGen.publish("Camelot", "tournament"));
		storyDeck.add(storyGen.publish("Orkney", "tournament"));
		storyDeck.add(storyGen.publish("Tintagel", "tournament"));
		storyDeck.add(storyGen.publish("York", "tournament"));

		storyDeck.add(storyGen.publish("Chivalrous Deed", "event"));
		storyDeck.add(storyGen.publish("Pox", "event"));
		storyDeck.add(storyGen.publish("Plague", "event"));
		storyDeck.add(storyGen.publish("King's Recognition", "event"));
		storyDeck.add(storyGen.publish("Queen's Favor", "event"));
		storyDeck.add(storyGen.publish("Court Called to Camelot", "event"));
		storyDeck.add(storyGen.publish("King's Call to Arms", "event"));
		storyDeck.add(storyGen.publish("Prosperity Throughout the Realm", "event"));
	}

}

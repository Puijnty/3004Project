package project.TimYuyaoRyan;

public class StoryCardGenerator implements CardGenerator {
    @Override
    public Card publish(String name, String effect) {
        Card card = null;
        if(effect == "quest"){
            card = new QuestCard(name);
        }
        else if(effect == "tournament"){
            card = new TournamentCard("Tournament at " + name);
        }
        else if(effect == "event"){
            card = new EventCard(name);
        }
        //card.genValues();
        return card;
    }
}

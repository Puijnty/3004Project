package project.TimYuyaoRyan.src.models;

public class PlayerId {
    private int id;
    private static int nextId=1;
    public PlayerId(){
        id=nextId;
        nextId+=1;
    }

    public int getId() {
        return id;
    }
    public int getNextId(){
        return nextId;
    }
}

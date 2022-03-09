package project.TimYuyaoRyan.src.models;

public class PlayerInfo {
    public PlayerInfo(int id,String status){
        this.id = id;
        this.status=status;
    }
    private int id;
    private String status;
    public String getStatus(){return status;}
    public int getId(){
        return id;
    }

}

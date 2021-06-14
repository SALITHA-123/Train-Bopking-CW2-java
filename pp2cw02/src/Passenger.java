import java.io.Serializable;
public class Passenger implements Serializable {
    private String name;
    private int secondsInQueue;
    private int seats;

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return  name ;
    }

    public void setSecondsInQueue(int secondsInQueue) {
        this.secondsInQueue = this.secondsInQueue+secondsInQueue;
    }

    public int getSecondsInQueue(){
        return secondsInQueue;
    }

    public void setSeat(int seat){
        this.seats = seat;
    }

    public int getSeat(){
        return seats;
    }

}

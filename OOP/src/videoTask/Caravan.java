package videoTask;

public class Caravan implements Habitable , Movable{
    int location;
    int max;
    public void move(int distance){
        location = location + distance;
    }

    public boolean canFit(int inhabitants){
        return max <= inhabitants;
    }
}

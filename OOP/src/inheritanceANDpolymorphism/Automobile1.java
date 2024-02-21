package inheritanceANDpolymorphism;

public class Automobile1 extends Vehicle{
    private int numSeats;
    private boolean isSUV;


    public Automobile1(String make, String model, int year, int numWheels , int numSeats , boolean isSUV) {
        super(make, model, year, numWheels);
        this.numSeats = numSeats;
        this.isSUV = isSUV;
    }

    public int getNumSeats() {
        return this.numSeats;
    }
    public boolean isSUV() {
        return this.isSUV;
    }
}

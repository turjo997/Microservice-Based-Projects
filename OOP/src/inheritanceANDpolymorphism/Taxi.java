package inheritanceANDpolymorphism;

public class Taxi extends Automobile {
    private String taxiID;
    private double fareTotal;
    private int numFares;

    public Taxi(String make, String model, int year, int numSeats, boolean isSUV , String taxiID) {
        super(make, model, year, numSeats, isSUV);
        this.taxiID = taxiID;
    }


//    public void setTaxiID(String taxiID){
//        this.taxiID = taxiID;
//    }
    public String getID(){
        return this.taxiID;
    }

    public void addFare(double fare){
       if(fare < 0){
           throw new IllegalArgumentException();
       }
       this.fareTotal += fare;
       this.numFares++;
    }

    public String toString(){
        return "Taxi (id = " + this.taxiID + ")";
    }

    public static void main(String[] args) {
        Taxi t = new Taxi("Austrilia" , "#12211" , 2023 , 10 , true , "43");

        t.setMileage(2300);
        t.setPlateNumber("4211");
        //t.setTaxiID("43");
        t.addFare(122);

        System.out.println(t.getID());
    }


}

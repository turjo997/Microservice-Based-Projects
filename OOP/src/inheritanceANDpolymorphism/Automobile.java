package inheritanceANDpolymorphism;

public class Automobile {
    private String make;
    private String model;
    private int year;

    private int mileage;
    private String plateNumber;
    private int numSeats;
    private boolean isSUV;


    public Automobile(String make, String model, int year,int numSeats, boolean isSUV)
    {
        this.make = make;
        this.model = model;
        if (year < 1900)
        {
            throw new
                    IllegalArgumentException();
        }

        this.year = year;

        this.numSeats = numSeats;
        this.isSUV = isSUV;
        this.mileage = 0;
        this.plateNumber = "unknown";
    }
    public Automobile(String make, String model, int year) {
        this(make, model, year, 5, false);
    }


    public void setMileage(int newMileage)
    {
        if (newMileage < this.mileage)
        {
            throw new IllegalArgumentException();

        }

        this.mileage = newMileage;

    }
    public void setPlateNumber(String plate)
    {
        this.plateNumber = plate;
    }
    public String toString() {
        String str = this.make + " " + this.model;
        str += "( " + this.numSeats + " seats)";

        return str;
    }


    public static void main(String[] args) {
        Automobile ob = new Automobile("Germany" , "#12351" , 2022);
        ob.setMileage(300);
        ob.setPlateNumber("12311");

        System.out.println(ob.toString());
    }

}

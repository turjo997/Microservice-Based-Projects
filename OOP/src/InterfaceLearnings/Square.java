package InterfaceLearnings;

public class Square implements Moveable , Drawable{

    public void move(){
        System.out.println("I am move method");
    }

   public void draw(){
       System.out.println("I am draw method");
    }
}

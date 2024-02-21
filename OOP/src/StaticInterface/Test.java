package StaticInterface;

public class Test {

    public static void main(String[] args) {
         Drawable d = new Rectangle();
         d.draw();

        System.out.println(Drawable.cube(10));
    }
}

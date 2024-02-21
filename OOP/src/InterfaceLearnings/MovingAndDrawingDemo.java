package InterfaceLearnings;

public class MovingAndDrawingDemo {
    public static void main(String[] args) {
        Square square = new Square();
        square.move();
        square.draw();

        Moveable ob = new Moveable() {
            @Override
            public void move() {

            }

            @Override
            public void draw() {
                System.out.println("I am draw method calling from moveable interface");
            }
        };

        ob.draw();

        Drawable d = new Square();
        d.draw();

        Moveable m = new Square();
        m.draw();
        m.move();

    }
}

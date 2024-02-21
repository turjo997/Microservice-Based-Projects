interface sayable5{
    void say(String msg);   // abstract method
}
@FunctionalInterface
interface Doable extends sayable5{
    // Invalid '@FunctionalInterface' annotation; Doable is not a functional interface
    //void doIt();
}

public class Functional_Interface {

    public static void main(String[] args) {

        Doable ob = new Doable() {
            @Override
            public void say(String msg) {
                System.out.println(msg);
            }
        };

        ob.say("Hello world");

    }
}

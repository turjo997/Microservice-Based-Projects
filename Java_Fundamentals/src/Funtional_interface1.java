interface Doable1{
    default void doIt(){
        System.out.println("Print it now");
    }
}
@FunctionalInterface
interface Sayable3 extends Doable1{
    void say(String msg);   // abstract method
}

class Random implements Sayable3{

    @Override
    public void say(String msg) {
        System.out.println(msg);
    }
}


public class Funtional_interface1 {

    public static void main(String[] args) {
        Sayable3 ob = new Sayable3() {
            @Override
            public void say(String msg) {
                System.out.println(msg);
            }
        };

        ob.say("ki je hbe");

        Random obj = new Random();

        obj.say("Ami pari na kichu");

        obj.doIt();



    }
}

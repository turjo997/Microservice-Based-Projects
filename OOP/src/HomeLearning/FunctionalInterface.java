package HomeLearning;


@java.lang.FunctionalInterface
interface myInterface extends Doable{
    void say(String msg);

    int hashCode();
    String toString();
    boolean equals(Object obj);


    static void print(){
        System.out.println("print something");
    }
}


interface Doable{

    default void doIt(){
        System.out.println("Do it now");
    }
}




public class FunctionalInterface implements myInterface {

    public void say(String msg){
        System.out.println(msg);
    }

    public static void main(String[] args) {

        FunctionalInterface ob = new FunctionalInterface();
        ob.say("Honesty is the best policy");
        ob.doIt();

        myInterface obj = new FunctionalInterface();
        obj.say("Jani na ki hbe");

        myInterface.print();
    }
}

package InterfaceInheritance;

public interface Printable {
    void print();

    default void msg(){
        System.out.println("default method");
    }
}

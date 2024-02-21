package InterfaceInheritance;

public class test implements Showable{
    public void show(){
        System.out.println("I am show method");
    }

    public void print(){
        System.out.println("I am print method");
    }

    public static void main(String[] args) {
        test ob = new test();
        ob.show();
        ob.print();
        ob.msg();
    }
}

package Transports;

public interface Test2 {
 final int a = 20;

 static void display(){
     System.out.println("I am display method");
 }
}

class MyClass implements Test2{
    public static void main(String[] args) {

        Test2.display();

    }
}

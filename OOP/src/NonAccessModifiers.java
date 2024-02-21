
class Gari{
    static int a = 10;
     int b = 20;

    void display(){
        System.out.println(Honda.c);
    }
}

class Honda extends Gari{

    static int c = 20;
    static void print() {
        System.out.println(a);
    }
}

class Motor{

     static void show(){
        System.out.println(Gari.a);
        System.out.println(Honda.a);

    }

    void show2(){
        System.out.println(Gari.a);
        System.out.println(Honda.a);
    }
}




class AA{
    final int age = 100;
}

class BB extends AA{

    void aaPrint(){
        System.out.println(age);
    }

    final void aaPrint1(){
        System.out.println(age);
    }
}
public class NonAccessModifiers {

    public static void main(String[] args) {
        System.out.println(Gari.a);
        System.out.println(Honda.a);
        Honda ob = new Honda();
        ob.print();

        Gari ob1 = new Gari();
        ob1.display();

        BB ob2 = new BB();
        ob2.aaPrint();
        ob2.aaPrint1();
    }
}

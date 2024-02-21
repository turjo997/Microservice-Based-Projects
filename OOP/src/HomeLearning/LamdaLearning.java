package HomeLearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

interface Drawable{
    void draw();

    String say(String name);
}

interface Sayable{
    //String say();

    String say1(String name);

}

interface  Sayable1{
    String say(String str);
}

interface Addable{
    int add(int a , int b);
}

class Product{
    int id;
    String name;
    float price;
    public Product(int id, String name, float price) {
       // super();
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

public class LamdaLearning {
    public static void main(String[] args) {
           int width = 10;

           Drawable d = new Drawable() {
               @Override
               public void draw() {
                   System.out.println("Drawing : " +width);
               }

               public String say(String name){
                   return "Hello , " + name;
               }
           };

           d.draw();
           System.out.println(d.say("Hello Good People"));


//
//           Drawable d2  = ()->{
//               System.out.println("Drawing : " +width);
//           };
//
//           d2.draw();



//           Sayable s = ()->{
//               return "I have nothing to say";
//           };
//        System.out.println(s.say());


        Sayable s1=(name)->{
            return "Hello, "+name;
        };
        System.out.println(s1.say1("Sonoo"));

        Sayable s2 = name->{
            return  "Hello , " + name;
        };

        System.out.println(s1.say1("Honey"));


        Addable ad1 = (a , b)->{
            return a + b;
        };

        System.out.println(ad1.add(10,20));


        Addable ad2=(a,b)->(a+b);
        System.out.println(ad2.add(10,20));

        List<String> l = new ArrayList<>();
        l.add("lob");
        l.add("kush");
        l.add("joy");
        l.add("maa");
        l.add("baba");

        l.forEach((str)-> System.out.println(str));


        Sayable1 s = (str)->{
           String msg = "I would like to say";

           return msg + str;
        };

        System.out.println(s.say(" Time is precious"));

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread is running");
            }
        };



        Thread t1 = new Thread(r1);
        t1.start();


        Runnable r2 = ()->{
            System.out.println("Thread 2 is running");
        };

        Thread t2 = new Thread(r2);

        t2.start();



        List<Goods> list=new ArrayList<Goods>();

        //Adding Products
        list.add(new Goods(1,"HP Laptop",25000f));
        list.add(new Goods(3,"Keyboard",300f));
        list.add(new Goods(2,"Dell Mouse",150f));

        System.out.println("Sorting on the basis of name...");

        Collections.sort(list,(p1, p2)->{
            return p1.price < p2.price ? 1 : -1;
            //return p1.name.compareTo(p2.name);
        });


        for(Goods p:list){
            System.out.println(p.id+" "+p.name+" "+p.price);
        }



        List<Goods> list1=new ArrayList<Goods>();
        list1.add(new Goods(1,"Samsung A5",17000f));
        list1.add(new Goods(3,"Iphone 6S",65000f));
        list1.add(new Goods(2,"Sony Xperia",25000f));
        list1.add(new Goods(4,"Nokia Lumia",15000f));
        list1.add(new Goods(5,"Redmi4 ",26000f));
        list1.add(new Goods(6,"Lenevo Vibe",19000f));


        Stream<Goods> filtered_data = list1.stream().filter(p -> p.price > 20000);

        filtered_data.forEach((Goods p )-> System.out.println(p.id + " " + p.name + " " + p.price));

    }
}

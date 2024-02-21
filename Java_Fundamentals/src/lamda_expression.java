import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Drawable{
     void draw();
}
interface Sayable{
    String say();
}

interface Sayable1{
    String say1(String name);
}

interface Sayable2{
    String say2(String fname , String lname);
}

interface Addable{
    int add(int a,int b);
}

class Product{
    int id;
    String name;
    float price;
    public Product(int id, String name, float price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }
}


public class lamda_expression {

    public static void main(String[] args) {
        double width = 10.5;

        Drawable d = () ->{
               System.out.println(width);
           };

        Sayable s = () -> {
            return "I am saying";
        };

        Sayable1 s1 = (name)->{
            return "Hello " + name;
        };
//        Sayable1 s1 = name->{
//            return "Hello " + name;
//        };



        d.draw();

        System.out.println(s.say());
        System.out.println(s1.say1("Ullash"));


        // Multiple parameters in lambda expression
        Addable ad1=(a,b)->(a+b);
        System.out.println(ad1.add(10,20));

        // Multiple parameters with data type in lambda expression
        Addable ad2=(int a,int b)->(a+b);
        System.out.println(ad2.add(100,200));

        // Lambda expression with return keyword.
        Addable ad3=(int a,int b)->{
            return (a+b);
        };
        System.out.println(ad3.add(100,200));


        List<String> l = new ArrayList<>();

        l.add("A");
        l.add("B");
        l.add("C");
        l.add("D");

        l.forEach(
                (n)-> System.out.print(n)
        );



        List<Product> list = new ArrayList<>();

        list.add(new Product(1,"AC" , 40000));
        list.add(new Product(3,"Iphone 6S",65000f));
        list.add(new Product(2,"Sony Xperia",25000f));
        list.add(new Product(4,"Nokia Lumia",15000f));
        list.add(new Product(5,"Redmi4 ",26000f));
        list.add(new Product(6,"Lenevo Vibe",19000f));
        list.add(new Product(7,"Dell Laptop",30000f));

        Stream<Product> filtered_data = list.stream()
                .filter(p->p.price > 20000);

        filtered_data.forEach(
                (product)-> System.out.println(product.name+": "+product.price)
        );

        System.out.println("-------------------------------");

        List<Float> productPriceList2 =list.stream()
                .filter(p -> p.price > 30000)// filtering data
                .map(p->p.price)        // fetching price
                .collect(Collectors.toList()); // collecting as list
        System.out.println(productPriceList2);

        System.out.println("-------------------------------");


        // This is more compact approach for filtering data
        list.stream()
                .filter(product -> product.price == 30000)
                .forEach(product -> System.out.println(product.name + " " + product.price));


    }
}

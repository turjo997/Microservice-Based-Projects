package HomeLearning;

import java.util.*;

class Container<T extends Number>{

    T val1;

    T val2;

    public Container(){
        // default constructor
    }

    public T getVal1() {
        return val1;
    }

    public void setVal1(T val1) {
        this.val1 = val1;
    }

    public T getVal2() {
        return val2;
    }

    public void setVal2(T val2) {
        this.val2 = val2;
    }

    public void show(){
        System.out.println(val1.getClass().getName());
        System.out.println(val2.getClass().getName());
    }

    public void Demo(ArrayList<? extends T> ar){
    }
}
public class GenericsDemo {

    public static void main(String[] args) {
        List values = new ArrayList<>();

        values.add(12);
        values.add("asas");

        List <Integer>values1 = new ArrayList<>();
        values1.add(12);
       // values1.add("sas"); showing error

        //Container<Object> obj1 = new Container();
        Container<Integer> obj2 = new Container();
        Container<Double> obj3 = new Container();
        Container<Number> obj4 = new Container();
        Container <Number> obj = new Container();


        //obj.val1 = "string";
        obj.setVal1(12);
        obj.setVal2(2121);
        obj.show();


        obj.Demo(new ArrayList<Double>());


        Collection val = new ArrayList();
        val.add(1);
        val.add("ullash");
        val.add(3.3);
        val.add(4.5f);

        for(Object o : val){
            System.out.println(o);
        }

        List<Integer> val1 = new ArrayList<>();

        val1.add(1);
        val1.add(2);
        val1.add(3);
        val1.add(4);

        Collections.sort(val1 , (o1,o2)-> {
            return o1 <o2 ? 1:-1;
        });

        val1.forEach(System.out::println);

        Vector v = new Vector();











    }
}

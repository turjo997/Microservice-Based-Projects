package Ja2EEXM;

import Task.A;

class Tech{
     public void tech(){
         System.out.println("Tech");
     }

}

class ATech{
    Tech a = new Tech(){
        public void Tech(){
            System.out.println("anonymus tech");
        }
    };

    public void doThis(){
        a.tech();
    }
}


class Parent{
    Integer get(){
        return 1;
    }
}

class Child extends Parent{
//     Double get(){
//         return 2.0;
//     }
}


class Outer {
    private int a = 7;
    class Inner{
        public void displayValue(){
            System.out.println("Value of a is : " +a);
        }
    }
}
public class xm1 {

    public static void main(String[] args) {

//        ATech aTech = new ATech();
//        aTech.doThis();

        //System.out.println(new Child().get());

        Outer mo = new Outer();
        Outer.Inner inner = mo.new Inner();
        inner.displayValue();
    }
}

package Ja2EEXM;

class A{
    public void doA(){
        B b = new B();
        b.doB();
        System.out.println("doA");
    }
}

class B{
    public void doB(){
        C c = new C();
        c.doC();
        System.out.println("doB");
    }
}

class C{
    public void doC(){
        if (true){
            throw new NullPointerException();
        }
        System.out.println("doC");
    }
}


public class xm11 {
    public static void main(String[] args) {

        try{
            A a = new A();
            a.doA();
        }catch (Exception ex){
            System.out.println("error");
        }
    }
}

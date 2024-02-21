package Transports;

public interface A {
    void method1();
    void method2();
}

// B now includes method1 and method2
interface B extends A {
    void method3();
}
// the class must implement all method of A and B.
class Test1 implements B {
    public void method1()
    {
        System.out.println("Method 1");
    }
    public void method2()
    {
        System.out.println("Method 2");
    }
    public void method3()
    {
        System.out.println("Method 3");
    }

    public static void main(String[] args) {
        A ob = new Test1();
        ob.method1();
        ob.method2();

        B ob2 = new Test1();
        ob2.method1();
        ob2.method2();
        ob2.method3();
    }
}

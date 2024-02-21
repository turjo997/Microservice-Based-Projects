package Exception;


import java.io.*;
class M1{
    void method()throws IOException{
        System.out.println("device operation performed");
    }
}

public class Test2 {

    public static void main(String[] args) throws IOException {
        M1 m=new M1();
        m.method();

        System.out.println("normal flow...");
    }
}

package Exception;


import java.io.*;
class M2{
    void method()throws IOException{

        throw new IOException("device error");
    }
}
public class Test3 {

    public static void main(String[] args) throws IOException {

        M2 m = new M2();
        m.method();

        System.out.println("normal worlflow..");

    }
}

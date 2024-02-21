package Exception;


import java.io.IOException;

class Test{
    void m()throws IOException {
        throw new IOException("device error");//checked exception
    }
    void n()throws IOException{
        m();
    }
    void p(){
        try{
            n();
        }catch(Exception e){System.out.println("exception handled");}
    }

}
public class ThrowsLearning {

    public static void main(String[] args) {
        Test obj=new Test();
        obj.p();
        System.out.println("normal flow...");
    }
}

package Threads;

class EvenOddPrinter implements Runnable{

    static int Counter = 1;
    int remainder;

    static Object flag = new Object();


    EvenOddPrinter(int remainder){
        this.remainder = remainder;
    }

    public void PrintingThreads(){
        System.out.println(Thread.currentThread().getName() + " : " + Counter++);
    }

    @Override
    public void run() {

        for(int i = 0 ; i< 5 ; ++i){
            synchronized (flag){
                while (Counter%2 != remainder){
                    try {
                        flag.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                //PrintingThreads();

                System.out.println(Thread.currentThread().getName() + " : " + Counter++);

                flag.notifyAll();
            }

        }
    }
}

public class MultiThreading {
    public static void main(String[] args) {

        EvenOddPrinter evenObj = new EvenOddPrinter(0);
        EvenOddPrinter oddObj = new EvenOddPrinter(1);

        Thread even = new Thread(evenObj , "Even Thread");
        Thread odd = new Thread(oddObj , "Odd Thread");

        odd.start();
        even.start();

    }
}

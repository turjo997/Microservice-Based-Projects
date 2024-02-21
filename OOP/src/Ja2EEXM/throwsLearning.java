package Ja2EEXM;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

class ReadAndWrite{
    void readFile() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("E:\\BJIT\\abc.txt");
    }

    void saveFile() throws FileNotFoundException {
        String s = "This is demo";
        FileOutputStream fos = new FileOutputStream("E:\\BJIT\\abc.txt");
    }
}
public class throwsLearning {

    public static void main(String[] args) {
          ReadAndWrite ob = new ReadAndWrite();

        try {
            ob.readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Hello");

        try {
            ob.saveFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Hello");
    }
}

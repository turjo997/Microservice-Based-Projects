package HomeLearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

class TestThrow2 {

    //function to check if person is eligible to vote or not
    public static void method() throws FileNotFoundException {

        FileReader file = new FileReader("E:/BJIT/J2EE/Course Materials/Class Lectures//abc.txt");
        BufferedReader fileInput = new BufferedReader(file);


        throw new FileNotFoundException();

    }
}

    public class ThrowExceptionLearning {


    public static void validate(int age){
        if(age < 18){
            throw new ArithmeticException("Person is not eligible to vote");
        }else{
            System.out.println("Person is eligible to vote!!");
        }
    }

    public static void main(String[] args) {

        //ThrowExceptionLearning.validate(13);
        //validate(21);


        try
        {
            TestThrow2.method();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}

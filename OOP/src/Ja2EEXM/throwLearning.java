package Ja2EEXM;

import java.util.Scanner;

class YoungerAgeException extends RuntimeException{
    YoungerAgeException(String message){
        super(message);
    }
}



public class throwLearning {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter your age : ");

        try{
            int age = input.nextInt();

            if(age < 18){
                throw new YoungerAgeException("You are not eligible for voting");
            }else{
                System.out.println("You can vote successfully");
            }

        }catch (YoungerAgeException e){
           e.printStackTrace();
        }


    }
}

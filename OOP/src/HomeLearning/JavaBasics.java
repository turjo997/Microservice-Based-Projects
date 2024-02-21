package HomeLearning;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JavaBasics {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int [] myArray = new int[]{1,2,3,4,5};

        for (int x : myArray){
         //   System.out.println(x);
        }

        int [] myArr = new int[10];

        for (int i = 0 ; i < 10 ; ++i){
            myArr[i] = i;
        }

        for(int x : myArr){
           // System.out.println(x);
        }

        int [][] myArray2 = new int[2][2];

        for(int i = 0 ; i < 2 ; ++i){
            for(int j = 0 ; j < 2 ; ++j){
                //int x = input.nextInt();
                //myArray2[i][j] = x;
            }
        }

        for(int [] i : myArray2){
            for (int j : i){
                //System.out.println(j);
            }
        }

       int [][] myArr3 = new int [][] {{1,2} , {3,4}};
       int [][] myArr4 = {{1,2} , {3,4}};

        for(int [] i : myArr4){
            for (int j : i){
                System.out.println(j);
            }
        }

        int [][] myArr5 = new int[2][];

        myArr5[0] = new int[2];
        myArr5[0][0] = 10;
        myArr5[0][1] = 20;

        myArr5[1] = new int[3];

        myArr5[1][0] = 30;
        myArr5[1][1] = 40;
        myArr5[1][2] = 50;

//        int n = input.nextInt();
//        int x = n % 2;
//
//        switch (x){
//
//            case 0:
//                System.out.println("The number is even");
//                break;
//            case 1:
//                System.out.println("The number is odd");
//                break;
//            default:
//                break;
//        }

        List<String> names = Arrays.asList("Anowar" , "Alim" , "John" , "Bob");

        for (String name : names){
         //   System.out.println(name);
        }

        int i = 10;
        Integer integerObject = Integer.valueOf(i);
        Integer integerObject1 = i; // autoboxing

        int j = integerObject.intValue();

        int k = integerObject; // unboxing

        System.out.println(integerObject);
        System.out.println(integerObject1);
        System.out.println(j);



        String str = "123";
        int i1 = Integer.parseInt(str);
        Integer integerObject2 = Integer.valueOf( str );

        











    }
}

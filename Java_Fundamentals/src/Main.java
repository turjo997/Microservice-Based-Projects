import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner ip = new Scanner(System.in);

//        String s = ip.nextLine();
//        System.out.println(s);
//
//        int x = ip.nextInt();
//        System.out.println(x);
//
//        int a=10;
//        float f=a;
//        System.out.println(a);
//        System.out.println(f);

//          int a =-128;
//          byte b = (byte) a;
//
//          System.out.println(a);
//          System.out.println(b);
//
//        System.out.println("Enter the size : ");
//        int n = ip.nextInt();
//
//        int[] ar = new int[n];
//
//        for(int i = 0 ; i < n ; ++i){
//             ar[i] = ip.nextInt();
//        }
//
//        Arrays.sort(ar);
//
//        for(int i = 0 ; i < n ; ++i){
//            System.out.println(ar[i]);
//        }

//        ArrayList<Integer> num = new ArrayList<>();
//
//        System.out.println(num.size());
//
//        num.add(10);
//        num.add(20);
//        num.add(18);
//        num.add(45);
//
//        num.add(4,50);
//
//        System.out.println(num);
//
//        for(int x : num){
//           // System.out.println(x);
//        }
//
//        num.remove(3);
//
//
//        for(int x : num){
//           // System.out.println(x);
//        }
//
//
//        Iterator it = num.iterator();
//
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }


//        String s1 = "Ullash Bhattacharjee" ;
//        String s3 = "Ullash bhattacharjee" ;
//
//        String s2 = new String ("Ullash Bhattacharjee");
//
//
//        int len = s1.length() ;
//
//        System.out.println(len);
//        System.out.println(s2.length());
//
//        if(s1 == s2){
//            System.out.println("Strings are same");
//        }else{
//            System.out.println("Strings are not same");
//        }
//
//        if(s1.equals(s3)){
//            System.out.println("Strings are same");
//        }else{
//            System.out.println("Strings are not same");
//        }


//        String s1 = "apple";
//        String s2 = "banana";
//        int result = s1.compareTo(s2); // negative value
//
//        System.out.println(result);

//        String names[] = {"Karim" , "Rahim" , "Bulbul"};
//
//        for(int i = 0 ; i < names.length ; ++i){
//            System.out.println(names[i]);
//        }

//        String country = "Bangladesh is my country";
//
//        char ch = country.charAt(2); // 2 no. ind er char konta
//
//        int val = country.codePointAt(0); // 0 ind er ascii val
//
//
//        int po = country.indexOf("is");
//        System.out.println(po);
//
//        int po1 = country.lastIndexOf("try");
//        System.out.println(po1);


//        StringBuffer sb = new StringBuffer("Ullash");
//
//        sb.append(" Bhattacharjee") ;
//
//        sb.reverse();
//
//        System.out.println(sb);
//
//        String ag = sb.reverse().toString();
//
//        System.out.println(sb);


        int x1 = 30;
        Integer y = Integer.valueOf(x1); //autoboxing
        Integer z = x1;
        int k1 =y;

        //Float.valueOf();
        //Double.valueOf()


        //Double d1 = 10.0;

        Double d = new Double(10.45);
        double e = d.doubleValue(); // unboxing
        double f = d;

        System.out.println(e);
        System.out.println(f);


        int i = 90;

        String s5 = Integer.toString(i);

        double j = 67.88;
        s5 = Double.toString(j);

        String s6 = "32";
        int k = Integer.parseInt(s6);

        s6 = "45.67";
        double m = Double.parseDouble(s6);

    }
}
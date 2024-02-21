package HomeLearning;

import java.util.*;

public class SetAndMap {
    public static void main(String[] args) {
        Set<Integer> s = new HashSet<>();

        s.add(11);
        s.add(23);
        s.add(13);
        s.add(433);
        s.add(11);

        for (int i : s){
            System.out.println(i);
        }


        Set<Integer> s2 = new TreeSet<>();

        s2.add(11);
        s2.add(23);
        s2.add(13);
        s2.add(433);
        s2.add(11);


        for (int i : s2){
            System.out.println(i);
        }


        int [] arr = {1,4,5,9,7,7,1,4,5};

        Map<Integer , Integer> mp = new HashMap<Integer , Integer>();

        for(int i = 0 ; i < arr.length ; ++i){
            if(mp.containsKey(arr[i])){
                mp.put(arr[i], mp.get(arr[i]) + 1);
            }else{
                mp.put(arr[i], 1 );
            }
        }

        for(Map.Entry e : mp.entrySet()){
            System.out.println("value : " + e.getKey() + " Frequency Count : " + e.getValue());
        }

       // Map<String , String> mp2 = new Hashtable<>(); // Synchronized
       // Map<String , String> mp2 = new HashMap<>(); // not Synchronized
       // Map<String , String> mp2 = new TreeMap<>(); // not Synchronized
        Map<String , String> mp2 = new LinkedHashMap<>(); // not Synchronized

        mp2.put("Actor" , "Akshay");
        mp2.put("Footballer" , "Neymar");
        mp2.put("Cricketer" , "Dhoni");


        Set<String> keys = mp2.keySet();

        for(String key : keys){
            System.out.println(key + ' ' + mp2.get(key));
        }
    }
}

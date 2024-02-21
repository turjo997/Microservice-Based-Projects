package HomeLearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Book{

    int id;
    String name,author,publisher;
    int quantity;
    public Book(int id, String name, String author, String publisher, int quantity) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
    }
}

class StudentClass{
    int id , rollNo;
    String name;

    public StudentClass(int id , int rollNo , String name){
        this.id = id;
        this.rollNo = rollNo;
        this.name = name;
    }
}

public class MapLearning {

    public static void main(String[] args) {
        HashMap<Integer , Book> mp = new HashMap<>();
        //Creating Books
        Book b1=new Book(101,"Let us C","Yashwant Kanetkar","BPB",8);
        Book b2=new Book(102,"Data Communications & Networking","Forouzan","Mc Graw Hill",4);
        Book b3=new Book(103,"Operating System","Galvin","Wiley",6);
        //Adding Books to map
        mp.put(1,b1);
        mp.put(2,b2);
        mp.put(3,b3);

        //Traversing map
        for(Map.Entry<Integer, Book> entry:mp.entrySet()){
            int key=entry.getKey();
            Book b=entry.getValue();
            System.out.println(key+" Details:");
            System.out.println(b.id+" "+b.name+" "+b.author+" "+b.publisher+" "+b.quantity);
        }

        ArrayList<StudentClass> ar = new ArrayList<>();

        ar.add(new StudentClass(1,1, "Rahim"));
        ar.add(new StudentClass(2,2, "Karim"));
        ar.add(new StudentClass(3,3, "Rahim"));

        HashMap<String, Integer> mp1 = new HashMap<>();

        for(StudentClass ob : ar){
            if(mp1.containsKey(ob.name)){
                mp1.put(ob.name , mp1.get(ob.name) + 1);
            }else{
                mp1.put(ob.name , + 1);
            }
        }

        for(Map.Entry e : mp1.entrySet()){
            System.out.println(e.getKey() + " frequency : " + e.getValue());
        }


    }
}

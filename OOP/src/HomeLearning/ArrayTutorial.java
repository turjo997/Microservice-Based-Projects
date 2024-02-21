package HomeLearning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Person{
    int rollno;
    String name;
    int age;
    Person(int rollno,String name,int age){
        this.rollno=rollno;
        this.name=name;
        this.age=age;
    }
}

public class ArrayTutorial {

    public static void main(String[] args) {
        List<Integer> ar = new ArrayList<>();
        ar.add(1);
        ar.add(2);
        ar.add(3);
        ar.add(4);

        Iterator<Integer> it = ar.iterator();

        while (it.hasNext()) {
            Integer item = it.next();
            System.out.println(item);
        }


        ArrayList<Person> ob = new ArrayList<>();

        ob.add(new Person(1 , "x" , 10));
        ob.add(new Person(2 , "Y" , 11));
        ob.add(new Person(3 , "Z" , 12));

        Iterator it1 = ob.iterator();

        while (it1.hasNext()){
            Person p = (Person) it1.next();
            System.out.println(p.rollno + " " + p.age + " " + p.name);
        }

        int vertexCount = 3;
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(vertexCount);

        for(int i=0; i < vertexCount; i++) {
            graph.add(new ArrayList());
        }

        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);

        graph.get(1).add(0);
        graph.get(2).add(1);
        graph.get(0).add(2);


        int vertexCount1 = graph.size();
        for (int i = 0; i < vertexCount1; i++) {
            int edgeCount = graph.get(i).size();
            for (int j = 0; j < edgeCount; j++) {
                Integer startVertex = i;
                Integer endVertex = graph.get(i).get(j);
                System.out.printf("Vertex %d is connected to vertex %d%n", startVertex, endVertex);
            }
        }

        int n = 2;

        // Here v is a Vector of Vector
        ArrayList<ArrayList<Person> > v = new ArrayList<>(n);


        ArrayList<Person> a1 = new ArrayList<>();

        a1.add(new Person(1 , "x" , 10));
        a1.add(new Person(2 , "Y" , 11));
        a1.add(new Person(3 , "Z" , 12));


        v.add(a1);

        ArrayList<Person> a2 = new ArrayList<>();
        a2.add(new Person(4 , "W" , 13));
        v.add(a2);



        // Iterate through the ArrayList
        for (ArrayList<Person> studentList : v) {
            // Iterate through each student in the inner ArrayList
            for (Person student : studentList) {
                // Access the name, age, and roll values for each student
                String name = student.name;
                int age = student.age;
                int roll = student.rollno;
                // Do something with the values, such as print them out
                System.out.println("Name: " + name + ", Age: " + age + ", Roll: " + roll);
            }
        }


//
//        for (int i = 0; i < v.size(); i++) {
//            for (int j = 0; j < v.get(i).size(); j++) {
//                System.out.print(v.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
    }
}

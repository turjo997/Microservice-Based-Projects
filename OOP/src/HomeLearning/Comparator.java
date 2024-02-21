package HomeLearning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class Student implements Comparable<Student>{
    int rollNo;
    int marks;

    public Student(int rollNo , int marks){
        this.rollNo = rollNo;
        this.marks = marks;
    }

    public String toString(){
        return "Student{" + "rollNo=" + rollNo + " , marks="+marks +'}';
    }

    public int compareTo(Student s){
        return this.marks > s.marks?-1:this.marks<s.marks?1:0;
    }
}

public class Comparator {
    public static void main(String[] args) {

        List<Student> studs = new ArrayList<>();
        studs.add(new Student(1,55));
        studs.add(new Student(2,95));
        studs.add(new Student(3,35));
        studs.add(new Student(4,65));
        studs.add(new Student(5,25));

//        Collections.sort(studs , (s1,s2)->{
//            return s1.marks > s2.marks ? 1: -1;
//        });

        Collections.sort(studs);


        for (Student s : studs){
            System.out.println(s);
        }
    }
}

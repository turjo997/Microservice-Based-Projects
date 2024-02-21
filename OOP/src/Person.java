public class Person {
    String name;
    int age;
    Object ob;

    Person(String name , int age){
        this.name = name;
        this.age = age;
    }

    void studentDetails(Student s){
        System.out.println("Name is : "+s.name);
        System.out.println("Age is : "+s.age);
        System.out.println("ID is : "+s.id);
    }

    void teacherDetails(Teacher t){
        System.out.println("Name is : "+t.name);
        System.out.println("Age is : "+t.age);
        System.out.println("Designation is : "+t.designation);
    }


    void printDetails(){
        System.out.println("Name is : "+name);
        System.out.println("Age is : "+age);
        System.out.println("Third object is : "+ob);
    }
}

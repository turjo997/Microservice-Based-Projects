public class Test2 {
    public static void main(String[] args) {

        Person p = new Person("Rahim" , 23);
        Student st = new Student("ABC" , 16 , 2);
        Teacher te = new Teacher("DEF" , 32 ,"Lecturer");


        p.studentDetails(st);
        p.teacherDetails(te);


        Person ob1 = new Student("Asad" , 23 , 5);
        Person ob2 = new Teacher("Aziz" , 34 , "Assistant Professor");

        ob1.printDetails();
        ob2.printDetails();

    }
}

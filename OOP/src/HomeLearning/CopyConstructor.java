package HomeLearning;



public class CopyConstructor {

    int id;
    String name;
    //constructor to initialize integer and string
    CopyConstructor(int i,String n){
        id = i;
        name = n;
    }
    //constructor to initialize another object
    CopyConstructor(CopyConstructor s){
        id = s.id;
        name =s.name;
    }

    CopyConstructor(){}
    void display(){System.out.println(id+" "+name);}

    public static void main(String[] args) {
        CopyConstructor s1 = new CopyConstructor(111,"Karan");
        CopyConstructor s2 = new CopyConstructor(s1);
        CopyConstructor s3 = new CopyConstructor();
        s1.display();
        s2.display();

        s3.id = s2.id;
        s3.name = s2.name;

        s3.display();
    }
}

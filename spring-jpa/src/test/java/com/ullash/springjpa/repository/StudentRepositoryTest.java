package com.ullash.springjpa.repository;

import com.ullash.springjpa.entity.Course;
import com.ullash.springjpa.entity.Guardian;
import com.ullash.springjpa.entity.Student;
import com.ullash.springjpa.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {


    @Autowired
    private StudentRepository studentRepository;

//    @Test
//    public void saveCourseWithStudentAndTeacher() {
//
//        Teacher teacher = Teacher.builder()
//                .firstName("Lizze")
//                .lastName("Morgan")
//                .build();
//
//        Course course = Course
//                .builder()
//                .title("AI")
//                .credit(12)
//                .teacher(teacher)
//                .build();
//
//        Student student = Student.builder()
//                .firstName("Abhishek")
//                .lastName("Singh")
//                .emailId("abhishek@gmail.com")
//                .build();
//
//        student.addCourse(course);
//
//        studentRepository.save(student);
//    }


    @Test
    public void saveStudentWithGuardian() {

        Guardian guardian = Guardian.builder()
                .email("razon@gmail.com")
                .name("Razon")
                .mobile("9999956324")
                .build();

        Student student = Student.builder()
                .firstName("Shabbir")
                .emailId("shabbir@gmail.com")
                .lastName("Kumar")
                .guardian(guardian)
                .build();

        studentRepository.save(student);
    }


    @Test
    public void printAllStudent() {
        List<Student> studentList =
                studentRepository.findAll();

        System.out.println("studentList = " + studentList);
    }

    @Test
    public void printStudentByFirstName() {

        List<Student> students =
                studentRepository.findByFirstName("shivam");

        System.out.println("students = " + students);
    }

    @Test
    public void printStudentByFirstNameContaining() {

        List<Student> students =
                studentRepository.findByFirstNameContaining("sh");

        System.out.println("students = " + students);
    }

    @Test
    public void printStudentBasedOnGuardianName(){
        List<Student> students =
                studentRepository.findByGuardianName("Nikhil");
        System.out.println("students = " + students);
    }

    @Test
    public void printgetStudentByEmailAddress() {
        Student student =
                studentRepository.getStudentByEmailAddress(
                        "shabbir@gmail.com"
                );

        System.out.println("student = " + student);
    }

    @Test
    public void printgetStudentFirstNameByEmailAddress() {
        String firstName =
                studentRepository.getStudentFirstNameByEmailAddress(
                        "shivam@gmail.com"
                );
        System.out.println("firstName = " + firstName);
    }

    @Test
    public void printgetStudentByEmailAddressNative(){
        Student student =
                studentRepository.getStudentByEmailAddressNative(
                        "shivam@gmail.com"
                );

        System.out.println("student = " + student);
    }

    @Test
    public void printgetStudentByEmailAddressNativeNamedParam() {
        Student student =
                studentRepository.getStudentByEmailAddressNativeNamedParam(
                        "shivam@gmail.com"
                );

        System.out.println("student = " + student);
    }

    @Test
    public void updateStudentNameByEmailIdTest() {
        studentRepository.updateStudentNameByEmailId(
                "shabbir dawoodi",
                "shabbir@gmail.com");
    }




}
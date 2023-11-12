package com.example.universitymanagement.accounts.student.service;

import com.example.universitymanagement.Engine.service.UniversityManagerService;
import com.example.universitymanagement.Exceptions.model.*;
import com.example.universitymanagement.accounts.student.model.Student;
import com.example.universitymanagement.accounts.student.model.StudentCourse;
import com.example.universitymanagement.accounts.student.model.StudentCourseId;
import com.example.universitymanagement.accounts.student.model.StudentRequests;
import com.example.universitymanagement.accounts.student.repositry.StudentCourseRepository;
import com.example.universitymanagement.accounts.student.repositry.StudentRepository;
import com.example.universitymanagement.accounts.student.repositry.StudentRequestRepository;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.model.Grade;
import com.example.universitymanagement.courses.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private UniversityManagerService universityManagerService;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRequestRepository studentRequestRepository;
    public int countStudentsBySemesterAndCourse(Course course) {
        return studentCourseRepository.countAllById_Course_NameAndId_Semester(course.getName(),universityManagerService.getSemester());
    }

    public StudentRequests newStudent(
            String email,
            String password,
            String name,
            Double balance
    ) throws StudentAlreadyExistsException {
        if(studentRequestRepository.existsByEmail(email)){
            throw new StudentAlreadyExistsException();
        }
        StudentRequests student = StudentRequests
                .builder()
                .email(email)
                .password(password)
                .name(name)
                .balance(balance)
                .build();
        return studentRequestRepository.save(student);
    }

    public Student loginStudent(String email, String password) throws InvalidCredentialsException {
        if(!studentRepository.existsByEmailAndPassword(email,password))
            throw new InvalidCredentialsException("Invalid Credentials");
        return studentRepository
                .findByEmailAndPassword(email,password);
    }

    public StudentCourse registerCourse(String email,String courseName) throws SystemException{
        if(!studentRepository.existsByEmail(email))
            throw new NonExistingStudentException();
        if(!courseService.existsByCourseName(courseName))
            throw new NonExistentCourseException();
        Student student=studentRepository.findByEmail(email);
        Course course= courseService.findByName(courseName);

        if(universityManagerService.isSemesterRunning()){
            throw new SemesterRunningException();
        }

        int studentCount = studentCourseRepository.countAllById_Course_NameAndId_Semester(courseName,universityManagerService.getSemester());
        if(studentCount==course.getMaxStudents()){
            throw new CourseFullException();
        }

        if(course.getCreditHours()*1000<=student.getBalance()&&!student.isFinancialAided()) {
            throw new NotEnoughCreditsException();
        }
        if(studentCourseRepository.existsById_Student_EmailAndId_Course_Name(email,courseName)
                &&studentCourseRepository.findById_Student_EmailAndId_Course_Name(email,courseName).getGrade()!= Grade.NULL
        ) {
            throw new CourseAlreadyRegisteredException();
        }
        StudentCourse studentCourse=new StudentCourse(
                new StudentCourseId(
                        student,
                        course,
                        universityManagerService.getSemester()
                )
                ,Grade.NULL
        );
        studentCourseRepository.save(studentCourse);
        if(!student.isFinancialAided())
            student.setBalance(student.getBalance()-course.getCreditHours()*1000);
        student.setCreditHours(student.getCreditHours()+course.getCreditHours());

        student.setCreditHours(student.getCreditHours()+3);
        studentRepository.save(student);
        //System.out.println("registered course successfully");
        return studentCourse;
    }

    public List<Course> showMyCourses(String email) throws NonExistingStudentException {
        if(!studentRepository.existsByEmail(email))
            throw new NonExistingStudentException();
        Student student=studentRepository.findByEmail(email);
        ArrayList<StudentCourse> studentCourses=studentCourseRepository.findAllById_StudentAndId_Semester(student,universityManagerService.getSemester());
        ArrayList<Course> courses=new ArrayList<>();
        studentCourses.forEach(studentCourse -> {
            courses.add(studentCourse.getId().getCourse());
        });
        return courses;
    }
    public List<Course> showAllCourses(){
        return courseService.findAll();
    }

    public List<Pair<Course,Grade>> showGrades(String email,int semester) throws NonExistingStudentException {
        if(!studentRepository.existsByEmail(email))
            throw new NonExistingStudentException();
        Student student=studentRepository.findByEmail(email);
        ArrayList<StudentCourse> studentCourses=studentCourseRepository.findAllById_StudentAndId_Semester(student,semester);
        ArrayList<Pair<Course,Grade>> Grades=new ArrayList<>();
        studentCourses.forEach(studentCourse -> {
            Grades.add(Pair.of(studentCourse.getId().getCourse(),studentCourse.getGrade()));
        });
        return Grades;
    }

    public void removeAllStudents(String courseName) throws NonExistentCourseException {
        if(!courseService.existsByCourseName(courseName))
            throw new NonExistentCourseException();
        List<StudentCourse> studentCourses= studentCourseRepository.findAllById_Course_NameAndId_Semester(courseName,universityManagerService.getSemester());
        studentCourses.forEach(studentCourse -> {
            Student student=studentCourse.getId().getStudent();
            student.setCreditHours(student.getCreditHours()-studentCourse.getId().getCourse().getCreditHours());
            if(!student.isFinancialAided())
                student.setBalance(student.getBalance()+1000*studentCourse.getId().getCourse().getCreditHours());
            studentRepository.save(student);
        });
        studentCourseRepository.deleteAll((Iterable<StudentCourse>) studentCourses);
    }
    public void removeStudentCourse(String email,String courseName) throws SystemException {
        if(!studentRepository.existsByEmail(email))
            throw new NonExistingStudentException();
        if(!courseService.existsByCourseName(courseName)||!studentCourseRepository.existsById_Student_EmailAndId_Course_NameAndId_Semester(email, courseName,universityManagerService.getSemester()))
            throw new NonExistentCourseException();
        Student student=studentRepository.findByEmail(email);
        Course course= courseService.findByName(courseName);
        student.setCreditHours(student.getCreditHours()- course.getCreditHours());
        if(!student.isFinancialAided())
            student.setBalance(student.getBalance()+1000*course.getCreditHours());
        studentRepository.save(student);
        studentCourseRepository.delete(studentCourseRepository.findById_Student_EmailAndId_Course_Name(email,courseName));
    }
    public List<StudentRequests> showRequests(){
        return studentRequestRepository.findAll();
    }


    public boolean acceptRequests(String id) {
        /*for (int i = 0; i <ids.length ; i++) {
            String id= ids[i];
            if(!studentRequestRepository.existsById(id))
                continue;*/
            StudentRequests studentRequests=studentRequestRepository.findById(id).orElseThrow();
            studentRepository.save(
                    Student
                            .builder()
                            .email(studentRequests.getEmail())
                            .password(studentRequests.getPassword())
                            .name(studentRequests.getName())
                            .balance(studentRequests.getBalance())
                            .creditHours(0)
                            .financialAided(false)
                            .build()
            );
            studentRequestRepository.delete(studentRequests);
        //}

        return true;
    }

    public Boolean rejectRequests(List<String> id) {
        studentRequestRepository.deleteAllByIdIn(id);
        return true;
    }

    public Student modifyStudent(String id, String email, String password, String name, Double balance) throws NonExistingStudentException {
        if(!studentRepository.existsById(id))
            throw new NonExistingStudentException();
        Student student=studentRepository.findById(id).orElseThrow();
        student.setEmail(email);
        student.setPassword(password);
        student.setName(name);
        student.setBalance(balance);
        return studentRepository.save(student);
    }

    public List<StudentCourse> findStudentCourseBySemester(int semester) {
        return studentCourseRepository.findAllById_Semester(semester);
    }

    public void randomizeGrades() {
        Grade grades[]=new Grade[]{Grade.A,Grade.B,Grade.C,Grade.D,Grade.F};
        List<StudentCourse> studentCourses=studentCourseRepository.findAllById_Semester(universityManagerService.getSemester());
        studentCourses.forEach(studentCourse -> {
            studentCourse.setGrade(grades[(int)(Math.random()*5)]);
            Student student=studentCourse.getId().getStudent();
            student.setCreditHours(0);
            studentRepository.save(student);
            studentCourseRepository.save(studentCourse);
        });
    }
}





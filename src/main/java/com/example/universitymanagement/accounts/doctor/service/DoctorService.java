package com.example.universitymanagement.accounts.doctor.service;

import com.example.universitymanagement.Engine.service.UniversityManagerService;
import com.example.universitymanagement.Exceptions.model.*;
import com.example.universitymanagement.accounts.admin.service.AdminService;
import com.example.universitymanagement.accounts.doctor.model.Doctor;
import com.example.universitymanagement.accounts.doctor.model.DoctorRequest;
import com.example.universitymanagement.accounts.doctor.model.DoctorTeachesCourse;
import com.example.universitymanagement.accounts.doctor.model.DoctorTeachesCourseId;
import com.example.universitymanagement.accounts.doctor.repositry.DoctorRepository;
import com.example.universitymanagement.accounts.doctor.repositry.DoctorRequestRepository;
import com.example.universitymanagement.accounts.doctor.repositry.DoctorTeachesCourseRepository;
import com.example.universitymanagement.accounts.student.service.StudentService;
import com.example.universitymanagement.courses.model.Course;
import com.example.universitymanagement.courses.repositry.CourseRepository;
import com.example.universitymanagement.courses.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Lazy
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final CourseRepository courseService;
    private final DoctorTeachesCourseRepository doctorTeachesCourseRepository;
    private final UniversityManagerService universityManagerService;
    private final StudentService studentService;
    private final DoctorRequestRepository doctorRequestRepository;

    public DoctorRequest newDoctor(
            String email,
            String password,
            String name,
            String specialization
    ) throws DoctorAlreadyExistsException {
        if(!doctorRepository.existsByEmail(email))
            throw new DoctorAlreadyExistsException();
        DoctorRequest doctor = DoctorRequest
                .builder()
                .email(email)
                .password(password)
                .name(name)
                .specialization(specialization)
                .build();
        return doctorRequestRepository.save(doctor);
    }

    public Doctor loginDoctor(String email, String password) throws InvalidCredentialsException {
        return doctorRepository
                .findByEmailAndPassword(email,password)
                .orElseThrow(()->new InvalidCredentialsException());
    }


    public List<Course> showAllCourses(String email) throws NonExistentDoctorException {
        Doctor doctor=doctorRepository.findByEmail(email).orElseThrow(NonExistentDoctorException::new);
        return courseService.findAllBySpecialization(doctor.getSpecialization());
    }
    public List<Course> showMyCourses(String email) throws NonExistentDoctorException {
        Doctor doctor=doctorRepository.findByEmail(email).orElseThrow(NonExistentDoctorException::new);
        List<DoctorTeachesCourse> doctorTeachesCourses= doctorTeachesCourseRepository.findAllById_Doctor_EmailAndId_Semester(email,universityManagerService.getSemester());
        List<Course> courses=new ArrayList<>();
        doctorTeachesCourses.forEach(doctorTeachesCourse -> courses.add(doctorTeachesCourse.getId().getCourse()));
        return courses;
    }

    public boolean registerCourse(String email, String courseName) throws SystemException{
        Doctor doctor=doctorRepository.findByEmail(email).orElseThrow(NonExistentDoctorException::new);

        if(universityManagerService.isSemesterRunning()){
            throw new SemesterRunningException();
        }
        if(doctorTeachesCourseRepository.existsById_SemesterAndId_Course_Name(universityManagerService.getSemester(),courseName)){
            throw new CourseHasInstructorException();
        }
        if(doctorTeachesCourseRepository.countAllById_Doctor_EmailAndId_Semester(email,universityManagerService.getSemester())>=3){
            throw new MaxCoursesException();
        }
        Course course=courseService.findByName(courseName).orElseThrow(NonExistentCourseException::new);
        DoctorTeachesCourse doctorTeachesCourse=new DoctorTeachesCourse(
                new DoctorTeachesCourseId(
                        doctor,
                        course,
                        universityManagerService.getSemester()
                )

        );
        return true;
    }

    public Boolean removeDoctorCourse(String email, String courseName) throws NonExistentCourseException, NonExistentDoctorException, SemesterRunningException {
        if(universityManagerService.isSemesterRunning())
            throw new SemesterRunningException();
        Doctor doctor=doctorRepository.findByEmail(email).orElseThrow(NonExistentDoctorException::new);

        Course course=courseService.findByName(courseName).orElseThrow(NonExistentCourseException::new);
        if(doctorTeachesCourseRepository.existsById_Doctor_EmailAndId_Course_NameAndId_Semester(email,courseName, universityManagerService.getSemester())){
            doctorTeachesCourseRepository.deleteById(new DoctorTeachesCourseId(doctor,course, universityManagerService.getSemester()));
            studentService.removeAllStudents(courseName);
            return true;
        }
        throw new NonExistentCourseException();
    }
    public List<DoctorRequest> showRequests(){
        return doctorRequestRepository.findAll();
    }

    public Boolean acceptRequests(List<String> emails) {
        emails.forEach(email->{
            if(!doctorRequestRepository.existsByEmail(email))
                return;
            DoctorRequest doctorRequest=doctorRequestRepository.findByEmail(email);
            Doctor doctor=Doctor
                    .builder()
                    .email(doctorRequest.getEmail())
                    .password(doctorRequest.getPassword())
                    .name(doctorRequest.getName())
                    .specialization(doctorRequest.getSpecialization())
                    .build();
            doctorRepository.save(doctor);
            doctorRequestRepository.delete(doctorRequest);
        });
        return true;
    }

    public Boolean rejectRequests(List<String> emails) {
        doctorRequestRepository.deleteAllByEmailIn(emails);
        return true;
    }

    public Doctor modifyDoctor(String email, String password, String name, String specialization) throws NonExistentDoctorException {
        Doctor doctor=doctorRepository.findByEmail(email).orElseThrow(NonExistentDoctorException::new);

        doctor.setPassword(password);
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        return doctorRepository.save(doctor);
    }

    public Doctor findByEmail(String email) throws NonExistentDoctorException {
        return doctorRepository.findByEmail(email).orElseThrow(()->new NonExistentDoctorException());
    }

    public boolean existsByCourseAndSemester(Course course) {
        return doctorTeachesCourseRepository.existsById_SemesterAndId_Course_Name(universityManagerService.getSemester(), course.getName());
    }

    public void removeAllCoursesByNoInstructor() {
        List<Course> courses=courseService.findAll();
        courses.forEach(course -> {
            int studentCount = studentService.countStudentsBySemesterAndCourse(course);
            boolean doctor = doctorTeachesCourseRepository.existsById_SemesterAndId_Course_Name(universityManagerService.getSemester(), course.getName());
            if(studentCount>0&&!doctor){
                try {
                    studentService.removeAllStudents(course.getName());
                } catch (NonExistentCourseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

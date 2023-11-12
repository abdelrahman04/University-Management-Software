package com.example.universitymanagement;

import com.example.universitymanagement.accounts.student.controller.StudentController;
import com.example.universitymanagement.accounts.student.service.StudentService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTests {
private MockMvc mockMvc;
@Before
public void setup() {
    MockitoAnnotations.initMocks(this);
    //mockMvc = MockMvcBuilders.standaloneSetup(new StudentController()).build();
}
/*@Test
    public void checklogin(){
        StudentService studentService = new StudentService();
        String username = "student";
        String password = "student";
        boolean result = studentService.loginStudent(username, password);
        assertEquals(true, result);
}*/

}

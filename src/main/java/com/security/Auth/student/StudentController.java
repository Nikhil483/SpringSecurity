package com.security.Auth.student;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

        private static final List<Student> studentList = new ArrayList<>(Arrays.asList(
                new Student(1,"Nikhil"),
                new Student(2, "Bharath"),
                new Student(3, "Arun")
        ));

        @GetMapping("{studentId}")
        public  Student getStudent(@PathVariable("studentId")  Integer studentId) {
                System.out.println("In getStudent controller");
                return studentList.stream().filter(student -> student.getStudentId() == studentId)
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Student with Id : "+studentId+" not found"));
        }
}

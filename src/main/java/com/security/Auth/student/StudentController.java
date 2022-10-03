package com.security.Auth.student;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StudentController {

        private static final List<Student> studentList = new ArrayList<>(Arrays.asList(
                new Student(1,"Nikhil"),
                new Student(2, "Bharath"),
                new Student(3, "Arun")
        ));

        @GetMapping("getStudent/{studentId}")
        public  Student getStudent(@PathVariable("studentId")  Integer studentId) {
                System.out.println("In getStudent controller");
                return studentList.stream().filter(student -> student.getStudentId() == studentId)
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Student with Id : "+studentId+" not found"));
        }

        @GetMapping(path = "/getAllStudents")
        @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
        public List<Student> getAllStudents() {
                System.out.println("getAllStudents");
                return studentList;
        }

        @PostMapping(path = "/addStudent")
        @PreAuthorize("hasAuthority('student:write')")
        public void registerNewStudent(@RequestBody Student student) {
                System.out.println("registerNewStudent");
                System.out.println(student);
        }

        @DeleteMapping(path = "deleteStudent/{studentId}")
        @PreAuthorize("hasAuthority('student:write')")
        public void deleteStudent(@PathVariable("studentId") Integer studentId) {
                System.out.println("deleteStudent");
                System.out.println(studentId);
        }

        @PutMapping(path = "editStudent/{studentId}")
        @PreAuthorize("hasAuthority('student:write')")
        public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
                System.out.println("updateStudent");
                System.out.println(String.format("%s %s", studentId, student));
        }
}

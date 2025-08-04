package org.example.studentcourseregistrationsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private String email;
    private String phoneNumber;
    private String program;
    private String semester;
    private String username;
    private String password;
    private final List<Course> registeredCourses;

    public Student(String studentId, String name, String email, String phoneNumber, String program, String semester, String username, String password) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.program = program;
        this.semester = semester;
        this.username = username;
        this.password = password;
        this.registeredCourses = new ArrayList<>();
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Course> getRegisteredCourses() { return registeredCourses; }
    public void addCourse(Course course) { this.registeredCourses.add(course); }
    public void removeCourse(Course course) { this.registeredCourses.remove(course); }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=\'" + studentId + '\'' +
                ", name=\'" + name + '\'' +
                ", email=\'" + email + '\'' +
                ", phoneNumber=\'" + phoneNumber + '\'' +
                ", program=\'" + program + '\'' +
                ", semester=\'" + semester + '\'' +
                ", username=\'" + username + '\'' +
                ", password=\'" + password + '\'' +
                ", registeredCourses=" + registeredCourses.size() +
                '}';
    }
}


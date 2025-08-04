package org.example.studentcourseregistrationsystem.service;

import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.model.Course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {

    private final List<Student> students;
    private final CourseService courseService; // Dependency for loading courses
    private static final String STUDENTS_DIR = "students_data/";

    public StudentService() {
        this.courseService = new CourseService(); // Initialize CourseService
        // Ensure the directory exists
        File dir = new File(STUDENTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.students = loadStudentsFromDirectory();
    }

    private List<Student> loadStudentsFromDirectory() {
        List<Student> loadedStudents = new ArrayList<>();
        File dir = new File(STUDENTS_DIR);
        File[] studentFiles = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (studentFiles != null) {
            for (File file : studentFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 8) {
                            String studentId = parts[0];
                            String name = parts[1];
                            String email = parts[2];
                            String phoneNumber = parts[3];
                            String program = parts[4];
                            String semester = parts[5];
                            String username = parts[6];
                            String password = parts[7];

                            Student student = new Student(studentId, name, email, phoneNumber, program, semester, username, password);

                            if (parts.length == 9 && !parts[8].isEmpty()) {
                                String[] registeredCourseIds = parts[8].split(";");
                                for (String courseId : registeredCourseIds) {
                                    courseService.getCourseById(courseId).ifPresent(student::addCourse);
                                }
                            }
                            loadedStudents.add(student);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error loading student from file " + file.getName() + ": " + e.getMessage());
                }
            }
        }
        return loadedStudents;
    }
    public void saveStudentToFile(Student student) {
        String filename = STUDENTS_DIR + student.getUsername() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder courseIds = new StringBuilder();
            for (Course course : student.getRegisteredCourses()) {
                courseIds.append(course.getCourseId()).append(";");
            }
            String coursesString = courseIds.length() > 0 ? courseIds.substring(0, courseIds.length() - 1) : "";

            writer.write(student.getStudentId() + "," + student.getName() + "," + student.getEmail() + "," + student.getPhoneNumber() + "," + student.getProgram() + "," + student.getSemester() + "," + student.getUsername() + "," + student.getPassword() + "," + coursesString);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving student to file " + filename + ": " + e.getMessage());
        }
    }
    // This method is no longer needed as students are saved individually
    // public void saveStudentsToFile() {
    //     // This method can be removed or adapted if needed
    // }

    public Optional<Student> getStudentById(String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();
    }
    public Optional<Student> getStudentByUsername(String username) {
        return students.stream()
                .filter(s -> s.getUsername().equals(username))
                .findFirst();
    }
    public void addStudent(Student student) {
        if (getStudentById(student.getStudentId()).isEmpty()) {
            this.students.add(student);
            saveStudentToFile(student); // Save individual student file
        }
    }
    public void updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(updatedStudent.getStudentId())) {
                students.set(i, updatedStudent);
                saveStudentToFile(updatedStudent); // Update individual student file
                return;
            }
        }
    }
    public void removeCourseFromStudent(String username, String courseId) {
        Optional<Student> studentOpt = getStudentByUsername(username);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.getRegisteredCourses().removeIf(course -> course.getCourseId().equals(courseId));
            updateStudent(student);
        }
    }
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}


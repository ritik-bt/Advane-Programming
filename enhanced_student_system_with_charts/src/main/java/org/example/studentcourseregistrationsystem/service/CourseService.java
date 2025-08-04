package org.example.studentcourseregistrationsystem.service;

import org.example.studentcourseregistrationsystem.model.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseService {

    private static final String COURSES_FILE = "courses.txt";
    private final List<Course> courses;

    public CourseService() {
        this.courses = loadCoursesFromFile();
    }

    private List<Course> loadCoursesFromFile() {
        List<Course> loadedCourses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    loadedCourses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading courses from file: " + e.getMessage());
            // Optionally create an empty file if it doesn\''t exist and add some default courses
            try (java.io.FileWriter writer = new java.io.FileWriter(COURSES_FILE)) {
                writer.write("CS101,Introduction to Programming,3\n");
                writer.write("MA201,Calculus I,4\n");
                writer.write("PH101,Physics I,3\n");
                writer.write("HI101,World History,3\n");
            } catch (IOException ex) {
                System.err.println("Error creating courses file: " + ex.getMessage());
            }
        }
        return loadedCourses;
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Optional<Course> getCourseById(String courseId) {
        return courses.stream()
                .filter(c -> c.getCourseId().equals(courseId))
                .findFirst();
    }
    
    public void addCourse(Course course) {
        if (getCourseById(course.getCourseId()).isEmpty()) {
            courses.add(course);
            saveCoursesToFile();
        }
    }

    private void saveCoursesToFile() {
        try (java.io.FileWriter writer = new java.io.FileWriter(COURSES_FILE)) {
            for (Course course : courses) {
                writer.write(course.getCourseId() + "," + course.getName() + "," + course.getCreditHours() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving courses to file: " + e.getMessage());
        }
    }
}


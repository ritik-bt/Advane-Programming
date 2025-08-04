package org.example.studentcourseregistrationsystem.controller;

import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.model.Course;
import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.model.User;
import org.example.studentcourseregistrationsystem.service.CourseService;
import org.example.studentcourseregistrationsystem.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class RegisterCourseController {

    @FXML
    private TableView<Course> availableCoursesTable;
    @FXML
    private TextField courseIdField;
    @FXML
    private TextField courseNameField;
    @FXML
    private TextField creditHoursField;
    @FXML
    private Label messageLabel;

    private final CourseService courseService = new CourseService();
    private final StudentService studentService = new StudentService();
    private Student currentStudent;

    @FXML
    public void initialize() {
        // Configure TableView to allow multiple selections
        availableCoursesTable.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // Load available courses and populate the table
        refreshCourseTable();

        // Get the current logged-in student
        User loggedInUser = LoginController.loggedInUser;
        if (loggedInUser != null && "Student".equals(loggedInUser.getRole())) {
            studentService.getStudentByUsername(loggedInUser.getUsername()).ifPresent(student -> {
                this.currentStudent = student;
            });
        }
    }

    private void refreshCourseTable() {
        ObservableList<Course> courses = FXCollections.observableArrayList(courseService.getAllCourses());
        availableCoursesTable.setItems(courses);
    }

    @FXML
    private void handleAddCourseButton() {
        String courseId = courseIdField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String creditHoursText = creditHoursField.getText().trim();
        if (courseId.isEmpty() || courseName.isEmpty() || creditHoursText.isEmpty()) {
            messageLabel.setText("Please fill in all course details.");
            return;
        }
        try {
            int creditHours = Integer.parseInt(creditHoursText);
            
            // Check if course ID already exists
            if (courseService.getCourseById(courseId).isPresent()) {
                messageLabel.setText("Course ID already exists. Please use a different ID.");
                return;
            }
            Course newCourse = new Course(courseId, courseName, creditHours);
            courseService.addCourse(newCourse);
            
            messageLabel.setText("Course added successfully!");
            
            // Clear fields
            courseIdField.clear();
            courseNameField.clear();
            creditHoursField.clear();

            // Refresh the table
            refreshCourseTable();
        } catch (NumberFormatException e) {
            messageLabel.setText("Credit hours must be a valid number.");
        }
    }

    @FXML
    private void handleRegisterButton() {
        ObservableList<Course> selectedCourses = availableCoursesTable.getSelectionModel().getSelectedItems();

        if (selectedCourses.isEmpty()) {
            messageLabel.setText("Please select at least one course to register.");
            return;
        }

        if (currentStudent == null) {
            messageLabel.setText("Error: No student logged in.");
            return;
        }

        for (Course course : selectedCourses) {
            if (!currentStudent.getRegisteredCourses().contains(course)) {
                currentStudent.addCourse(course);
            } else {
                System.out.println("Student already registered for: " + course.getName());
            }
        }
        studentService.updateStudent(currentStudent); // Save changes to file
        messageLabel.setText("Courses registered successfully!");

        // Optionally, show a confirmation
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Selected courses have been registered.");
        alert.showAndWait();
    }

    @FXML
    private void handleBackButton() {
        try {
            App.showDashboardPage();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error navigating back to dashboard.");
        }
    }
}
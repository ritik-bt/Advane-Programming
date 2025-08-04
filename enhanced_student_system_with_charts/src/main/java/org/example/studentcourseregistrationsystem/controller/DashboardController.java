package org.example.studentcourseregistrationsystem.controller;

import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.model.User;
import org.example.studentcourseregistrationsystem.model.Course;
import org.example.studentcourseregistrationsystem.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.stream.Collectors;
public class DashboardController {

    @FXML
    private Label welcomeMessageLabel;
    @FXML
    private Label studentNameLabel;
    @FXML
    private Label studentPhoneLabel;
    @FXML
    private Label registeredCoursesLabel;
    @FXML
    private Button registerCourseButton;
    @FXML
    private Button viewCoursesButton;
    @FXML
    private Button studentProfileButton;
    private StudentService studentService = new StudentService();
    @FXML
    public void initialize() {
        User loggedInUser = LoginController.loggedInUser;
        if (loggedInUser != null) {
            if ("Student".equals(loggedInUser.getRole())) {
                studentService.getStudentByUsername(loggedInUser.getUsername()).ifPresent(student -> {
                    welcomeMessageLabel.setText("Welcome, " + student.getName() + "!");
                    studentNameLabel.setText(student.getName());
                    studentPhoneLabel.setText(student.getPhoneNumber());
                    
                    // Display registered courses
                    if (student.getRegisteredCourses().isEmpty()) {
                        registeredCoursesLabel.setText("No courses registered");
                    } else {
                        registeredCoursesLabel.setText(student.getRegisteredCourses().size() + " courses registered");
                    }
                });
            } else if ("Teacher".equals(loggedInUser.getRole())) {
                welcomeMessageLabel.setText("Welcome, Teacher " + loggedInUser.getUsername() + "!");
                // Hide student-specific buttons for teachers
                registerCourseButton.setVisible(false);
                viewCoursesButton.setVisible(false);
                studentProfileButton.setVisible(false);
            }
        } else {
            welcomeMessageLabel.setText("Welcome!");
        }
    }

    @FXML
    private void handleProfileButton() {
        try {
            App.showProfilePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegisterCourseButton() {
        try {
            App.showRegisterCoursePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewCoursesButton() {
        try {
            App.showViewCoursesPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChartsButton() {
        try {
            App.showChartsPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutButton() {
        try {
            LoginController.loggedInUser = null; // Clear logged-in user on logout
            App.showLoginPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


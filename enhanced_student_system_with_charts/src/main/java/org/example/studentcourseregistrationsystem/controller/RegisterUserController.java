package org.example.studentcourseregistrationsystem.controller;

import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.model.User;
import org.example.studentcourseregistrationsystem.service.StudentService;
import org.example.studentcourseregistrationsystem.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RegisterUserController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private Label studentDetailsLabel;
    @FXML
    private VBox studentDetailsVBox;
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField programField;
    @FXML
    private TextField semesterField;
    @FXML
    private Label messageLabel;

    private UserService userService = new UserService();
    private StudentService studentService = new StudentService();

    @FXML
    public void initialize() {
        roleChoiceBox.getItems().addAll("Student", "Teacher");
        roleChoiceBox.setValue("Student");
        
        // Show/hide student details based on role selection
        roleChoiceBox.setOnAction(e -> {
            boolean isStudent = "Student".equals(roleChoiceBox.getValue());
            studentDetailsLabel.setVisible(isStudent);
            studentDetailsVBox.setVisible(isStudent);
        });
        
        // Initially show student details
        studentDetailsLabel.setVisible(true);
        studentDetailsVBox.setVisible(true);
    }

    @FXML
    private void handleRegisterButton() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            messageLabel.setText("Please fill in all required fields.");
            return;
        }

        // Check if username already exists
        if (userService.userExists(username)) {
            messageLabel.setText("Username already exists. Please choose a different one.");
            return;
        }

        if ("Student".equals(role)) {
            String studentId = studentIdField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            String program = programField.getText().trim();
            String semester = semesterField.getText().trim();

            if (studentId.isEmpty() || name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || program.isEmpty() || semester.isEmpty()) {
                messageLabel.setText("Please fill in all student details.");
                return;
            }

            // Create and save student
            Student student = new Student(studentId, name, email, phoneNumber, program, semester, username, password);
            studentService.addStudent(student);
        }

        // Create and save user
        User user = new User(username, password, role);
        userService.addUser(user);

        messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        messageLabel.setText("Registration successful! You can now login.");
        
        // Clear fields
        clearFields();
    }

    @FXML
    private void handleBackButton() {
        try {
            App.showLoginPage();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error navigating back to login.");
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        studentIdField.clear();
        nameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        programField.clear();
        semesterField.clear();
        roleChoiceBox.setValue("Student");
    }
}


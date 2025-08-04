package org.example.studentcourseregistrationsystem.controller;

import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.model.User;
import org.example.studentcourseregistrationsystem.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
public class StudentProfileController {
    @FXML
    private Label studentIdLabel;
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
    private final StudentService studentService = new StudentService();
    private Student currentStudent;
    @FXML
    public void initialize() {
        User loggedInUser = LoginController.loggedInUser;
        if (loggedInUser != null && "Student".equals(loggedInUser.getRole())) {
            studentService.getStudentByUsername(loggedInUser.getUsername()).ifPresent(student -> {
                this.currentStudent = student;
                studentIdLabel.setText(currentStudent.getStudentId());
                nameField.setText(currentStudent.getName());
                emailField.setText(currentStudent.getEmail());
                phoneNumberField.setText(currentStudent.getPhoneNumber());
                programField.setText(currentStudent.getProgram());
                semesterField.setText(currentStudent.getSemester());
            });
        } else {
            messageLabel.setText("No student data available or user is not a student.");
        }
    }
    @FXML
    private void handleSaveButton() {
        if (currentStudent != null) {
            currentStudent.setName(nameField.getText());
            currentStudent.setEmail(emailField.getText());
            currentStudent.setPhoneNumber(phoneNumberField.getText());
            currentStudent.setProgram(programField.getText());
            currentStudent.setSemester(semesterField.getText());

            studentService.updateStudent(currentStudent);
            messageLabel.setText("Profile updated successfully!");
        } else {
            messageLabel.setText("No student data to save.");
        }
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


package org.example.studentcourseregistrationsystem.controller;

import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.model.Course;
import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.model.User;
import org.example.studentcourseregistrationsystem.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class ViewCoursesController {

    @FXML
    private TableView<Course> registeredCoursesTable;
    @FXML
    private Label messageLabel;

    private final StudentService studentService = new StudentService();
    private Student currentStudent;

    @FXML
    public void initialize() {
        // Get the current logged-in student
        User loggedInUser = LoginController.loggedInUser;
        if (loggedInUser != null && "Student".equals(loggedInUser.getRole())) {
            studentService.getStudentByUsername(loggedInUser.getUsername()).ifPresent(student -> {
                this.currentStudent = student;
                refreshTable();
            });
        } else {
            messageLabel.setText("No student logged in or user is not a student.");
        }
    }

    private void refreshTable() {
        if (currentStudent != null) {
            ObservableList<Course> courses = FXCollections.observableArrayList(currentStudent.getRegisteredCourses());
            registeredCoursesTable.setItems(courses);
        }
    }

    @FXML
    private void handleDeleteButton() {
        Course selectedCourse = registeredCoursesTable.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            messageLabel.setText("Please select a course to delete.");
            return;
        }

        if (currentStudent == null) {
            messageLabel.setText("Error: No student logged in.");
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Course");
        alert.setContentText("Are you sure you want to delete " + selectedCourse.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentStudent.removeCourse(selectedCourse);
            studentService.updateStudent(currentStudent); // Save changes to file
            refreshTable(); // Update the TableView
            messageLabel.setText(selectedCourse.getName() + " deleted successfully.");
        } else {
            messageLabel.setText("Deletion cancelled.");
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
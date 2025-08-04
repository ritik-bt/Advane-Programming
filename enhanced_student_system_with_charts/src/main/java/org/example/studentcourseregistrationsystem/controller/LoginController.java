package org.example.studentcourseregistrationsystem.controller;
import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.model.User;
import org.example.studentcourseregistrationsystem.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;
    private final UserService userService = new UserService();
    public static User loggedInUser; // Static field to hold the logged-in user
    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User authenticatedUser = userService.authenticate(username, password);
        if (authenticatedUser != null) {
            loggedInUser = authenticatedUser; // Store the logged-in user
            try { App.showDashboardPage();
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Error loading dashboard.");
            }
        } else { messageLabel.setText("Invalid username or password.");
        }
    }
    @FXML
    private void handleRegisterButton() {
        try { App.showRegisterUserPage();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading registration page.");
        }
    }
}


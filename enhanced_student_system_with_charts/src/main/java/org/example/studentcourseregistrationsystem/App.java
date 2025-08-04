package org.example.studentcourseregistrationsystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Optional;
public class App extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Student Course Registration System");
        // Handle window close request (as per Phase 9.3 of the main outline)
        primaryStage.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent immediate close
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Application");
            alert.setHeaderText("Confirm Exit");
            alert.setContentText("Are you sure you want to exit the application?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Perform any necessary cleanup or data saving before exiting
                // For example, ensure all data is saved to files (if you have a global instance or can access it)
                Platform.exit();
            }
        });
        showLoginPage(); // Start with the login page
    }
    public static void showLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showDashboardPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("dashboard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showProfilePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("student-profile-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showRegisterCoursePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register-course-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showViewCoursesPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view-courses-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showChartsPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("charts-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showRegisterUserPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register-user-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}


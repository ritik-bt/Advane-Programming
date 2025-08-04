module org.example.studentcourseregistrationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base; // Added for PropertyValueFactory

    opens org.example.studentcourseregistrationsystem to javafx.fxml;
    opens org.example.studentcourseregistrationsystem.model to javafx.base; // Added for PropertyValueFactory
    exports org.example.studentcourseregistrationsystem;
    exports org.example.studentcourseregistrationsystem.controller;
    opens org.example.studentcourseregistrationsystem.controller to javafx.fxml;
}


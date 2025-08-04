package org.example.studentcourseregistrationsystem.util;

import org.example.studentcourseregistrationsystem.model.Student;
import org.example.studentcourseregistrationsystem.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.List;

public class ChartDataPrepare {

    public static ObservableList<XYChart.Data<String, Number>> getStudentCourseCountData() {
        StudentService studentService = new StudentService(); // Or inject this
        List<Student> students = studentService.getAllStudents();

        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        for (Student student : students) {
            data.add(new XYChart.Data<>(student.getName(), student.getRegisteredCourses().size()));
        }
        return data;
    }
}
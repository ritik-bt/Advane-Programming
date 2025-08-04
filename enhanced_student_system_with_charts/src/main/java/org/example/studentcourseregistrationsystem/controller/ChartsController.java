package org.example.studentcourseregistrationsystem.controller;

import org.example.studentcourseregistrationsystem.App;
import org.example.studentcourseregistrationsystem.util.ChartDataPrepare;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;

public class ChartsController {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        loadChartData();
    }

    private void loadChartData() {
        try {
            // Get data from ChartDataPrepare
            ObservableList<XYChart.Data<String, Number>> data = ChartDataPrepare.getStudentCourseCountData();
            
            if (data.isEmpty()) {
                messageLabel.setText("No student data available for charts.");
                return;
            }

            // Populate Bar Chart
            XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
            barSeries.setName("Courses Registered");
            barSeries.setData(data);
            barChart.getData().clear();
            barChart.getData().add(barSeries);

            // Populate Pie Chart
            ObservableList<PieChart.Data> pieData = pieChart.getData();
            pieData.clear();
            
            for (XYChart.Data<String, Number> item : data) {
                String studentName = item.getXValue();
                Number courseCount = item.getYValue();
                
                // Only add to pie chart if student has registered courses
                if (courseCount.intValue() > 0) {
                    pieData.add(new PieChart.Data(studentName + " (" + courseCount + " courses)", courseCount.doubleValue()));
                }
            }
            
            if (pieData.isEmpty()) {
                messageLabel.setText("No students have registered for courses yet.");
            } else {
                messageLabel.setText("Charts updated successfully!");
            }
            
        } catch (Exception e) {
            messageLabel.setText("Error loading chart data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefreshButton() {
        loadChartData();
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


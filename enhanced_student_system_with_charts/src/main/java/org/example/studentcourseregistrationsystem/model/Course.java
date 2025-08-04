package org.example.studentcourseregistrationsystem.model;

import java.util.Objects;

public class Course {
    private String courseId;
    private String name;
    private int creditHours;

    public Course(String courseId, String name, int creditHours) {
        this.courseId = courseId;
        this.name = name;
        this.creditHours = creditHours;
    }

    // Getters and Setters
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=\'" + courseId + '\'' +
                ", name=\'" + name + '\'' +
                ", creditHours=" + creditHours +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}
package org.example.studentcourseregistrationsystem.service;
import org.example.studentcourseregistrationsystem.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class UserService {
    private static final String USERS_FILE = "users.txt";
    private Map<String, User> users = new HashMap<>();
    public UserService() {
        loadUsersFromFile();
    }
    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) { // username, password, role
                    users.put(parts[0], new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
            // Create the file if it doesn't exist and add a default user
            try (FileWriter writer = new FileWriter(USERS_FILE)) {
                writer.write("student,password,Student\n"); // Default student user
                writer.write("teacher,password,Teacher\n"); // Default teacher user
                users.put("student", new User("student", "password", "Student"));
                users.put("teacher", new User("teacher", "password", "Teacher"));
            } catch (IOException ex) {
                System.err.println("Error creating users file: " + ex.getMessage());
            }
        }
    }
    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        saveUsersToFile();
    }
    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole() + "\n");
            }
        } catch (IOException e) { System.err.println("Error saving users to file: " + e.getMessage());
        }
    }
    public User getUserByUsername(String username) {
        return users.get(username);
    }
}


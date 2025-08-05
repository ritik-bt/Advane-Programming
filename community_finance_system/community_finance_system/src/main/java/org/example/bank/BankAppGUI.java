package org.example.bank;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class BankAppGUI extends Application {

    private final Map<String, BankAccount> accounts = new HashMap<>();
    private BankAccount loggedInAccount;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Create demo accounts
        BankAccount alice = new BankAccount("001", "Alice", 1000.0);
        BankAccount bob = new BankAccount("002", "Bob", 500.0);
        accounts.put("Alice", alice);
        accounts.put("Bob", bob);

        showLogin();
    }

    private void showLogin() {
        Label welcomeLabel = new Label("Banking App Login");
        welcomeLabel.setFont(Font.font(20));

        ComboBox<String> accountBox = new ComboBox<>();
        accountBox.getItems().addAll(accounts.keySet());
        accountBox.setValue("Alice");

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            String selected = accountBox.getValue();
            loggedInAccount = accounts.get(selected);
            showDashboard();
        });

        VBox loginLayout = new VBox(15, welcomeLabel, accountBox, loginBtn);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(40));

        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void showDashboard() {
        Label title = new Label("Account Dashboard");
        title.setFont(Font.font(20));

        Label nameLabel = new Label("Name: " + loggedInAccount.getAccountHolder());
        Label accNumLabel = new Label("Account No: " + loggedInAccount.getAccountNumber());
        Label balanceLabel = new Label("Balance: $" + String.format("%.2f", loggedInAccount.getBalance()));

        VBox infoBox = new VBox(10, nameLabel, accNumLabel, balanceLabel);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");
        infoBox.setAlignment(Pos.CENTER_LEFT);

        // Buttons
        Button addBtn = new Button("Add Balance");
        Button withdrawBtn = new Button("Withdraw");
        Button transferBtn = new Button("Transfer");
        Button interestBtn = new Button("Add Interest");
        Button logoutBtn = new Button("Logout");

        styleActionButton(addBtn, "#28a745");
        styleActionButton(withdrawBtn, "#dc3545");
        styleActionButton(transferBtn, "#17a2b8");
        styleActionButton(interestBtn, "#6610f2");
        styleActionButton(logoutBtn, "#6c757d");

        addBtn.setOnAction(e -> showTransactionScene("Add"));
        withdrawBtn.setOnAction(e -> showTransactionScene("Withdraw"));
        transferBtn.setOnAction(e -> showTransferScene());
        logoutBtn.setOnAction(e -> showLogin());

        interestBtn.setOnAction(e -> {
            double interest = loggedInAccount.calculateInterest();
            loggedInAccount.applyInterest();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Interest Applied");
            alert.setHeaderText(null);
            alert.setContentText("Interest of $" + String.format("%.2f", interest) +
                    " added.\nNew Balance: $" + String.format("%.2f", loggedInAccount.getBalance()));
            alert.showAndWait();
            showDashboard();
        });

        HBox tabs = new HBox(10, addBtn, withdrawBtn, transferBtn, interestBtn, logoutBtn);
        tabs.setAlignment(Pos.CENTER);
        tabs.setPadding(new Insets(15));

        VBox dashboardLayout = new VBox(20, title, infoBox, tabs);
        dashboardLayout.setPadding(new Insets(30));
        dashboardLayout.setAlignment(Pos.TOP_CENTER);

        Scene dashboardScene = new Scene(dashboardLayout, 500, 350);
        primaryStage.setScene(dashboardScene);
    }

    private void showTransactionScene(String type) {
        Label label = new Label(type + " Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button confirmBtn = new Button(type);
        styleActionButton(confirmBtn, type.equals("Add") ? "#28a745" : "#dc3545");

        Button backBtn = new Button("Back");
        styleActionButton(backBtn, "#6c757d");

        Label messageLabel = new Label();

        confirmBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());

                if (type.equals("Add")) {
                    loggedInAccount.deposit(amount);
                } else {
                    loggedInAccount.withdraw(amount);
                }

                messageLabel.setText(type + " successful. New balance: $" +
                        String.format("%.2f", loggedInAccount.getBalance()));
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid number.");
            } catch (InvalidAmountException | InsufficientFundsException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showDashboard());

        VBox layout = new VBox(15, label, amountField, confirmBtn, backBtn, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showTransferScene() {
        Label label = new Label("Transfer Amount:");
        TextField amountField = new TextField();

        ComboBox<String> recipientBox = new ComboBox<>();
        for (String name : accounts.keySet()) {
            if (!name.equals(loggedInAccount.getAccountHolder())) {
                recipientBox.getItems().add(name);
            }
        }

        recipientBox.setPromptText("Select recipient");

        Button transferBtn = new Button("Transfer");
        Button backBtn = new Button("Back");
        styleActionButton(transferBtn, "#17a2b8");
        styleActionButton(backBtn, "#6c757d");

        Label messageLabel = new Label();

        transferBtn.setOnAction(e -> {
            try {
                String recipientName = recipientBox.getValue();
                if (recipientName == null) {
                    messageLabel.setText("Please select a recipient.");
                    return;
                }

                double amount = Double.parseDouble(amountField.getText());
                BankAccount recipient = accounts.get(recipientName);

                loggedInAccount.transfer(recipient, amount);
                messageLabel.setText("Transferred $" + amount + " to " + recipientName);
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid number.");
            } catch (InvalidAmountException | InsufficientFundsException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showDashboard());

        VBox layout = new VBox(15, label, amountField, recipientBox, transferBtn, backBtn, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 450, 320);
        primaryStage.setScene(scene);
    }

    private void styleActionButton(Button button, String color) {
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
        button.setFont(Font.font(14));
        button.setPrefWidth(120);
    }
}

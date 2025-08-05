package com.fintech.finance;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceApplication extends Application {

    private final Map<String, FinancialAccount> customerAccounts = new HashMap<>();
    private final Map<String, List<String>> transactionHistory = new HashMap<>();
    private FinancialAccount activeAccount;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Initialize demo accounts
        FinancialAccount johnAccount = new FinancialAccount("CFS001", "John Smith", 2500.0);
        FinancialAccount sarahAccount = new FinancialAccount("CFS002", "Sarah Johnson", 1800.0);
        FinancialAccount mikeAccount = new FinancialAccount("CFS003", "Mike Davis", 750.0);
        
        customerAccounts.put("John Smith", johnAccount);
        customerAccounts.put("Sarah Johnson", sarahAccount);
        customerAccounts.put("Mike Davis", mikeAccount);

        // Initialize transaction history
        transactionHistory.put("John Smith", new ArrayList<>());
        transactionHistory.put("Sarah Johnson", new ArrayList<>());
        transactionHistory.put("Mike Davis", new ArrayList<>());

        displayLoginScreen();
    }

    private void displayLoginScreen() {
        // Create main container with BorderPane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);");

        // Header section
        VBox headerSection = new VBox(10);
        headerSection.setAlignment(Pos.CENTER);
        headerSection.setPadding(new Insets(30, 20, 20, 20));

        Label titleLabel = new Label("Community Finance System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Secure Financial Management Platform");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitleLabel.setTextFill(Color.LIGHTGRAY);

        headerSection.getChildren().addAll(titleLabel, subtitleLabel);

        // Login form section
        VBox loginForm = new VBox(20);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(40));
        loginForm.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        loginForm.setMaxWidth(400);

        Label instructionLabel = new Label("Select Your Account");
        instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        instructionLabel.setTextFill(Color.DARKSLATEGRAY);

        ComboBox<String> accountSelector = new ComboBox<>();
        accountSelector.getItems().addAll(customerAccounts.keySet());
        accountSelector.setValue("John Smith");
        accountSelector.setPrefWidth(300);
        accountSelector.setStyle("-fx-font-size: 14px;");

        Button accessButton = new Button("Access Account");
        styleButton(accessButton, "#27ae60", "#2ecc71");
        accessButton.setPrefWidth(200);

        accessButton.setOnAction(e -> {
            String selectedCustomer = accountSelector.getValue();
            activeAccount = customerAccounts.get(selectedCustomer);
            displayMainDashboard();
        });

        loginForm.getChildren().addAll(instructionLabel, accountSelector, accessButton);

        mainLayout.setTop(headerSection);
        mainLayout.setCenter(loginForm);

        Scene loginScene = new Scene(mainLayout, 600, 500);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Community Finance System - Login");
        primaryStage.show();
    }

    private void displayMainDashboard() {
        BorderPane dashboardLayout = new BorderPane();
        dashboardLayout.setStyle("-fx-background-color: #ecf0f1;");

        // Top section - Account info
        VBox accountInfoSection = createAccountInfoSection();
        dashboardLayout.setTop(accountInfoSection);

        // Center section - Action buttons
        GridPane actionGrid = createActionButtonGrid();
        dashboardLayout.setCenter(actionGrid);

        // Bottom section - Quick stats
        HBox statsSection = createStatsSection();
        dashboardLayout.setBottom(statsSection);

        Scene dashboardScene = new Scene(dashboardLayout, 700, 600);
        primaryStage.setScene(dashboardScene);
        primaryStage.setTitle("Community Finance System - Dashboard");
    }

    private VBox createAccountInfoSection() {
        VBox infoSection = new VBox(15);
        infoSection.setPadding(new Insets(25));
        infoSection.setStyle("-fx-background-color: #34495e;");

        Label welcomeLabel = new Label("Welcome, " + activeAccount.getCustomerName());
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.WHITE);

        HBox accountDetails = new HBox(30);
        accountDetails.setAlignment(Pos.CENTER_LEFT);

        Label accountIdLabel = new Label("Account ID: " + activeAccount.getAccountId());
        accountIdLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        accountIdLabel.setTextFill(Color.LIGHTGRAY);

        Label balanceLabel = new Label("Current Balance: $" + String.format("%.2f", activeAccount.getCurrentBalance()));
        balanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        balanceLabel.setTextFill(Color.LIGHTGREEN);

        accountDetails.getChildren().addAll(accountIdLabel, balanceLabel);
        infoSection.getChildren().addAll(welcomeLabel, accountDetails);

        return infoSection;
    }

    private GridPane createActionButtonGrid() {
        GridPane actionGrid = new GridPane();
        actionGrid.setAlignment(Pos.CENTER);
        actionGrid.setHgap(20);
        actionGrid.setVgap(20);
        actionGrid.setPadding(new Insets(40));

        Button depositButton = new Button("Deposit Funds");
        Button withdrawButton = new Button("Withdraw Funds");
        Button transferButton = new Button("Transfer Money");
        Button interestButton = new Button("Apply Interest");
        Button historyButton = new Button("View History");
        Button logoutButton = new Button("Logout");

        styleButton(depositButton, "#27ae60", "#2ecc71");
        styleButton(withdrawButton, "#e74c3c", "#c0392b");
        styleButton(transferButton, "#3498db", "#2980b9");
        styleButton(interestButton, "#9b59b6", "#8e44ad");
        styleButton(historyButton, "#f39c12", "#e67e22");
        styleButton(logoutButton, "#95a5a6", "#7f8c8d");

        depositButton.setOnAction(e -> displayTransactionScreen("Deposit"));
        withdrawButton.setOnAction(e -> displayTransactionScreen("Withdraw"));
        transferButton.setOnAction(e -> displayTransferScreen());
        historyButton.setOnAction(e -> displayTransactionHistory());
        logoutButton.setOnAction(e -> displayLoginScreen());

        interestButton.setOnAction(e -> {
            double interestAmount = activeAccount.computeInterest();
            activeAccount.creditInterest();
            addTransactionRecord("Interest credited: $" + String.format("%.2f", interestAmount));
            
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Interest Applied");
            successAlert.setHeaderText("Interest Successfully Credited");
            successAlert.setContentText("Interest of $" + String.format("%.2f", interestAmount) +
                    " has been added to your account.\nNew Balance: $" + String.format("%.2f", activeAccount.getCurrentBalance()));
            successAlert.showAndWait();
            displayMainDashboard();
        });

        actionGrid.add(depositButton, 0, 0);
        actionGrid.add(withdrawButton, 1, 0);
        actionGrid.add(transferButton, 0, 1);
        actionGrid.add(interestButton, 1, 1);
        actionGrid.add(historyButton, 0, 2);
        actionGrid.add(logoutButton, 1, 2);

        return actionGrid;
    }

    private HBox createStatsSection() {
        HBox statsSection = new HBox(20);
        statsSection.setAlignment(Pos.CENTER);
        statsSection.setPadding(new Insets(20));
        statsSection.setStyle("-fx-background-color: #bdc3c7;");

        Label statsLabel = new Label("Quick Stats");
        statsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label interestLabel = new Label("Potential Interest: $" + String.format("%.2f", activeAccount.computeInterest()));
        interestLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        statsSection.getChildren().addAll(statsLabel, new Separator(), interestLabel);
        return statsSection;
    }

    private void displayTransactionScreen(String transactionType) {
        BorderPane transactionLayout = new BorderPane();
        transactionLayout.setStyle("-fx-background-color: #ecf0f1;");

        VBox formSection = new VBox(20);
        formSection.setAlignment(Pos.CENTER);
        formSection.setPadding(new Insets(50));
        formSection.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        formSection.setMaxWidth(400);

        Label titleLabel = new Label(transactionType + " Funds");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setPrefWidth(300);
        amountField.setStyle("-fx-font-size: 16px;");

        Button confirmButton = new Button("Confirm " + transactionType);
        Button cancelButton = new Button("Cancel");

        styleButton(confirmButton, transactionType.equals("Deposit") ? "#27ae60" : "#e74c3c", 
                   transactionType.equals("Deposit") ? "#2ecc71" : "#c0392b");
        styleButton(cancelButton, "#95a5a6", "#7f8c8d");

        Label messageLabel = new Label();
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        confirmButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());

                if (transactionType.equals("Deposit")) {
                    activeAccount.addFunds(amount);
                    addTransactionRecord("Deposited: $" + String.format("%.2f", amount));
                } else {
                    activeAccount.removeFunds(amount);
                    addTransactionRecord("Withdrew: $" + String.format("%.2f", amount));
                }

                messageLabel.setText(transactionType + " successful! New balance: $" +
                        String.format("%.2f", activeAccount.getCurrentBalance()));
                messageLabel.setTextFill(Color.GREEN);
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid number.");
                messageLabel.setTextFill(Color.RED);
            } catch (InvalidTransactionException | InsufficientBalanceException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
                messageLabel.setTextFill(Color.RED);
            }
        });

        cancelButton.setOnAction(e -> displayMainDashboard());

        formSection.getChildren().addAll(titleLabel, amountField, confirmButton, cancelButton, messageLabel);
        transactionLayout.setCenter(formSection);

        Scene transactionScene = new Scene(transactionLayout, 600, 500);
        primaryStage.setScene(transactionScene);
    }

    private void displayTransferScreen() {
        BorderPane transferLayout = new BorderPane();
        transferLayout.setStyle("-fx-background-color: #ecf0f1;");

        VBox formSection = new VBox(20);
        formSection.setAlignment(Pos.CENTER);
        formSection.setPadding(new Insets(50));
        formSection.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        formSection.setMaxWidth(450);

        Label titleLabel = new Label("Transfer Funds");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        TextField amountField = new TextField();
        amountField.setPromptText("Enter transfer amount");
        amountField.setPrefWidth(300);

        ComboBox<String> recipientBox = new ComboBox<>();
        for (String customerName : customerAccounts.keySet()) {
            if (!customerName.equals(activeAccount.getCustomerName())) {
                recipientBox.getItems().add(customerName);
            }
        }
        recipientBox.setPromptText("Select recipient");
        recipientBox.setPrefWidth(300);

        Button transferButton = new Button("Transfer");
        Button cancelButton = new Button("Cancel");

        styleButton(transferButton, "#3498db", "#2980b9");
        styleButton(cancelButton, "#95a5a6", "#7f8c8d");

        Label messageLabel = new Label();

        transferButton.setOnAction(e -> {
            try {
                String recipientName = recipientBox.getValue();
                if (recipientName == null) {
                    messageLabel.setText("Please select a recipient.");
                    messageLabel.setTextFill(Color.RED);
                    return;
                }

                double amount = Double.parseDouble(amountField.getText());
                FinancialAccount recipient = customerAccounts.get(recipientName);

                activeAccount.transferFunds(recipient, amount);
                addTransactionRecord("Transferred $" + String.format("%.2f", amount) + " to " + recipientName);
                
                messageLabel.setText("Successfully transferred $" + String.format("%.2f", amount) + " to " + recipientName);
                messageLabel.setTextFill(Color.GREEN);
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid amount.");
                messageLabel.setTextFill(Color.RED);
            } catch (InvalidTransactionException | InsufficientBalanceException ex) {
                messageLabel.setText("Error: " + ex.getMessage());
                messageLabel.setTextFill(Color.RED);
            }
        });

        cancelButton.setOnAction(e -> displayMainDashboard());

        formSection.getChildren().addAll(titleLabel, amountField, recipientBox, transferButton, cancelButton, messageLabel);
        transferLayout.setCenter(formSection);

        Scene transferScene = new Scene(transferLayout, 600, 550);
        primaryStage.setScene(transferScene);
    }

    private void displayTransactionHistory() {
        BorderPane historyLayout = new BorderPane();
        historyLayout.setStyle("-fx-background-color: #ecf0f1;");

        VBox historySection = new VBox(15);
        historySection.setPadding(new Insets(30));

        Label titleLabel = new Label("Transaction History");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        ListView<String> historyList = new ListView<>();
        List<String> userHistory = transactionHistory.get(activeAccount.getCustomerName());
        historyList.getItems().addAll(userHistory);
        historyList.setPrefHeight(300);

        if (userHistory.isEmpty()) {
            historyList.getItems().add("No transactions yet.");
        }

        Button backButton = new Button("Back to Dashboard");
        styleButton(backButton, "#95a5a6", "#7f8c8d");
        backButton.setOnAction(e -> displayMainDashboard());

        historySection.getChildren().addAll(titleLabel, historyList, backButton);
        historyLayout.setCenter(historySection);

        Scene historyScene = new Scene(historyLayout, 600, 500);
        primaryStage.setScene(historyScene);
    }

    private void addTransactionRecord(String transaction) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String record = "[" + timestamp + "] " + transaction;
        transactionHistory.get(activeAccount.getCustomerName()).add(record);
    }

    private void styleButton(Button button, String baseColor, String hoverColor) {
        button.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        button.setPrefWidth(150);
        button.setPrefHeight(40);
        
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + baseColor + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"));
    }
}


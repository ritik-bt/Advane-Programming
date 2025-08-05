module com.fintech.finance {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fintech.finance to javafx.fxml;
    exports com.fintech.finance;
}
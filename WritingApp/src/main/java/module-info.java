module main.writingapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens application to javafx.fxml;
    exports application;
}
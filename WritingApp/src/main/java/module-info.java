module main.writingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.prefs;
    requires org.kordamp.ikonli.javafx;


    opens application to javafx.fxml;
    exports application;
}
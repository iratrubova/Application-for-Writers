package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button settingsCloseWindowButton;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Pane settingsDraggedPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsCloseWindowButton.setText("\u2715");

        settingsDraggedPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        settingsDraggedPane.setOnMouseDragged(event -> {
            Stage stage = (Stage) settingsDraggedPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void settingsCloseApp() {
        Stage stage = (Stage) settingsCloseWindowButton.getScene().getWindow();
        stage.close();
        }





}


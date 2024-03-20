package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;




public class FirstWindowController implements Initializable {

    @FXML
    private Button plusButtonManuscript;
    @FXML
    private Button plusButtonProject;
    @FXML
    private Pane manuscriptProjectPane;
    @FXML
    private Pane recentProjectsPane;
    @FXML
    private Button cancelCreatingButton;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Pane topPane;
    @FXML
    private Button newProjectButton;
    @FXML
    private Button openSettingsButton;

    @FXML
    private void openProject(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File documentsDirectory = new File(System.getProperty("user.home"), "Documents");
        if (documentsDirectory.exists() && documentsDirectory.isDirectory()) {
            fileChooser.setInitialDirectory(documentsDirectory);
        }
        fileChooser.setTitle("Open Project File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println("File selected: " + file.getAbsolutePath());
        }
    }

    @FXML
    private void cancelCreating() {
        ((Stage) cancelCreatingButton.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Show create project files pane by default
        showCreateProjectFiles();
        // Set newProjectButton to be focused by default after the scene is shown
        Platform.runLater(() -> {
            newProjectButton.requestFocus();
        });

        manuscriptProjectPane.setVisible(true);
        recentProjectsPane.setVisible(false);
        topPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topPane.setOnMouseDragged(event -> {
            Stage stage = (Stage) topPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

    }

    @FXML
    private void showCreateProjectFiles(){
        manuscriptProjectPane.setVisible(true);
        recentProjectsPane.setVisible(false);
    }

    @FXML
    private void showRecentProjectFiles(){
        recentProjectsPane.setVisible(true);
        manuscriptProjectPane.setVisible(false);

    }


    @FXML
    private void openCreateManuscriptScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/create-manuscript-scene.fxml"));
            Scene sceneManuscript = new Scene(loader.load());
            String cssFilePath = getClass().getResource("/styles/create-manuscript-scene.css").toExternalForm();
            sceneManuscript.getStylesheets().add(cssFilePath);
            Stage stageManuscript = (Stage) plusButtonManuscript.getScene().getWindow();
            stageManuscript.setScene(sceneManuscript);
            stageManuscript.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCreateProjectScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/create-project-scene.fxml"));
            Scene sceneProject = new Scene(loader.load());
            String cssFilePath = getClass().getResource("/styles/create-project-scene.css").toExternalForm();
            sceneProject.getStylesheets().add(cssFilePath);
            Stage stageProject = (Stage) plusButtonProject.getScene().getWindow();
            stageProject.setScene(sceneProject);
            stageProject.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openWebsite(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/iratrubova"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openSettingsWindow(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/settings.fxml"));
            Scene sceneSettings = new Scene(loader.load());
            String cssFilePath = getClass().getResource("/styles/settings.css").toExternalForm();
            sceneSettings.getStylesheets().add(cssFilePath);
            Stage stageSettings = (Stage) openSettingsButton.getScene().getWindow();
            stageSettings.setScene(sceneSettings);
            stageSettings.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}










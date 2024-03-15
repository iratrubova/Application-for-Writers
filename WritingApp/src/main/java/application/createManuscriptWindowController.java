package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class createManuscriptWindowController implements Initializable {

    @FXML
    private TextField manuscriptLocationTextField;
    @FXML
    private TextField manuscriptNameField;
    @FXML
    private TextField manuscriptAuthorField;

    @FXML
    private Button manuscriptCloseWindowButton;
    @FXML
    private ImageView manuscriptSmallLogo;
    @FXML
    private Pane manuscriptDraggedPane;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button manuscriptBrowseButton;
    @FXML
    private Button manuscriptButtonBack;
    @FXML
    private Button manuscriptCancelButton;
    @FXML
    private Label manuscriptTypeLabel;
    @FXML
    private Button shortStoryButton, novelButton, poemButton, screenplayButton, playButton, comicBookButton;
    @FXML
    private Parent manuscriptLeftSidePane;
    private Map<Button, String> projectTypesMap = new HashMap<>();
    @FXML
    private Button manuscriptCreateButton;
    private String selectedProjectType = null;



    @FXML
    private void manuscriptOnClickBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/first-window.fxml"));
            Scene sceneFirstWindow = new Scene(loader.load());
            Stage stageFirstWindow = (Stage) manuscriptButtonBack.getScene().getWindow();
            stageFirstWindow.setScene(sceneFirstWindow);
            stageFirstWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleLogoClick(MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemRestore = new MenuItem("Restore");
        itemRestore.setOnAction(e -> ((Stage) manuscriptSmallLogo.getScene().getWindow()).setIconified(false));
        ImageView restoreIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/restore.png")));
        restoreIcon.setFitWidth(16);
        restoreIcon.setFitHeight(16);
        itemRestore.setGraphic(restoreIcon);
        MenuItem itemMinimize = new MenuItem("Minimize");
        itemMinimize.setOnAction(e -> ((Stage) manuscriptSmallLogo.getScene().getWindow()).setIconified(true));
        ImageView minimizeIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/minimize_.png")));
        minimizeIcon.setFitWidth(16);
        minimizeIcon.setFitHeight(16);
        itemMinimize.setGraphic(minimizeIcon);
        MenuItem itemMaximize = new MenuItem("Maximize");
        itemMaximize.setOnAction(e -> ((Stage) manuscriptSmallLogo.getScene().getWindow()).setMaximized(false));
        itemMaximize.setDisable(true);
        MenuItem itemClose = new MenuItem("Close");
        itemClose.setOnAction(e -> ((Stage) manuscriptSmallLogo.getScene().getWindow()).close());
        ImageView closeIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/close.png")));
        closeIcon.setFitWidth(16);
        closeIcon.setFitHeight(16);
        itemClose.setGraphic(closeIcon);
        contextMenu.getItems().addAll(itemRestore, itemMinimize, itemMaximize, itemClose);
        contextMenu.show(manuscriptSmallLogo, event.getScreenX(), event.getScreenY());
    }


    @FXML
    private void manuscriptCloseApp() {
        ((Stage) manuscriptCloseWindowButton.getScene().getWindow()).close();
    }


    @FXML
    public void manuscriptCancelCreateClose(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setTitle("Closing");
        alert.setHeaderText("You are about to leave");
        alert.setContentText("Cancel creating a project?");
        Image image = new Image(getClass().getResourceAsStream("/img/alert2.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        imageView.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(imageView);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/create-manuscript-scene.css").toExternalForm()
        );
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 14);
        System.out.println(font);
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Add mappings for each button
        projectTypesMap.put(shortStoryButton, "Short Story");
        projectTypesMap.put(novelButton, "Novel");
        projectTypesMap.put(poemButton, "Poem");
        projectTypesMap.put(screenplayButton, "Screenplay");
        projectTypesMap.put(playButton, "Play");
        projectTypesMap.put(comicBookButton, "Comic Book");
        System.out.println(projectTypesMap);


        // Add focus listeners to each button
        shortStoryButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedProjectType = "Short Story";
            }
        });

        novelButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedProjectType = "Novel";
            }
        });


        manuscriptCloseWindowButton.setText("\u2715");
        manuscriptDraggedPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        manuscriptDraggedPane.setOnMouseDragged(event -> {
            Stage stage = (Stage) manuscriptDraggedPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // Set the button to be focused by default
        shortStoryButton.requestFocus();
        shortStoryButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");

        shortStoryButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Short story manuscript");
            clearFocusedStyle();
            shortStoryButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });

        novelButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Novel manuscript");
            clearFocusedStyle();
            novelButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });

        poemButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Poem manuscript");
            clearFocusedStyle();
            poemButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });

        screenplayButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Screenplay manuscript");
            clearFocusedStyle();
            screenplayButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });

        playButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Play manuscript");
            clearFocusedStyle();
            playButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });

        comicBookButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Comic book manuscript");
            clearFocusedStyle();
            comicBookButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });


        // Simulate click on the first button to set initial label
        shortStoryButton.fire();




        // Add event handler for the "Create" button
//        manuscriptCreateButton.setOnAction(event -> createProject());


    }

    private void clearFocusedStyle() {
        // Remove focused style from all buttons
        shortStoryButton.setStyle("");
        novelButton.setStyle("");
        poemButton.setStyle("");
        screenplayButton.setStyle("");
        playButton.setStyle("");
        comicBookButton.setStyle("");
    }



    @FXML
    private void handleProjectTypeSelection(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        String projectType = projectTypesMap.get(selectedButton);

        if (projectType != null) {
            System.out.println("Selected Project Type: " + projectType);
            clearFields(); // Clear text fields when a project type button is selected
        } else {
            System.out.println("No project type mapped to the clicked button.");
        }
    }

    private void clearFields() {
        manuscriptNameField.clear();
        manuscriptLocationTextField.clear();
        manuscriptAuthorField.clear();
    }




    @FXML
    private void handleCreateButtonClick(ActionEvent event) {
        if (selectedProjectType != null) {
            String projectName = manuscriptNameField.getText();
            String projectLocation = manuscriptLocationTextField.getText();
            String projectAuthor = manuscriptAuthorField.getText();

            switch (selectedProjectType) {
                case "Short Story":
                    createShortStoryProject(projectName, projectLocation, projectAuthor);
                    break;
                case "Novel":
                    createNovelProject(projectName, projectLocation, projectAuthor);
                    break;
                // Add cases for other project types
            }
        } else {
            System.out.println("No project type selected.");
        }
    }


    private void createShortStoryProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Short Story Project");
        // Create a short story project
        ShortStory shortStory = new ShortStory(projectName, projectLocation, projectAuthor);
        // Save the short story project
        saveProjectAsTxt(shortStory);
    }

    private void createNovelProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Novel Project");
        // Create a novel project
        Novel novel = new Novel(projectName, projectLocation, projectAuthor);
        // Save the novel project
        saveProjectAsTxt(novel);
    }



    private void saveProjectAsTxt(Project project) {
        String fileName = project.getName() + ".txt";
        String filePath = project.getLocation() + File.separator + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Project Name: " + project.getName());
            writer.newLine();
            writer.write("Project Location: " + project.getLocation());
            writer.newLine();
            writer.write("Project Author: " + project.getAuthor());
            writer.close();
            System.out.println("Data written to the file successfully!");
        } catch (IOException e) {
            System.err.println("Error writing data to the file:");
            e.printStackTrace();
        }
    }





    @FXML
    private void manuscriptOnChooseLocation() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Installation Location");

        // Set the initial directory to the user's home or current label text
        File initialDirectory = new File(manuscriptLocationTextField.getText());
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            directoryChooser.setInitialDirectory(initialDirectory);
        }

        // Show dialog
        File selectedDirectory = directoryChooser.showDialog(null); // Use a reference to your primary stage if available

        // Set the maximum number of characters to display
        final int maxCharacters = 38;

        // Update the TextField with the selected directory path, trimming if necessary
        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();

            // Trim the path if it exceeds maxCharacters
            if (directoryPath.length() > maxCharacters) {
                // Optionally, add an ellipsis to indicate the text has been trimmed
                String trimmedPath = directoryPath.substring(0, maxCharacters - 3) + "...";
                manuscriptLocationTextField.setText(trimmedPath);
            } else {
                manuscriptLocationTextField.setText(directoryPath);
            }

        }
    }
    }
























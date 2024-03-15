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


        // Add mappings for each button
        projectTypesMap.put(shortStoryButton, "Short Story");
        projectTypesMap.put(novelButton, "Novel");
        projectTypesMap.put(poemButton, "Poem");
        projectTypesMap.put(screenplayButton, "Screenplay");
        projectTypesMap.put(playButton, "Play");
        projectTypesMap.put(comicBookButton, "Comic Book");

        // Add event handler for the "Create" button
        manuscriptCreateButton.setOnAction(event -> createProject());


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

    private void createProject() {
        // Get the selected project type
        Button selectedButton = projectTypesMap.entrySet().stream()
                .filter(entry -> entry.getKey().isFocused())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (selectedButton != null) {
            String projectType = projectTypesMap.get(selectedButton);
            String projectName = manuscriptNameField.getText();
            String projectLocation = manuscriptLocationTextField.getText();
            String projectAuthor = manuscriptAuthorField.getText();

            // Perform project creation logic based on the project type
            switch (projectType) {
                case "Short Story":
                    // Create a short story project
                    ShortStory shortStory = new ShortStory(projectName, projectLocation, projectAuthor);
                    // Perform additional actions for short story project creation
                    saveProjectAsTxt(shortStory);
                    break;
                case "Novel":
                    // Create a novel project
                    Novel novel = new Novel(projectName, projectLocation, projectAuthor);
                    // Perform additional actions for novel project creation
                    saveProjectAsTxt(novel);
                    break;
                // Add cases for other project types
            }


            // For example, save the project to a database or display it in a list
        }
    }

    // Method to save the project as a .txt file


    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        // Call a method to get the project information
        Project project = getProjectInformation();

        // Call the saveProjectAsTxt() method with the project information
        saveProjectAsTxt(project);
    }

    private Project getProjectInformation() {
        // Implement logic to retrieve project information from UI fields
        String name = manuscriptNameField.getText();
        String location = manuscriptLocationTextField.getText();
        String author = manuscriptAuthorField.getText();

        // Create and return a new Project object
        return new ShortStory(name, location, author);
    }
    private void saveProjectAsTxt(Project project) {
        String fileName = project.getName() + ".txt";
        String filePath = "path/to/directory/" + fileName; // Update with the actual file path

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Project Name: " + project.getName());
            writer.newLine();
            writer.write("Project Location: " + project.getLocation());
            writer.newLine();
            writer.write("Project Author: " + project.getAuthor());

            // Add additional project details as needed
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
























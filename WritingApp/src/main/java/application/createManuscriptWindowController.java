package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

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
    private Button manuscriptButtonBack;
    @FXML
    private Label manuscriptTypeLabel;
    @FXML
    private Button shortStoryManuscriptButton, novelManuscriptButton, poemManuscriptButton, screenplayManuscriptButton, playManuscriptButton, comicBookManuscriptButton;
    private final Map<Button, String> manuscriptTypesMap = new HashMap<>();
    private String selectedManuscriptType = null;
    private static final String DONT_ASK_AGAIN_KEY = "dontAskAgain";


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
        // Retrieve the Scene object from any node in the scene
        Scene scene = manuscriptSmallLogo.getScene();
        if (scene != null) {
            // Load CSS file for context menu styling
            scene.getStylesheets().add(getClass().getResource("/styles/context_menu.css").toExternalForm());
        }
        contextMenu.show(manuscriptSmallLogo, event.getScreenX(), event.getScreenY());
    }


    @FXML
    private void manuscriptCloseApp() {
        Stage stage = (Stage) manuscriptCloseWindowButton.getScene().getWindow();
        if (!loadDontAskAgainPreference()) {
            showCloseConfirmationDialog(stage);
        } else {
            // Temporarily delete preference
            deletePreference();
            stage.close();
        }
    }

    private void showCloseConfirmationDialog(Stage stage) {
        // Create a custom dialog
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.initStyle(StageStyle.UNDECORATED);
        DialogPane dialogPaneCustom = dialog.getDialogPane();
        dialogPaneCustom.getStylesheets().add(
                getClass().getResource("/styles/create-manuscript-scene.css").toExternalForm()
        );
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Do you want to exit?");
        Image imageDialog = new Image(getClass().getResourceAsStream("/img/alert2.png"));
        ImageView imageViewDialog = new ImageView(imageDialog);
        imageViewDialog.setFitHeight(60);
        imageViewDialog.setFitWidth(60);
        imageViewDialog.setPreserveRatio(true);
        dialog.getDialogPane().setGraphic(imageViewDialog);
        // Load "Don't ask again" preference
        boolean dontAskAgain = loadDontAskAgainPreference();
        // Create a VBox to hold the content
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        // Add text label
        Label textLabel = new Label("If you exit, any unsaved changes will be lost.");
        vbox.getChildren().add(textLabel);
        // Add "Don't ask again" checkbox
        CheckBox dontAskAgainCheckBox = new CheckBox("Don't ask again");
        dontAskAgainCheckBox.setSelected(dontAskAgain);
        vbox.getChildren().add(dontAskAgainCheckBox);
        // Add the VBox to the dialog pane content
        dialog.getDialogPane().setContent(vbox);
        // Remove default buttons and add custom OK and Cancel buttons
        dialog.getButtonTypes().clear();
        dialog.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        // Set preferred width and height
        dialog.getDialogPane().setPrefWidth(380); // Set preferred width
        dialog.getDialogPane().setPrefHeight(150); // Set preferred height
        // Show the dialog and handle the response
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (dontAskAgainCheckBox.isSelected()) {
                    saveDontAskAgainPreference(true);
                }
                stage.close();
            }
        });
    }





    private void saveDontAskAgainPreference(boolean dontAskAgain) {
        Preferences prefs = Preferences.userNodeForPackage(createManuscriptWindowController.class);
        prefs.putBoolean(DONT_ASK_AGAIN_KEY, dontAskAgain);
    }

    private boolean loadDontAskAgainPreference() {
        Preferences prefs = Preferences.userNodeForPackage(createManuscriptWindowController.class);
        return prefs.getBoolean(DONT_ASK_AGAIN_KEY, false);
    }

    private void deletePreference() {
        // Get user preferences node for the package of your controller class
        Preferences prefs = Preferences.userNodeForPackage(createManuscriptWindowController.class);

        // Remove the "dontAskAgain" preference
        prefs.remove("dontAskAgain");

        // Save the preferences
        try {
            prefs.flush(); // This saves the preferences immediately
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur during saving
        }
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
        manuscriptTypesMap.put(shortStoryManuscriptButton, "Short Story");
        manuscriptTypesMap.put(novelManuscriptButton, "Novel");
        manuscriptTypesMap.put(poemManuscriptButton, "Poem");
        manuscriptTypesMap.put(screenplayManuscriptButton, "Screenplay");
        manuscriptTypesMap.put(playManuscriptButton, "Play");
        manuscriptTypesMap.put(comicBookManuscriptButton, "Comic Book");
        System.out.println(manuscriptTypesMap);

        selectedManuscriptType = "Short Story";
        clearFields();
        // Add focus listeners to each button
        shortStoryManuscriptButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedManuscriptType = "Short Story";
                clearFields();
            }
        });
        novelManuscriptButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedManuscriptType = "Novel";
                clearFields();
            }
        });
        poemManuscriptButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedManuscriptType = "Poem";
                clearFields();
            }
        });
        screenplayManuscriptButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedManuscriptType = "Screenplay";
                clearFields();
            }
        });
        playManuscriptButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedManuscriptType = "Play";
                clearFields();
            }
        });
        comicBookManuscriptButton.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selectedManuscriptType = "Comic Book";
                clearFields();
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
        shortStoryManuscriptButton.requestFocus();
        shortStoryManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");

        shortStoryManuscriptButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Short story manuscript");
            clearFocusedStyle();
            shortStoryManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });
        novelManuscriptButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Novel manuscript");
            clearFocusedStyle();
            novelManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });
        poemManuscriptButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Poem manuscript");
            clearFocusedStyle();
            poemManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });
        screenplayManuscriptButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Screenplay manuscript");
            clearFocusedStyle();
            screenplayManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });
        playManuscriptButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Play manuscript");
            clearFocusedStyle();
            playManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });
        comicBookManuscriptButton.setOnAction(event -> {
            manuscriptTypeLabel.setText("Comic book manuscript");
            clearFocusedStyle();
            comicBookManuscriptButton.setStyle("-fx-background-color: #9FCEF4; -fx-border-style: none;");
        });
        // Simulate click on the first button to set initial label
        shortStoryManuscriptButton.fire();

    }
    private void clearFields() {
        manuscriptNameField.clear();
        manuscriptLocationTextField.clear();
        manuscriptAuthorField.clear();
    }

    private void clearFocusedStyle() {
        // Remove focused style from all buttons
        shortStoryManuscriptButton.setStyle("");
        novelManuscriptButton.setStyle("");
        poemManuscriptButton.setStyle("");
        screenplayManuscriptButton.setStyle("");
        playManuscriptButton.setStyle("");
        comicBookManuscriptButton.setStyle("");
    }

    @FXML
    private void handleManuscriptTypeSelection(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        String projectType = manuscriptTypesMap.get(selectedButton);

        if (projectType != null) {
            System.out.println("Selected Project Type: " + projectType);
        } else {
            System.out.println("No project type mapped to the clicked button.");
        }
    }

    @FXML
    private void handleCreateButtonClick(ActionEvent event) {
        // Find the selected project type
        for (Button button : manuscriptTypesMap.keySet()) {
            if (button.isFocused()) {
                selectedManuscriptType = manuscriptTypesMap.get(button);
                break;
            }
        }
        if (selectedManuscriptType != null) {
            String projectName = manuscriptNameField.getText();
            String projectLocation = manuscriptLocationTextField.getText();
            String projectAuthor = manuscriptAuthorField.getText();

            // Check if any of the fields are empty
            if (projectName.isEmpty() || projectLocation.isEmpty() || projectAuthor.isEmpty()) {
                showAlert("Empty Fields", "Please fill in all fields.");
                return; // Exit method if any field is empty
            }

            // Check if project name contains non-string characters
            if (!projectName.matches("[a-zA-Z0-9_\\s]+")) {
                showAlert("Invalid Project Name", "Project name should contain only letters, numbers, spaces, or underscores.");
                return; // Exit method if project name is invalid
            }

            // Check if author name contains non-string characters
            if (!projectAuthor.matches("[a-zA-Z0-9_\\s]+")) {
                showAlert("Invalid Author Name", "Author name should contain only letters, numbers, spaces, or underscores.");
                return; // Exit method if project name is invalid
            }

            // Check if project location is a valid directory
            File locationDirectory = new File(projectLocation);
            if (!locationDirectory.exists() || !locationDirectory.isDirectory()) {
                showAlert("Invalid Location", "Please select a valid directory for the project location.");
                return; // Exit method if location is invalid
            }

            try {
                if (!locationDirectory.canRead()) {
                    showAlert("Access Denied", "You don't have permission to access the selected directory.");
                    return; // Exit method if access is denied
                }
            } catch (SecurityException e) {
                // Handle security exception (e.g., permission denied)
                showAlert("Error", "Failed to check directory access: " + e.getMessage());
                return;
            }



            switch (selectedManuscriptType) {
                case "Short Story":
                    createShortStoryProject(projectName, projectLocation, projectAuthor);
                    break;
                case "Novel":
                    createNovelProject(projectName, projectLocation, projectAuthor);
                    break;
                case "Poem":
                    createPoemProject(projectName,projectLocation, projectAuthor);
                    break;
                case "Screenplay":
                    createScreenplayProject(projectName,projectLocation, projectAuthor);
                    break;
                case "Play":
                    createPlayProject(projectName,projectLocation, projectAuthor);
                    break;
                case "Comic Book":
                    createComicBookProject(projectName,projectLocation, projectAuthor);
                    break;
            }
        } else {
            System.out.println("No project type selected.");
        }
    }

    private void showAlert(String header, String content) {
        Alert alertEmptyFields = new Alert(Alert.AlertType.ERROR);
        alertEmptyFields.initStyle(StageStyle.UNDECORATED);
        alertEmptyFields.setHeaderText(header);
        alertEmptyFields.setContentText(content);
        Image image = new Image(getClass().getResourceAsStream("/img/alert_red.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        imageView.setPreserveRatio(true);
        alertEmptyFields.getDialogPane().setGraphic(imageView);
        DialogPane dialogPane = alertEmptyFields.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/create-manuscript-scene.css").toExternalForm()
        );
        alertEmptyFields.showAndWait();
    }


    private void createShortStoryProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Short Story Project");
        ShortStory shortStory = new ShortStory(projectName, projectLocation, projectAuthor);
        saveProjectAsTxt(shortStory);
    }

    private void createNovelProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Novel Project");
        Novel novel = new Novel(projectName, projectLocation, projectAuthor);
        saveProjectAsTxt(novel);
    }

    private void createPoemProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Poem Project");
        Poem poem = new Poem(projectName, projectLocation, projectAuthor);
        saveProjectAsTxt(poem);
    }

    private void createScreenplayProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Screenplay Project");
        Screenplay screenplay = new Screenplay(projectName, projectLocation, projectAuthor);
        saveProjectAsTxt(screenplay);
    }

    private void createPlayProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating Play Project");
        Play play = new Play(projectName, projectLocation, projectAuthor);
        saveProjectAsTxt(play);
    }

    private void createComicBookProject(String projectName, String projectLocation, String projectAuthor) {
        System.out.println("Creating ComicBook Project");
        ComicBook comicBook = new ComicBook(projectName, projectLocation, projectAuthor);
        saveProjectAsTxt(comicBook);
    }

    private void saveProjectAsTxt(Project project) {
        String fileName = project.getName() + ".txt";
        String filePath = project.getLocation() + File.separator + fileName;
        File outputFile = new File(filePath);

        try {
            // Create parent directories if they don't exist
            outputFile.getParentFile().mkdirs();

            // Write project information to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write("Project Name: " + project.getName());
                writer.newLine();
                writer.write("Project Location: " + project.getLocation());
                writer.newLine();
                writer.write("Project Author: " + project.getAuthor());
                writer.newLine();
                System.out.println("Data written to the file successfully!");
            }
        } catch (FileNotFoundException e) {
            // Handle file not found error
            showAlert("Error", "You cannot access this directory: " + e.getMessage());
        } catch (IOException e) {
            // Handle file writing errors
            System.out.println("Error writing data to the file:");
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
                String trimmedPath = directoryPath.substring(0, maxCharacters - 3) + "...";
                manuscriptLocationTextField.setText(trimmedPath);
            } else {
                manuscriptLocationTextField.setText(directoryPath);
            }

        }
    }
}
























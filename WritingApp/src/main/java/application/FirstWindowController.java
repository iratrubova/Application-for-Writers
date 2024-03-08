package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstWindowController implements Initializable {

    @FXML
    private Label textType;
    @FXML
    private TextField locationTextField;
    @FXML
    private Button closeWindowButton;
    @FXML
    private AnchorPane firstWindowAnchor;
    @FXML
    private HBox firstWindowHBox;
    @FXML
    private ImageView imageSmallLogo;

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button browseButton;
    @FXML
    private ChoiceBox <String> typeChoiceBox;
    private String[] typeOfProject = {"Short story", "Novel", "Poem", "Screenplay", "Play", "Comic book script"};

    @FXML
    private void handleLogoClick(MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemRestore = new MenuItem("Restore");
        itemRestore.setOnAction(e -> ((Stage) imageSmallLogo.getScene().getWindow()).setIconified(false));
        ImageView restoreIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/restore.png")));
        restoreIcon.setFitWidth(16);
        restoreIcon.setFitHeight(16);
        itemRestore.setGraphic(restoreIcon);
        MenuItem itemMinimize = new MenuItem("Minimize");
        itemMinimize.setOnAction(e -> ((Stage) imageSmallLogo.getScene().getWindow()).setIconified(true));
        ImageView minimizeIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/minimize_.png")));
        minimizeIcon.setFitWidth(16);
        minimizeIcon.setFitHeight(16);
        itemMinimize.setGraphic(minimizeIcon);
        MenuItem itemMaximize = new MenuItem("Maximize");
        itemMaximize.setOnAction(e -> ((Stage) imageSmallLogo.getScene().getWindow()).setMaximized(false));
        itemMaximize.setDisable(true);
        MenuItem itemClose = new MenuItem("Close");
        itemClose.setOnAction(e -> ((Stage) imageSmallLogo.getScene().getWindow()).close());
        ImageView closeIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/close.png")));
        closeIcon.setFitWidth(16);
        closeIcon.setFitHeight(16);
        itemClose.setGraphic(closeIcon);

        contextMenu.getItems().addAll(itemRestore, itemMinimize, itemMaximize, itemClose);

        // Show context menu at the location of the mouse click
        contextMenu.show(imageSmallLogo, event.getScreenX(), event.getScreenY());
    }


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
    private void cancelCreateProject() {
        ((Stage) closeWindowButton.getScene().getWindow()).close();
    }


    @FXML
    public void closeApp(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closing");
        alert.setHeaderText("You are about to leave");
        alert.setContentText("Close the app?");
        Image image = new Image(getClass().getResourceAsStream("/img/alert.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(imageView);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/first-window.css").toExternalForm()
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

        typeChoiceBox.getItems().addAll(typeOfProject);
        closeWindowButton.setText("\u2715");
        firstWindowHBox.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        firstWindowHBox.setOnMouseDragged(event -> {
            Stage stage = (Stage) firstWindowHBox.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

    }

    @FXML
    private void onChooseLocation() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Installation Location");

        // Set the initial directory to the user's home or current label text
        File initialDirectory = new File(locationTextField.getText());
        if (initialDirectory.exists() && initialDirectory.isDirectory()) {
            directoryChooser.setInitialDirectory(initialDirectory);
        }

        // Show dialog
        File selectedDirectory = directoryChooser.showDialog(null); // Use a reference to your primary stage if available

        // Set the maximum number of characters to display
        final int maxCharacters = 20;

        // Update the TextField with the selected directory path, trimming if necessary
        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();

            // Trim the path if it exceeds maxCharacters
            if (directoryPath.length() > maxCharacters) {
                // Optionally, add an ellipsis to indicate the text has been trimmed
                String trimmedPath = directoryPath.substring(0, maxCharacters - 3) + "...";
                locationTextField.setText(trimmedPath);
            } else {
                locationTextField.setText(directoryPath);
            }

        }
    }
}






















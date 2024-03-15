package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    @Override
    public void start(Stage stage)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/first-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image(getClass().getResourceAsStream("/img/book.png"));
            stage.getIcons().add(icon);
            Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 14);
            Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/JimNightshade-Regular.ttf"), 14);
            Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/Acme-Regular.ttf"), 14);
            scene.getStylesheets().addAll(
                    getClass().getResource("/styles/create-manuscript-scene.css").toExternalForm(),
                    getClass().getResource("/styles/create-project-scene.css").toExternalForm(),
                    getClass().getResource("/styles/first-window.css").toExternalForm()
            );
            System.out.println(font3);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}




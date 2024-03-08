package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    @Override
    public void start(Stage stage)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/first-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            String css = this.getClass().getResource("/styles/first-window.css").toExternalForm();
            Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 14);
            System.out.println(font);
            scene.getStylesheets().add(css);
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




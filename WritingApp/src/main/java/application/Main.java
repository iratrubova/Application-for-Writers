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
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/open-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 14);
            Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/RubikGemstones-Regular.ttf"), 14);
            Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/JimNightshade-Regular.ttf"), 14);
            scene.getStylesheets().addAll(
                    getClass().getResource("/styles/first-window.css").toExternalForm(),
                    getClass().getResource("/styles/open-window.css").toExternalForm()
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




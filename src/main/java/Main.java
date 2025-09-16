import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jimmy.Jimmy;

/**
 * A modern GUI for Jimmy using FXML with improved UX and responsive design.
 */
public class Main extends Application {

    private Jimmy jimmy = new Jimmy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            
            // Set window properties for better UX
            stage.setScene(scene);
            stage.setTitle("Jimmy - Task Management Assistant");
            stage.setMinHeight(400);
            stage.setMinWidth(500);
            stage.setWidth(800);
            stage.setHeight(700);
            
            // Enable window resizing
            stage.setResizable(true);
            
            // Center the window on screen
            stage.centerOnScreen();
            
            fxmlLoader.<MainWindow>getController().setJimmy(jimmy);  // inject the Jimmy instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

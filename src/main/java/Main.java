import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jimmy.Jimmy;

/**
 * A GUI for Jimmy using FXML.
 */
public class Main extends Application {

    private Jimmy jimmy = new Jimmy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJimmy(jimmy);  // inject the Jimmy instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

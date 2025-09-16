import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box with improved asymmetric design for user vs bot conversations.
 * Supports different styles for user messages, bot responses, and error messages.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert img != null : "Avatar image must not be null";
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box for bot messages (avatar on left, text on right).
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a user dialog box with right-aligned styling.
     * User messages: avatar on right, text on left (flipped layout).
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip(); // Flip so avatar is on right, text on left
        db.dialog.getStyleClass().add("user-message");
        db.setAlignment(Pos.TOP_RIGHT);
        db.setMaxWidth(600); // Prevent excessive width
        return db;
    }

    /**
     * Creates a bot dialog box with left-aligned styling.
     * Bot messages: avatar on left, text on right (normal layout).
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        // No flip - avatar stays on left, text on right
        db.dialog.getStyleClass().add("bot-message");
        db.setAlignment(Pos.TOP_LEFT);
        db.setMaxWidth(600); // Prevent excessive width
        return db;
    }

    /**
     * Creates an error dialog box with distinctive error styling.
     * Error messages: avatar on left, text on right (normal layout).
     */
    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        // No flip - avatar stays on left, text on right
        db.dialog.getStyleClass().add("error-message");
        db.setAlignment(Pos.TOP_LEFT);
        db.setMaxWidth(600); // Prevent excessive width
        return db;
    }
}

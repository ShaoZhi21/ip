import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

/**
 * Controller for the main GUI with improved asymmetric conversation design.
 * Features better error handling, responsive layout, and enhanced UX.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private jimmy.Jimmy jimmy;

    // Avatar images for chat interface
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/dudu.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/bubu.jpg"));

    @FXML
    public void initialize() {
        // Auto-scroll to bottom when new messages are added
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        
        // Add welcome message
        addWelcomeMessage();
    }

    /** Injects the Jimmy instance */
    public void setJimmy(jimmy.Jimmy j) {
        jimmy = j;
    }

    /**
     * Handles user input with improved error detection and asymmetric conversation display.
     * Creates distinct visual styles for user messages, bot responses, and errors.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return; // Don't process empty input
        }

        // Add user message (right-aligned)
        HBox userWrapper = new HBox();
        userWrapper.setAlignment(Pos.TOP_RIGHT);
        userWrapper.getChildren().add(DialogBox.getUserDialog(input, userImage));
        dialogContainer.getChildren().add(userWrapper);

        // Get bot response
        String response = jimmy.getResponse(input);
        
        // Check if response contains error indicators
        boolean isError = response.contains("Error:") || 
                         response.contains("Invalid") || 
                         response.contains("cannot be empty") ||
                         response.contains("I don't know what") ||
                         response.contains("Bleh");

        // Add bot response with appropriate styling (left-aligned)
        HBox botWrapper = new HBox();
        botWrapper.setAlignment(Pos.TOP_LEFT);
        if (isError) {
            botWrapper.getChildren().add(DialogBox.getErrorDialog(response, dukeImage));
        } else {
            botWrapper.getChildren().add(DialogBox.getDukeDialog(response, dukeImage));
        }
        dialogContainer.getChildren().add(botWrapper);

        userInput.clear();
    }

    /**
     * Adds a welcome message to the conversation.
     */
    private void addWelcomeMessage() {
        String welcomeText = "Hello! I'm Jimmy, your task management assistant. " +
                           "I can help you manage todos, deadlines, and events. " +
                           "Type 'help' to see what I can do!";
        HBox welcomeWrapper = new HBox();
        welcomeWrapper.setAlignment(Pos.TOP_LEFT);
        welcomeWrapper.getChildren().add(DialogBox.getDukeDialog(welcomeText, dukeImage));
        dialogContainer.getChildren().add(welcomeWrapper);
    }
}

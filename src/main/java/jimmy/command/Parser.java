package jimmy.command;

import jimmy.exception.JimmyException;

/**
 * Parses user input commands for the Jimmy task management system.
 * Provides methods to extract command types and arguments from user input strings.
 * Supports various command formats and validates input before processing.
 */
public class Parser {
    private static final String KEY_BY = "/by";
    private static final String KEY_FROM = "/from";
    private static final String KEY_TO = "/to";
    private static final String ERR_POSITIVE_INDEX = "Task index must be a positive number.";
    private static final String ERR_VALID_NUMBER = "Task index must be a valid number.";
    private static final String ERR_EMPTY_INPUT = "Input cannot be empty or contain only whitespace.";
    private static final String ERR_INVALID_CHARACTERS = "Input contains invalid special characters.";
    private static final String ERR_MULTIPLE_SPACES = "Multiple consecutive spaces are not allowed.";
    private static final String ERR_DUPLICATE_KEYWORD = "Keyword appears multiple times in the command.";
    private static final String ERR_MISSING_DESCRIPTION = "Description cannot be empty.";
    private static final String ERR_MISSING_DATE = "Date/time parameter is missing or empty.";
    
    /**
     * Represents a parsed command with its type and full input.
     * Contains the extracted command and the complete user input for further processing.
     */
    public static class ParsedCommand {
        /** The extracted command type (e.g., "todo", "deadline", "mark") */
        public final String command;
        
        /** The complete user input string for further parsing */
        public final String fullInput;  
        
        /**
         * Constructs a new ParsedCommand with the specified command and input.
         *
         * @param command The extracted command type
         * @param fullInput The complete user input string
         */
        public ParsedCommand(String command, String fullInput) {
            this.command = command;
            this.fullInput = fullInput;
        }
    }
    
    /**
     * Parses a user input string into a ParsedCommand object.
     * Splits the input on the first space to separate command from arguments.
     * Validates input format and handles common errors.
     *
     * @param userInput The user's input string
     * @return A ParsedCommand object containing the command and full input
     * @throws JimmyException if input format is invalid
     */
    public static ParsedCommand parseCommand(String userInput) throws JimmyException {
        if (userInput == null) {
            throw new JimmyException("Input cannot be null.");
        }
        
        // Normalize input: trim and collapse multiple spaces
        String normalizedInput = userInput.trim().replaceAll("\\s+", " ");
        
        if (normalizedInput.isEmpty()) {
            throw new JimmyException(ERR_EMPTY_INPUT);
        }
        
        // Check for invalid characters (basic validation)
        if (containsInvalidCharacters(normalizedInput)) {
            throw new JimmyException(ERR_INVALID_CHARACTERS);
        }
        
        String[] inputParts = normalizedInput.split(" ", 2);
        String command = inputParts[0];
        String fullInput = inputParts.length > 1 ? inputParts[1] : "";
        
        return new ParsedCommand(command, fullInput);
    }
    
    /**
     * Validates if input contains invalid special characters.
     * 
     * @param input The input string to validate
     * @return true if input contains invalid characters, false otherwise
     */
    private static boolean containsInvalidCharacters(String input) {
        // Allow alphanumeric, spaces, common punctuation, and task keywords
        return !input.matches("^[a-zA-Z0-9\\s.,!?/\\-:]+$");
    }
    
    /**
     * Validates if a mark command has valid arguments.
     * Checks that the command has a non-empty description.
     *
     * @param fullInput The full input string after the command
     * @return true if the mark command is valid, false otherwise
     */
    public static boolean isValidMarkCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    /**
     * Validates if an unmark command has valid arguments.
     * Checks that the command has a non-empty description.
     *
     * @param fullInput The full input string after the command
     * @return true if the unmark command is valid, false otherwise
     */
    public static boolean isValidUnmarkCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    /**
     * Validates if a todo command has valid arguments.
     * Checks that the command has a non-empty description.
     *
     * @param fullInput The full input string after the command
     * @return true if the todo command is valid, false otherwise
     */
    public static boolean isValidTodoCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    /**
     * Validates if a deadline command has valid arguments.
     * Checks that the command contains the required "/by" keyword and validates format.
     *
     * @param fullInput The full input string after the command
     * @return true if the deadline command is valid, false otherwise
     * @throws JimmyException if command format is invalid
     */
    public static boolean isValidDeadlineCommand(String fullInput) throws JimmyException {
        if (fullInput.trim().isEmpty()) {
            throw new JimmyException(ERR_MISSING_DESCRIPTION);
        }
        
        // Check for duplicate /by keywords
        int byCount = (fullInput.length() - fullInput.replace(KEY_BY, "").length()) / KEY_BY.length();
        if (byCount > 1) {
            throw new JimmyException(ERR_DUPLICATE_KEYWORD + " (" + KEY_BY + ")");
        }
        
        if (!fullInput.contains(KEY_BY)) {
            throw new JimmyException("Deadline command must include '/by' keyword.");
        }
        
        // Validate that description and date are not empty
        String[] parts = fullInput.split(KEY_BY);
        if (parts[0].trim().isEmpty()) {
            throw new JimmyException(ERR_MISSING_DESCRIPTION);
        }
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new JimmyException(ERR_MISSING_DATE);
        }
        
        return true;
    }
    
    /**
     * Validates if an event command has valid arguments.
     * Checks that the command contains both "/from" and "/to" keywords and validates format.
     *
     * @param fullInput The full input string after the command
     * @return true if the event command is valid, false otherwise
     * @throws JimmyException if command format is invalid
     */
    public static boolean isValidEventCommand(String fullInput) throws JimmyException {
        if (fullInput.trim().isEmpty()) {
            throw new JimmyException(ERR_MISSING_DESCRIPTION);
        }
        
        // Check for duplicate keywords
        int fromCount = (fullInput.length() - fullInput.replace(KEY_FROM, "").length()) / KEY_FROM.length();
        int toCount = (fullInput.length() - fullInput.replace(KEY_TO, "").length()) / KEY_TO.length();
        
        if (fromCount > 1) {
            throw new JimmyException(ERR_DUPLICATE_KEYWORD + " (" + KEY_FROM + ")");
        }
        if (toCount > 1) {
            throw new JimmyException(ERR_DUPLICATE_KEYWORD + " (" + KEY_TO + ")");
        }
        
        if (!containsAllKeywords(fullInput, KEY_FROM, KEY_TO)) {
            throw new JimmyException("Event command must include both '/from' and '/to' keywords.");
        }
        
        // Validate that all parts are not empty
        String[] fromParts = fullInput.split(KEY_FROM);
        if (fromParts[0].trim().isEmpty()) {
            throw new JimmyException(ERR_MISSING_DESCRIPTION);
        }
        
        String[] toParts = fromParts[1].split(KEY_TO);
        if (toParts[0].trim().isEmpty()) {
            throw new JimmyException("Start time cannot be empty.");
        }
        if (toParts.length < 2 || toParts[1].trim().isEmpty()) {
            throw new JimmyException("End time cannot be empty.");
        }
        
        return true;
    }
    
    /**
     * Validates if a delete command has valid arguments.
     * Checks that the command has a non-empty description.
     *
     * @param fullInput The full input string after the command
     * @return true if the delete command is valid, false otherwise
     */
    public static boolean isValidDeleteCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    public static boolean isValidFindCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }

    /**
     * Validates if a command contains all required keywords.
     * Uses varargs to check for multiple required keywords in a single call.
     *
     * @param fullInput The full input string to validate
     * @param requiredKeywords The required keywords to check for (varargs)
     * @return true if all required keywords are present, false otherwise
     */
    public static boolean containsAllKeywords(String fullInput, String... requiredKeywords) {
        for (String keyword : requiredKeywords) {
            if (!fullInput.contains(keyword)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Extracts the description part from a deadline command.
     * Splits the input on "/by" and returns the part before it.
     *
     * @param fullInput The full input string after the deadline command
     * @return The description part of the deadline
     */
    public static String extractDeadlineDescription(String fullInput) {
        return fullInput.split(KEY_BY)[0].trim();
    }
    
    /**
     * Extracts the date part from a deadline command.
     * Splits the input on "/by" and returns the part after it.
     *
     * @param fullInput The full input string after the deadline command
     * @return The date part of the deadline
     */
    public static String extractDeadlineDate(String fullInput) {
        return fullInput.split(KEY_BY)[1].trim();
    }
    
    /**
     * Extracts the description part from an event command.
     * Splits the input on "/from" and returns the part before it.
     *
     * @param fullInput The full input string after the event command
     * @return The description part of the event
     */
    public static String extractEventDescription(String fullInput) {
        return fullInput.split(KEY_FROM)[0].trim();
    }
    
    /**
     * Extracts the start time from an event command.
     * Splits the input on "/from" and "/to" to get the start time.
     *
     * @param fullInput The full input string after the event command
     * @return The start time of the event
     */
    public static String extractEventFrom(String fullInput) {
        return fullInput.split(KEY_FROM)[1].split(KEY_TO)[0].trim();
    }
    
    /**
     * Extracts the end time from an event command.
     * Splits the input on "/to" and returns the part after it.
     *
     * @param fullInput The full input string after the event command
     * @return The end time of the event
     */
    public static String extractEventTo(String fullInput) {
        return fullInput.split(KEY_TO)[1].trim();
    }
    
    /**
     * Parses a task index from the input string.
     * Converts the string to a 1-based index and validates it.
     *
     * @param fullInput The input string containing the task index
     * @return The 0-based index for internal use
     * @throws JimmyException if the index is invalid or not a number
     */
    public static int parseTaskIndex(String fullInput) throws JimmyException {
        String trimmed = fullInput.trim();
        boolean isNumber;
        try {
            Integer.parseInt(trimmed);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        if (!isNumber) {
            throw new JimmyException(ERR_VALID_NUMBER);
        }
        int index = Integer.parseInt(trimmed) - 1;
        if (index < 0) {
            throw new JimmyException(ERR_POSITIVE_INDEX);
        }
        return index;
    }
}

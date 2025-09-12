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
     *
     * @param userInput The user's input string
     * @return A ParsedCommand object containing the command and full input
     */
    public static ParsedCommand parseCommand(String userInput) {
        assert userInput != null : "Input must not be null";
        assert !userInput.isEmpty() : "Input must not be empty";
        String[] inputParts = userInput.split(" ", 2);
        String command = inputParts[0];
        String fullInput = inputParts.length > 1 ? inputParts[1] : "";
        assert command != null && !command.isEmpty() : "Command must be present";
        
        return new ParsedCommand(command, fullInput);
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
     * Checks that the command contains the required "/by" keyword.
     *
     * @param fullInput The full input string after the command
     * @return true if the deadline command is valid, false otherwise
     */
    public static boolean isValidDeadlineCommand(String fullInput) {
        return fullInput.contains(KEY_BY);
    }
    
    /**
     * Validates if an event command has valid arguments.
     * Checks that the command contains both "/from" and "/to" keywords.
     *
     * @param fullInput The full input string after the command
     * @return true if the event command is valid, false otherwise
     */
    public static boolean isValidEventCommand(String fullInput) {
        return containsAllKeywords(fullInput, KEY_FROM, KEY_TO);
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

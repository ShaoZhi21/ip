public class Parser {
    
    public static class ParsedCommand {
        public final String command;
        public final String fullInput;  
        
        public ParsedCommand(String command, String fullInput) {
            this.command = command;
            this.fullInput = fullInput;
        }
    }
    
    public static ParsedCommand parseCommand(String userInput) {
        String[] inputParts = userInput.split(" ", 2);
        String command = inputParts[0];
        String fullInput = inputParts.length > 1 ? inputParts[1] : "";
        
        return new ParsedCommand(command, fullInput);
    }
    
    public static boolean isValidMarkCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    public static boolean isValidUnmarkCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    public static boolean isValidTodoCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    public static boolean isValidDeadlineCommand(String fullInput) {
        return fullInput.contains("/by");
    }
    
    public static boolean isValidEventCommand(String fullInput) {
        return fullInput.contains("/from") && fullInput.contains("/to");
    }
    
    public static boolean isValidDeleteCommand(String fullInput) {
        return !fullInput.trim().isEmpty();
    }
    
    public static String extractDeadlineDescription(String fullInput) {
        return fullInput.split("/by")[0].trim();
    }
    
    public static String extractDeadlineDate(String fullInput) {
        return fullInput.split("/by")[1].trim();
    }
    
    public static String extractEventDescription(String fullInput) {
        return fullInput.split("/from")[0].trim();
    }
    
    public static String extractEventFrom(String fullInput) {
        return fullInput.split("/from")[1].split("/to")[0].trim();
    }
    
    public static String extractEventTo(String fullInput) {
        return fullInput.split("/to")[1].trim();
    }
    
    public static int parseTaskIndex(String fullInput) throws JimmyException {
        try {
            int index = Integer.parseInt(fullInput.trim()) - 1;
            if (index < 0) {
                throw new JimmyException("Task index must be a positive number.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new JimmyException("Task index must be a valid number.");
        }
    }
}

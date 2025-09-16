package jimmy;

import jimmy.task.Task;
import jimmy.task.TaskList;
import jimmy.task.Todo;
import jimmy.task.Deadline;
import jimmy.task.Event;
import jimmy.command.Parser;
import jimmy.storage.Storage;
import jimmy.ui.Ui;
import jimmy.exception.JimmyException;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * Main class for the Jimmy task management application.
 * Jimmy is a command-line task manager that allows users to add, mark, unmark,
 * list, and delete tasks. It supports different types of tasks including
 * Todo, Deadline, and Event tasks.
 */
public class Jimmy {
    /**
     * Command keywords supported by the application.
     * Using an enum avoids magic strings and reduces deep nesting.
     */
    private enum CommandKeyword {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, FIND, DELETE, BLAH, UNKNOWN
    }

    private static CommandKeyword toKeyword(String word) {
        if (word == null) {
            return CommandKeyword.UNKNOWN;
        }
        switch (word) {
        case "bye":
            return CommandKeyword.BYE;
        case "list":
            return CommandKeyword.LIST;
        case "mark":
            return CommandKeyword.MARK;
        case "unmark":
            return CommandKeyword.UNMARK;
        case "todo":
            return CommandKeyword.TODO;
        case "deadline":
            return CommandKeyword.DEADLINE;
        case "event":
            return CommandKeyword.EVENT;
        case "find":
            return CommandKeyword.FIND;
        case "delete":
            return CommandKeyword.DELETE;
        case "blah":
            return CommandKeyword.BLAH;
        default:
            return CommandKeyword.UNKNOWN;
        }
    }

    /**
     * Gets the data file path, ensuring it works both in development and when packaged as JAR.
     * When running from JAR, uses the data folder beside the JAR file.
     * When running in development, uses the project's data directory.
     * No hardcoded paths - all paths are dynamically determined.
     *
     * @return The path to the data file as a string
     */
    private static String getDataFilePath() {
        try {
            // Get the directory where the JAR file is located
            String jarPath = Jimmy.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            Path jarDir = Paths.get(jarPath).getParent();
            
            // If running from JAR, use the data folder beside the JAR file
            if (jarPath.endsWith(".jar")) {
                Path dataDir = jarDir.resolve("data");
                String jarDataPath = dataDir.resolve("jimmy.txt").toString();
                System.out.println("DEBUG: Running from JAR, using data folder beside JAR: " + jarDataPath);
                return jarDataPath;
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Exception in getDataFilePath: " + e.getMessage());
            // Fall back to relative path if we can't determine JAR location
        }
        
        // Fall back to relative path (for development or if JAR detection fails)
        System.out.println("DEBUG: Running in development mode, using relative path: data/jimmy.txt");
        return "data/jimmy.txt";
    }
    private TaskList guiTaskList; // persisted across GUI inputs
    private Storage guiStorage;
    
    /**
     * Main entry point for the Jimmy application.
     * Initializes the storage, UI, and task list, then starts the main application loop.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Storage storage = new Storage(getDataFilePath());
        Ui ui = new Ui();
        
        List<Task> loadedTasks = storage.load();
        TaskList taskList = new TaskList(loadedTasks);
        
        ui.showWelcome();

        // User input
        Scanner scanner = new Scanner(System.in);
        run(taskList, scanner, ui, storage);
    }

    /**
     * Main application loop that processes user commands.
     * Continuously reads user input and executes corresponding commands
     * until the user types "bye".
     *
     * @param taskList The list of tasks to manage
     * @param scanner Scanner for reading user input
     * @param ui User interface for displaying messages
     * @param storage Storage system for persisting tasks
     */
    public static void run(TaskList taskList, Scanner scanner, Ui ui, Storage storage) {
        boolean running = true;
        while (scanner.hasNextLine() && running) {
            String userInput = scanner.nextLine();
            try {
                Parser.ParsedCommand parsed = Parser.parseCommand(userInput);
                String command = parsed.command;
                CommandKeyword keyword = toKeyword(command);

                switch (keyword) {
                case BYE:
                    ui.showGoodbye();
                    running = false;
                    break;
                case LIST:
                    ui.showTaskList(taskList.getAllTasks());
                    break;
                case MARK:
                    if (!Parser.isValidMarkCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a mark cannot be empty.");
                    }
                    int markIndex = Parser.parseTaskIndex(parsed.fullInput);
                    if (markIndex >= taskList.getSize()) {
                        throw new JimmyException("Task index " + (markIndex + 1) + " is out of range. You have " + taskList.getSize() + " tasks.");
                    }
                    Task markedTask = taskList.getTask(markIndex);
                    taskList.markTaskAsDone(markIndex);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskMarkedAsDone(markedTask);
                    break;
                case UNMARK:
                    if (!Parser.isValidUnmarkCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of an unmark cannot be empty.");
                    }
                    int unmarkIndex = Parser.parseTaskIndex(parsed.fullInput);
                    if (unmarkIndex >= taskList.getSize()) {
                        throw new JimmyException("Task index " + (unmarkIndex + 1) + " is out of range. You have " + taskList.getSize() + " tasks.");
                    }
                    Task unmarkedTask = taskList.getTask(unmarkIndex);
                    taskList.markTaskAsNotDone(unmarkIndex);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskMarkedAsNotDone(unmarkedTask);
                    break;
                case TODO:
                    if (!Parser.isValidTodoCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a todo cannot be empty.");
                    }
                    Task todoTask = new Todo(parsed.fullInput);
                    taskList.addTask(todoTask);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskAdded(todoTask, taskList.getSize());
                    break;
                case DEADLINE:
                    if (!Parser.isValidDeadlineCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a deadline must include '/by'.");
                    }
                    String deadlineDesc = Parser.extractDeadlineDescription(parsed.fullInput);
                    String by = Parser.extractDeadlineDate(parsed.fullInput);
                    try {
                        Task deadlineTask = new Deadline(deadlineDesc, by);
                        taskList.addTask(deadlineTask);
                        storage.save(taskList.getAllTasks());
                        ui.showTaskAdded(deadlineTask, taskList.getSize());
                    } catch (IllegalArgumentException e) {
                        throw new JimmyException("Invalid date format: " + e.getMessage());
                    }
                    break;
                case EVENT:
                    if (!Parser.isValidEventCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of an event must include '/from' and '/to'.");
                    }
                    String eventDesc = Parser.extractEventDescription(parsed.fullInput);
                    String from = Parser.extractEventFrom(parsed.fullInput);
                    String to = Parser.extractEventTo(parsed.fullInput);
                    try {
                        Task eventTask = new Event(eventDesc, from, to);
                        taskList.addTask(eventTask);
                        storage.save(taskList.getAllTasks());
                        ui.showTaskAdded(eventTask, taskList.getSize());
                    } catch (IllegalArgumentException e) {
                        throw new JimmyException("Invalid date format: " + e.getMessage());
                    }
                    break;
                case FIND:
                    if (!Parser.isValidFindCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a find cannot be empty.");
                    }
                    java.util.List<Task> matches = taskList.findByKeyword(parsed.fullInput);
                    ui.showMatchingTasks(matches);
                    break;
                case BLAH:
                    throw new JimmyException("I don't know what blah is. Bleh.");
                case DELETE:
                    if (!Parser.isValidDeleteCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a delete cannot be empty.");
                    }
                    int deleteIndex = Parser.parseTaskIndex(parsed.fullInput);
                    if (deleteIndex >= taskList.getSize()) {
                        throw new JimmyException("Task index " + (deleteIndex + 1) + " is out of range. You have " + taskList.getSize() + " tasks.");
                    }
                    Task removedTask = taskList.getTask(deleteIndex);
                    taskList.removeTask(deleteIndex);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskDeleted(removedTask, taskList.getSize());
                    break;
                case UNKNOWN:
                default:
                    throw new JimmyException("I don't know what that means. Please use a valid command like 'todo', 'deadline', 'event', 'list', 'mark', 'unmark', 'delete', or 'find'.");
                }
            } catch (JimmyException e) {
                ui.showError(e.getMessage());
            }
        }
        scanner.close();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (guiStorage == null || guiTaskList == null) {
            guiStorage = new Storage(getDataFilePath());
            java.util.List<Task> loaded = guiStorage.load();
            guiTaskList = new TaskList(loaded);
        }

        StringBuilder out = new StringBuilder();
        jimmy.ui.GuiUi guiUi = new jimmy.ui.GuiUi(out);
        try {
            // Process exactly one line using the same logic as CLI
            Scanner singleLine = new Scanner(input + "\n");
            run(guiTaskList, singleLine, guiUi, guiStorage);
        } finally {
            // no-op
        }
        return out.toString().trim();
    }
}

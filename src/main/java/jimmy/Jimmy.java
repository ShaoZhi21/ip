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

/**
 * Main class for the Jimmy task management application.
 * Jimmy is a command-line task manager that allows users to add, mark, unmark,
 * list, and delete tasks. It supports different types of tasks including
 * Todo, Deadline, and Event tasks.
 */
public class Jimmy {
    
    /**
     * Main entry point for the Jimmy application.
     * Initializes the storage, UI, and task list, then starts the main application loop.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Storage storage = new Storage("data/jimmy.txt");
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
                
                if (userInput.equals("bye")) {
                    ui.showGoodbye();
                    running = false;
                } else if (userInput.equals("list")) {
                    ui.showTaskList(taskList.getAllTasks());
                } else if (command.equals("mark")) {
                    if (!Parser.isValidMarkCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a mark cannot be empty.");
                    }
                    int argument = Parser.parseTaskIndex(parsed.fullInput);
                    Task task = taskList.getTask(argument);
                    taskList.markTaskAsDone(argument);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskMarkedAsDone(task);
                } else if (command.equals("unmark")) {
                    if (!Parser.isValidUnmarkCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of an unmark cannot be empty.");
                    }
                    int argument = Parser.parseTaskIndex(parsed.fullInput);
                    Task task = taskList.getTask(argument);
                    taskList.markTaskAsNotDone(argument);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskMarkedAsNotDone(task);
                } else if (command.equals("todo")) {
                    if (!Parser.isValidTodoCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a todo cannot be empty.");
                    }
                    Task task = new Todo(parsed.fullInput);
                    taskList.addTask(task);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskAdded(task, taskList.getSize());
                } else if (command.equals("deadline")) {
                    if (!Parser.isValidDeadlineCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a deadline must include '/by'.");
                    }
                    String description = Parser.extractDeadlineDescription(parsed.fullInput);
                    String by = Parser.extractDeadlineDate(parsed.fullInput);
                    try {
                        Task task = new Deadline(description, by);
                        taskList.addTask(task);
                        storage.save(taskList.getAllTasks());
                        ui.showTaskAdded(task, taskList.getSize());
                    } catch (IllegalArgumentException e) {
                        throw new JimmyException("Invalid date format: " + e.getMessage());
                    }
                } else if (command.equals("event")) {
                    if (!Parser.isValidEventCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of an event must include '/from' and '/to'.");
                    }
                    String description = Parser.extractEventDescription(parsed.fullInput);
                    String from = Parser.extractEventFrom(parsed.fullInput);
                    String to = Parser.extractEventTo(parsed.fullInput);
                    try {
                        Task task = new Event(description, from, to);
                        taskList.addTask(task);
                        storage.save(taskList.getAllTasks());
                        ui.showTaskAdded(task, taskList.getSize());
                    } catch (IllegalArgumentException e) {
                        throw new JimmyException("Invalid date format: " + e.getMessage());
                    }
                } else if (command.equals("blah")) {
                    throw new JimmyException("I don't know what blah is. Bleh.");
                } else if (command.equals("delete")) {
                    if (!Parser.isValidDeleteCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a delete cannot be empty.");
                    }
                    int argument = Parser.parseTaskIndex(parsed.fullInput);
                    Task removedTask = taskList.getTask(argument);
                    taskList.removeTask(argument);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskDeleted(removedTask, taskList.getSize());
                } else {
                    Task task = new Task(userInput);
                    taskList.addTask(task);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskAddedSimple(userInput);
                }
            } catch (JimmyException e) {
                ui.showError(e.getMessage());
            }
        }
        scanner.close();
    }
}

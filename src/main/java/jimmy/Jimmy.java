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

public class Jimmy {
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
                    Task t = new Todo(parsed.fullInput);
                    taskList.addTask(t);
                    storage.save(taskList.getAllTasks());
                    ui.showTaskAdded(t, taskList.getSize());
                } else if (command.equals("deadline")) {
                    if (!Parser.isValidDeadlineCommand(parsed.fullInput)) {
                        throw new JimmyException("The description of a deadline must include '/by'.");
                    }
                    String description = Parser.extractDeadlineDescription(parsed.fullInput);
                    String by = Parser.extractDeadlineDate(parsed.fullInput);
                    try {
                        Task t = new Deadline(description, by);
                        taskList.addTask(t);
                        storage.save(taskList.getAllTasks());
                        ui.showTaskAdded(t, taskList.getSize());
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
                        Task t = new Event(description, from, to);
                        taskList.addTask(t);
                        storage.save(taskList.getAllTasks());
                        ui.showTaskAdded(t, taskList.getSize());
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
                    taskList.addTask(new Task(userInput));
                    ui.showTaskAddedSimple(userInput);
                }
            } catch (JimmyException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}

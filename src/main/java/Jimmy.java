import java.util.List;
import java.util.Scanner;

public class Jimmy {
    public static void main(String[] args) {
        
        Storage storage = new Storage("data/jimmy.txt");
        Ui ui = new Ui();
        
        List<Task> list = storage.load();
        
        ui.showWelcome();

        // User input
        Scanner scanner = new Scanner(System.in);
        run(list, scanner, ui, storage);
    }

    public static void run(List<Task> list, Scanner scanner, Ui ui, Storage storage) {
        boolean running = true;
        while (scanner.hasNextLine() && running) {
            String userInput = scanner.nextLine();
            try {
                String[] inputParts = userInput.split(" ", 2);
                String command = inputParts[0];
                if (userInput.equals("bye")) {
                    ui.showGoodbye();
                    running = false;
                } else if (userInput.equals("list")) {
                    ui.showTaskList(list);
                } else if (command.equals("mark")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of a mark cannot be empty.");
                    }
                    int argument = Integer.parseInt(inputParts[1]) - 1;
                    Task task = list.get(argument);
                    task.markAsDone();
                    storage.save(list);
                    ui.showTaskMarkedAsDone(task);
                } else if (command.equals("unmark")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of an unmark cannot be empty.");
                    }
                    int argument = Integer.parseInt(inputParts[1]) - 1;
                    Task task = list.get(argument);
                    task.markAsNotDone();
                    storage.save(list);
                    ui.showTaskMarkedAsNotDone(task);
                } else if (command.equals("todo")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of a todo cannot be empty.");
                    }
                    Task t = new Todo(inputParts[1]);
                    list.add(t);
                    storage.save(list);
                    ui.showTaskAdded(t, list.size());
                } else if (command.equals("deadline")) {
                    if (inputParts.length < 2 || !inputParts[1].contains("/by")) {
                        throw new JimmyException("The description of a deadline must include '/by'.");
                    }
                    String description = inputParts[1].split("/by")[0].trim();
                    String by = inputParts[1].split("/by")[1].trim();
                    try {
                        Task t = new Deadline(description, by);
                        list.add(t);
                        storage.save(list);
                        ui.showTaskAdded(t, list.size());
                    } catch (IllegalArgumentException e) {
                        throw new JimmyException("Invalid date format: " + e.getMessage());
                    }
                } else if (command.equals("event")) {
                    if (inputParts.length < 2 || !inputParts[1].contains("/from") || !inputParts[1].contains("/to")) {
                        throw new JimmyException("The description of an event must include '/from' and '/to'.");
                    }
                    String description = inputParts[1].split("/from")[0].trim();
                    String from = inputParts[1].split("/from")[1].split("/to")[0].trim();
                    String to = inputParts[1].split("/to")[1].trim();
                    try {
                        Task t = new Event(description, from, to);
                        list.add(t);
                        storage.save(list);
                        ui.showTaskAdded(t, list.size());
                    } catch (IllegalArgumentException e) {
                        throw new JimmyException("Invalid date format: " + e.getMessage());
                    }
                } else if (command.equals("blah")) {
                    throw new JimmyException("I don't know what blah is. Bleh.");
                } else if (command.equals("delete")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of a delete cannot be empty.");
                    }
                    int argument = Integer.parseInt(inputParts[1]) - 1;
                    Task removedTask = list.get(argument);
                    list.remove(argument);
                    storage.save(list);
                    ui.showTaskDeleted(removedTask, list.size());
                } else {
                    list.add(new Task(userInput));
                    ui.showTaskAddedSimple(userInput);
                }
            } catch (JimmyException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}

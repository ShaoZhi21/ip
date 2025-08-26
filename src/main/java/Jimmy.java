import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jimmy {
    public static void main(String[] args) {
        // Relative path for txt file
        Path path = Paths.get("data", "jimmy.txt");
        // Creates task and puts them into a list
        List<Task> list = loadTasks(path);

        printWelcome();

        // User input
        Scanner scanner = new Scanner(System.in);
        run(list, scanner);
    }

    public static List<Task> loadTasks(Path filePath) {
        List<Task> tasks = new ArrayList<>();
        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task t;
                    if (type.equals("T")) {
                        t = new Todo(description);
                    } else if (type.equals("D")) {
                        String by = parts[3];
                        t = new Deadline(description, by);
                    } else if (type.equals("E")) {
                        String from = parts[3];
                        String to = parts[4];
                        t = new Event(description, from, to);
                    } else {
                        continue; // Skip unknown task types
                    }
                    if (isDone) {
                        t.markAsDone();
                    }
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }   

    public static void printWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Jimmy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public static void run(List<Task> list, Scanner scanner) {
        boolean running = true;
        while (scanner.hasNextLine() && running) {
            String userInput = scanner.nextLine();
            try {
                String[] inputParts = userInput.split(" ", 2);
                String command = inputParts[0];
                if (userInput.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                    running = false;
                } else if (userInput.equals("list")) {
                    System.out.println("____________________________________________________________");
                    for (int i = 0; i < list.size(); i++) {
                        Task t = list.get(i);
                        System.out.print((i + 1) + "." + t.toString());
                        System.out.print("\n");
                    }
                    System.out.println("____________________________________________________________");
                } else if (command.equals("mark")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of a mark cannot be empty.");
                    }
                    int argument = Integer.parseInt(inputParts[1]) - 1;
                    list.get(argument).markAsDone();
                    saveTasks(list);
                    System.out.println("____________________________________________________________");
                    System.out.print("Nice! I've marked this task as done: ");
                    System.out.print("\n");
                    System.out.println("[" + list.get(argument).getStatusIcon() + "] " + list.get(argument).description);
                    System.out.println("____________________________________________________________");
                } else if (command.equals("unmark")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of an unmark cannot be empty.");
                    }
                    int argument = Integer.parseInt(inputParts[1]) - 1;
                    list.get(argument).markAsNotDone();
                    saveTasks(list);
                    System.out.println("____________________________________________________________");
                    System.out.print("OK, I've marked this task as not done yet: ");
                    System.out.print("\n");
                    System.out.println("[" + list.get(argument).getStatusIcon() + "] " + list.get(argument).description);
                    System.out.println("____________________________________________________________");
                } else if (command.equals("todo")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of a todo cannot be empty.");
                    }
                    Task t = new Todo(inputParts[1]);
                    list.add(t);
                    saveTasks(list);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(t.toString());
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (command.equals("deadline")) {
                    if (inputParts.length < 2 || !inputParts[1].contains("/by")) {
                        throw new JimmyException("The description of a deadline must include '/by'.");
                    }
                    String description = inputParts[1].split("/by")[0].trim();
                    String by = inputParts[1].split("/by")[1].trim();
                    Task t = new Deadline(description, by);
                    list.add(t);
                    saveTasks(list);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(t.toString());
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (command.equals("event")) {
                    if (inputParts.length < 2 || !inputParts[1].contains("/from") || !inputParts[1].contains("/to")) {
                        throw new JimmyException("The description of an event must include '/from' and '/to'.");
                    }
                    String description = inputParts[1].split("/from")[0].trim();
                    String from = inputParts[1].split("/from")[1].split("/to")[0].trim();
                    String to = inputParts[1].split("/to")[1].trim();
                    Task t = new Event(description, from, to);
                    list.add(t);
                    saveTasks(list);
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(t.toString());
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else if (command.equals("blah")) {
                    throw new JimmyException("I don't know what blah is. Bleh.");
                } else if (command.equals("delete")) {
                    if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                        throw new JimmyException("The description of a delete cannot be empty.");
                    }
                    int argument = Integer.parseInt(inputParts[1]) - 1;
                    Task removedTask = list.get(argument);
                    list.remove(argument);
                    saveTasks(list);
                    System.out.println("____________________________________________________________");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println(removedTask.toString());
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } else {
                    list.add(new Task(userInput));
                    System.out.println("____________________________________________________________");
                    System.out.println("added: " + userInput);
                    System.out.println("____________________________________________________________");
                }
            } catch (JimmyException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }
        }
    }

    // Update jimmy.txt with new list of tasks
    public static void saveTasks(List<Task> list) {
        Path path = Paths.get("data", "jimmy.txt");
        List<String> lines = new ArrayList<>();
        for (Task t : list) {
            lines.add(t.toFileString());
        }
        try {
            Files.write(path, lines); // overwrites the old file
        } catch (Exception e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}

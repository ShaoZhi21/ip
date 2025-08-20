import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jimmy {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Jimmy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        Scanner scanner = new Scanner(System.in);
        List<Task> list = new ArrayList<>();
        boolean running = true;
        while (running) {
            String userInput = scanner.nextLine();
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
                int argument = Integer.parseInt(inputParts[1]) - 1;
                list.get(argument).markAsDone();
                System.out.println("____________________________________________________________");
                System.out.print("Nice! I've marked this task as done: ");
                System.out.print("\n");
                System.out.println("[" + list.get(argument).getStatusIcon() + "] " + list.get(argument).description);
                System.out.println("____________________________________________________________");
            } else if (command.equals("unmark")) {
                int argument = Integer.parseInt(inputParts[1]) - 1;
                list.get(argument).markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.print("OK, I've marked this task as not done yet: ");
                System.out.print("\n");
                System.out.println("[" + list.get(argument).getStatusIcon() + "] " + list.get(argument).description);
                System.out.println("____________________________________________________________");
            } else if (command.equals("todo")) {
                Task t = new Todo(inputParts[1]);
                list.add(t);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(t.toString());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else if (command.equals("deadline")) {
                String description = inputParts[1].split("/by")[0].trim();
                String by = inputParts[1].split("/by")[1].trim();
                Task t = new Deadline(description, by);
                list.add(t);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(t.toString());
                System.out.println("Now you have " + list.size() + " tasks in the list.");
                System.out.println("____________________________________________________________");
            } else if (command.equals("event")) {
                String description = inputParts[1].split("/from")[0].trim();
                String from = inputParts[1].split("/from")[1].split("/to")[0].trim();
                String to = inputParts[1].split("/to")[1].trim();
                Task t = new Event(description, from, to);
                list.add(t);
                System.out.println("____________________________________________________________");
                System.out.println("Got it. I've added this task:");
                System.out.println(t.toString());
                System.out.println("Now you have " + list.size() + " tasks in the list".);
                System.out.println("____________________________________________________________");
            } else {
                list.add(new Task(userInput));
                System.out.println("____________________________________________________________");
                System.out.println("added: " + userInput);
                System.out.println("____________________________________________________________");
            }
            }
        }
    }

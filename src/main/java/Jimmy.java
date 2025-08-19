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
        List<String> list = new ArrayList<>();
        boolean running = true;
        while (running) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                running = false;
            } else {
                list.add(userInput);
                System.out.println("____________________________________________________________");
                System.out.println("added: " + userInput);
                System.out.println("____________________________________________________________");
            }
            }
        }
    }

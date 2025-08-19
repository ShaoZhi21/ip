import java.util.Scanner;

public class Jimmy {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Jimmy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            String userInput = scanner.nextLine();
            if (userInput.equals("exit")) {
                System.out.println("Bye. Hope to see you again soon!");
                running = false;
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(userInput);
                System.out.println("____________________________________________________________");
            }
            }
        }
    }

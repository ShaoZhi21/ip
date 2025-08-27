package jimmy.ui;

import jimmy.task.Task;
import java.util.List;

public class Ui {
    
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Jimmy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    
    public void showGoodbye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
    
    public void showTaskList(List<Task> tasks) {
        System.out.println("____________________________________________________________");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            System.out.print((i + 1) + "." + t.toString());
            System.out.print("\n");
        }
        System.out.println("____________________________________________________________");
    }
    
    public void showTaskMarkedAsDone(Task task) {
        System.out.println("____________________________________________________________");
        System.out.print("Nice! I've marked this task as done: ");
        System.out.print("\n");
        System.out.println("[" + task.getStatusIcon() + "] " + task.getDescription());
        System.out.println("____________________________________________________________");
    }
    
    public void showTaskMarkedAsNotDone(Task task) {
        System.out.println("____________________________________________________________");
        System.out.print("OK, I've marked this task as not done yet: ");
        System.out.print("\n");
        System.out.println("[" + task.getStatusIcon() + "] " + task.getDescription());
        System.out.println("____________________________________________________________");
    }
    
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
    
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
    
    public void showTaskAddedSimple(String description) {
        System.out.println("____________________________________________________________");
        System.out.println("added: " + description);
        System.out.println("____________________________________________________________");
    }
    
    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }
    
    public void showLoadingError(String message) {
        System.out.println("Error loading tasks: " + message);
    }
    
    public void showSavingError(String message) {
        System.out.println("Error saving tasks: " + message);
    }
    
    public void showWarning(String message) {
        System.out.println("Warning: " + message);
    }
}

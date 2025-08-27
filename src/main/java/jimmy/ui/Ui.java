package jimmy.ui;

import jimmy.task.Task;
import java.util.List;

/**
 * Handles user interface and display operations for the Jimmy task management system.
 * Provides methods to show welcome messages, task lists, and various status updates.
 * All output is formatted consistently with clear visual separators.
 */
public class Ui {
    
    /**
     * Displays the welcome message when the application starts.
     * Shows a formatted greeting with the application name.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Jimmy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays the goodbye message when the application exits.
     * Shows a formatted farewell message.
     */
    public void showGoodbye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a list of all tasks with their numbers.
     * Each task is shown on a separate line with its index.
     *
     * @param tasks The list of tasks to display
     */
    public void showTaskList(List<Task> tasks) {
        System.out.println("____________________________________________________________");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.print((i + 1) + "." + task.toString());
            System.out.print("\n");
        }
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a message confirming that a task has been marked as done.
     * Shows the task with its completion status.
     *
     * @param task The task that was marked as done
     */
    public void showTaskMarkedAsDone(Task task) {
        System.out.println("____________________________________________________________");
        System.out.print("Nice! I've marked this task as done: ");
        System.out.print("\n");
        System.out.println("[" + task.getStatusIcon() + "] " + task.getDescription());
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a message confirming that a task has been marked as not done.
     * Shows the task with its completion status.
     *
     * @param task The task that was marked as not done
     */
    public void showTaskMarkedAsNotDone(Task task) {
        System.out.println("____________________________________________________________");
        System.out.print("OK, I've marked this task as not done yet: ");
        System.out.print("\n");
        System.out.println("[" + task.getStatusIcon() + "] " + task.getDescription());
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a message confirming that a task has been added.
     * Shows the added task and the new total count.
     *
     * @param task The task that was added
     * @param totalTasks The total number of tasks after adding
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a message confirming that a task has been deleted.
     * Shows the deleted task and the new total count.
     *
     * @param task The task that was deleted
     * @param totalTasks The total number of tasks after deletion
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a simple message confirming that a task was added.
     * Shows just the description of the added task.
     *
     * @param description The description of the task that was added
     */
    public void showTaskAddedSimple(String description) {
        System.out.println("____________________________________________________________");
        System.out.println("added: " + description);
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays an error message to the user.
     * Shows the error message in a formatted box.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println("____________________________________________________________");
        System.out.println(message);
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a loading error message.
     * Shows the error without the formatted box for system-level errors.
     *
     * @param message The loading error message to display
     */
    public void showLoadingError(String message) {
        System.out.println("Error loading tasks: " + message);
    }
    
    /**
     * Displays a saving error message.
     * Shows the error without the formatted box for system-level errors.
     *
     * @param message The saving error message to display
     */
    public void showSavingError(String message) {
        System.out.println("Error saving tasks: " + message);
    }
    
    /**
     * Displays a warning message to the user.
     * Shows the warning without the formatted box for system-level messages.
     *
     * @param message The warning message to display
     */
    public void showWarning(String message) {
        System.out.println("Warning: " + message);
    }

    public void showMatchingTasks(java.util.List<jimmy.task.Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            jimmy.task.Task t = tasks.get(i);
            System.out.print((i + 1) + "." + t.toString());
            System.out.print("\n");
        }
        System.out.println("____________________________________________________________");
    }
}

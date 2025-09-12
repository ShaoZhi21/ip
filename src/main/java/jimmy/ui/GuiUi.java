package jimmy.ui;

import java.util.List;
import jimmy.task.Task;

/**
 * UI adapter that accumulates outputs into a StringBuilder for GUI use.
 * Reuses the same messages as the console UI, but returns text to caller.
 */
public class GuiUi extends Ui {

    private final StringBuilder out;

    public GuiUi(StringBuilder out) {
        this.out = out;
    }

    private void println(String line) {
        out.append(line).append("\n");
    }

    @Override
    public void showGoodbye() {
        println("Bye. Hope to see you again soon!");
    }

    @Override
    public void showTaskList(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            println((i + 1) + "." + task.toString());
        }
        if (tasks.isEmpty()) {
            println("(no tasks)");
        }
    }

    @Override
    public void showTaskMarkedAsDone(Task task) {
        println("Nice! I've marked this task as done:");
        println("[" + task.getStatusIcon() + "] " + task.getDescription());
    }

    @Override
    public void showTaskMarkedAsNotDone(Task task) {
        println("OK, I've marked this task as not done yet:");
        println("[" + task.getStatusIcon() + "] " + task.getDescription());
    }

    @Override
    public void showTaskAdded(Task task, int totalTasks) {
        println("Got it. I've added this task:");
        println(task.toString());
        println("Now you have " + totalTasks + " tasks in the list.");
    }

    @Override
    public void showTaskDeleted(Task task, int totalTasks) {
        println("Noted. I've removed this task:");
        println(task.toString());
        println("Now you have " + totalTasks + " tasks in the list.");
    }

    @Override
    public void showTaskAddedSimple(String description) {
        println("added: " + description);
    }

    @Override
    public void showError(String... messages) {
        for (String message : messages) {
            println(message);
        }
    }

    @Override
    public void showMatchingTasks(List<Task> tasks) {
        println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            println((i + 1) + "." + t.toString());
        }
        if (tasks.isEmpty()) {
            println("(no matches)");
        }
    }
}



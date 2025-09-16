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
        println("🎵 Dadada! Hope to see you again soon! Lalalala! 🎵");
    }

    @Override
    public void showTaskList(List<Task> tasks) {
        println("🎶 Lalalala! Here's your task symphony! 🎶");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            println((i + 1) + "." + task.toString());
        }
        if (tasks.isEmpty()) {
            println("Dadada... no tasks to sing about! 🎵");
        }
    }

    @Override
    public void showTaskMarkedAsDone(Task task) {
        println("🎉 Dadada! Task completed! Lalalala! 🎉");
        println("[" + task.getStatusIcon() + "] " + task.getDescription());
    }

    @Override
    public void showTaskMarkedAsNotDone(Task task) {
        println("🎵 Lalalala! Task unmarked! Dadada! 🎵");
        println("[" + task.getStatusIcon() + "] " + task.getDescription());
    }

    @Override
    public void showTaskAdded(Task task, int totalTasks) {
        println("🎶 Dadada! New task added to the melody! 🎶");
        println(task.toString());
        println("Lalalala! Now you have " + totalTasks + " tasks in your symphony! 🎵");
    }

    @Override
    public void showTaskDeleted(Task task, int totalTasks) {
        println("🎵 Lalalala! Task removed from the song! Dadada! 🎵");
        println(task.toString());
        println("Now your symphony has " + totalTasks + " tasks! 🎶");
    }

    @Override
    public void showTaskAddedSimple(String description) {
        println("🎵 Dadada! Added: " + description + " Lalalala! 🎵");
    }

    @Override
    public void showError(String... messages) {
        println("🎵 Dadada... Oops! Something went off-key! 🎵");
        for (String message : messages) {
            println("Lalalala: " + message);
        }
    }

    @Override
    public void showMatchingTasks(List<Task> tasks) {
        println("🎶 Lalalala! Here's your matching task melody! 🎶");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            println((i + 1) + "." + t.toString());
        }
        if (tasks.isEmpty()) {
            println("Dadada... no matching notes in your symphony! 🎵");
        }
    }
}



package jimmy.task;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages a collection of tasks in the Jimmy task management system.
 * Provides methods to add, remove, mark, and retrieve tasks from the list.
 * The TaskList maintains the order of tasks and provides size information.
 */
public class TaskList {
    /** The list of tasks */
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        assert task != null : "Task to add must not be null";
        tasks.add(task);
        assert tasks.size() > 0 : "Task list size should be positive after add";
    }

    /**
     * Adds multiple tasks to the list using varargs.
     *
     * @param tasks The tasks to add (varargs)
     */
    public void addTasks(Task... tasks) {
        for (Task task : tasks) {
            assert task != null : "Vararg task must not be null";
            this.tasks.add(task);
        }
    }

    /**
     * Removes a task from the list at the specified index.
     * The index is 1-based (user-friendly).
     *
     * @param index The 1-based index of the task to remove
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void removeTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for remove";
        tasks.remove(index);
    }

    /**
     * Marks a task as done at the specified index.
     * The index is 1-based (user-friendly).
     *
     * @param index The 1-based index of the task to mark as done
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void markTaskAsDone(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for mark";
        Task task = tasks.get(index);
        task.markAsDone();
    }

    /**
     * Marks a task as not done at the specified index.
     * The index is 1-based (user-friendly).
     *
     * @param index The 1-based index of the task to mark as not done
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void markTaskAsNotDone(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for unmark";
        Task task = tasks.get(index);
        task.markAsNotDone();
    }

    /**
     * Returns the task at the specified index.
     * The index is 1-based (user-friendly).
     *
     * @param index The 1-based index of the task to retrieve
     * @return The task at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task getTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for getTask";
        return tasks.get(index);
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return The number of tasks
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return A list containing all tasks
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Removes all tasks from the list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Finds tasks that match the given date string.
     * Searches through Deadline and Event tasks for date matches.
     * Supports multiple date formats: dd/MM/yyyy, yyyy-MM-dd, dd-MM-yyyy.
     *
     * @param dateStr The date string to search for
     * @return A list of tasks that match the given date
     */
    public List<Task> findTasksByDate(String dateStr) {
        List<Task> matchingTasks = new ArrayList<>();
        
        try {
            // Try to parse the date in various formats
            java.time.LocalDate searchDate = null;
            
            // Try "dd/MM/yyyy" format first (e.g., "2/12/2019")
            try {
                searchDate = java.time.LocalDate.parse(dateStr, 
                    java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy"));
            } catch (Exception e1) {
                // Try "yyyy-MM-dd" format
                try {
                    searchDate = java.time.LocalDate.parse(dateStr, 
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e2) {
                    // Try "dd-MM-yyyy" format
                    try {
                        searchDate = java.time.LocalDate.parse(dateStr, 
                            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    } catch (Exception e3) {
                        throw new IllegalArgumentException("Invalid date format. Use dd/MM/yyyy, yyyy-MM-dd, or dd-MM-yyyy");
                    }
                }
            }
            
            final java.time.LocalDate finalSearchDate = searchDate;
            
            for (Task task : tasks) {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    if (deadline.getBy().toLocalDate().equals(finalSearchDate)) {
                        matchingTasks.add(task);
                    }
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (event.getFrom().toLocalDate().equals(finalSearchDate) || 
                        event.getTo().toLocalDate().equals(finalSearchDate)) {
                        matchingTasks.add(task);
                    }
                }
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
        
        return matchingTasks;
    }

    public List<Task> findByKeyword(String keyword) {
        List<Task> matches = new ArrayList<>();
        String query = keyword.toLowerCase().trim();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(query)) {
                matches.add(task);
            }
        }
        return matches;
    }
}

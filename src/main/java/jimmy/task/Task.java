package jimmy.task;

/**
 * Represents a task in the Jimmy task management system.
 * This is the base class for all types of tasks (Todo, Deadline, Event).
 * Each task has a description and can be marked as done or not done.
 */
public class Task {
    /** Indicates whether the task has been completed */
    protected boolean isDone;
    
    /** The description or title of the task */
    protected String description;

    /**
     * Constructs a new Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the task's completion status.
     * Returns "X" if the task is done, " " (space) if not done.
     *
     * @return "X" for completed tasks, " " for incomplete tasks
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task suitable for file storage.
     * Format: "1 | description" for completed tasks, "0 | description" for incomplete tasks.
     *
     * @return A string representation for file storage
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the task.
     * This is the same as the description.
     *
     * @return The task description
     */
    public String toString() {
        return description;
    }
}

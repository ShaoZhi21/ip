package jimmy.task;

/**
 * Represents a simple todo task in the Jimmy task management system.
 * A todo task is a basic task with just a description and no specific time constraints.
 * Inherits from the Task class and provides todo-specific functionality.
 */
public class Todo extends Task {
    
    /**
     * Constructs a new Todo task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[T] " + "[" + super.getStatusIcon() + "]" + " " + super.toString();
    }
}

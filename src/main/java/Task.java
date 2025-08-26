public class Task {
    protected boolean isDone;
    protected String description;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon(){
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    public String toString() {
        return description;
    }
}

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index " + (index + 1) + " is out of bounds.");
        }
        return tasks.get(index);
    }
    
    public Task removeTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index " + (index + 1) + " is out of bounds.");
        }
        return tasks.remove(index);
    }
    
    public void markTaskAsDone(int index) throws IndexOutOfBoundsException {
        Task task = getTask(index);
        task.markAsDone();
    }
    
    public void markTaskAsNotDone(int index) throws IndexOutOfBoundsException {
        Task task = getTask(index);
        task.markAsNotDone();
    }
    
    public int getSize() {
        return tasks.size();
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
    
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    
    public void clear() {
        tasks.clear();
    }
    
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
}

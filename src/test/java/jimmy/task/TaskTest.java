package jimmy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    public void testTask() {
        Task task = new Task("Test Task");
        assertEquals("Test Task", task.getDescription());
    }
    
    @Test
    public void testTaskStatusChanges() {
        Task task = new Task("Another Task");
        assertEquals(" ", task.getStatusIcon()); 
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());    
        task.markAsNotDone();
        assertEquals(" ", task.getStatusIcon());
    }
}

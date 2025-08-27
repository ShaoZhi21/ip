package jimmy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

public class DeadlineTest {
    @Test
    public void testDeadlineCreation() {
        Deadline deadline = new Deadline("Submit report", "15/1/2024 1800");
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(" ", deadline.getStatusIcon());     
        assertEquals(LocalDateTime.of(2024, 1, 15, 18, 0), deadline.getBy()); 
    }
    
    @Test
    public void testDeadlineStatusChange() {
        Deadline deadline = new Deadline("Pay bills", "20/1/2024 1400");
        deadline.markAsDone();
        assertEquals("X", deadline.getStatusIcon()); 
        assertEquals(LocalDateTime.of(2024, 1, 20, 14, 0), deadline.getBy());
    }
}

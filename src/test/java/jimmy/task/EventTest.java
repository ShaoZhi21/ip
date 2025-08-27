package jimmy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

public class EventTest {
    @Test
    public void testEventCreation() {
        Event event = new Event("Team meeting", "15/1/2024 0900", "15/1/2024 1000");
        assertEquals("Team meeting", event.getDescription());
        assertEquals(" ", event.getStatusIcon()); 
        assertEquals(LocalDateTime.of(2024, 1, 15, 9, 0), event.getFrom()); 
        assertEquals(LocalDateTime.of(2024, 1, 15, 10, 0), event.getTo()); 
    }
    
    @Test
    public void testEventStatusChange() {
        Event event = new Event("Conference call", "16/1/2024 1400", "16/1/2024 1500");
        event.markAsDone();
        assertEquals("X", event.getStatusIcon()); 
        assertEquals(LocalDateTime.of(2024, 1, 16, 14, 0), event.getFrom()); 
        assertEquals(LocalDateTime.of(2024, 1, 16, 15, 0), event.getTo()); 
    }
}

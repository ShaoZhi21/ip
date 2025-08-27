package jimmy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void testTodoCreation() {
        Todo todo = new Todo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
        assertEquals(" ", todo.getStatusIcon()); 
    }
    
    @Test
    public void testTodoStatusChange() {
        Todo todo = new Todo("Read book");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon()); 
    }
}

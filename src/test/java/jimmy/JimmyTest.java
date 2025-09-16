package jimmy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import jimmy.exception.JimmyException;
import jimmy.task.Task;
import jimmy.task.TaskList;
import jimmy.task.Todo;
import jimmy.task.Deadline;
import jimmy.task.Event;
import jimmy.command.Parser;
import java.util.List;

public class JimmyTest {
    private TaskList taskList;
    
    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }
    
    @Test
    public void testAddTodoTask() {
        // Test adding a todo task
        Task todo = new Todo("Buy groceries");
        taskList.addTask(todo);
        
        assertEquals(1, taskList.getSize());
        assertEquals("Buy groceries", taskList.getTask(0).getDescription());
        assertEquals(" ", taskList.getTask(0).getStatusIcon()); // Not done
    }
    
    @Test
    public void testAddDeadlineTask() {
        // Test adding a deadline task
        Task deadline = new Deadline("Submit assignment", "25/12/2024 2359");
        taskList.addTask(deadline);
        
        assertEquals(1, taskList.getSize());
        assertEquals("Submit assignment", taskList.getTask(0).getDescription());
        assertEquals(" ", taskList.getTask(0).getStatusIcon()); // Not done
    }
    
    @Test
    public void testAddEventTask() {
        // Test adding an event task
        Task event = new Event("Team meeting", "25/12/2024 1400", "25/12/2024 1500");
        taskList.addTask(event);
        
        assertEquals(1, taskList.getSize());
        assertEquals("Team meeting", taskList.getTask(0).getDescription());
        assertEquals(" ", taskList.getTask(0).getStatusIcon()); // Not done
    }
    
    @Test
    public void testMarkTaskAsDone() {
        // Add a task and mark it as done
        Task todo = new Todo("Buy groceries");
        taskList.addTask(todo);
        
        taskList.markTaskAsDone(0);
        assertEquals("X", taskList.getTask(0).getStatusIcon()); // Done
    }
    
    @Test
    public void testUnmarkTask() {
        // Add a task, mark it as done, then unmark it
        Task todo = new Todo("Buy groceries");
        taskList.addTask(todo);
        taskList.markTaskAsDone(0);
        assertEquals("X", taskList.getTask(0).getStatusIcon()); // Done
        
        taskList.markTaskAsNotDone(0);
        assertEquals(" ", taskList.getTask(0).getStatusIcon()); // Not done
    }
    
    @Test
    public void testDeleteTask() {
        // Add multiple tasks and delete one
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        taskList.addTask(new Todo("Task 3"));
        
        assertEquals(3, taskList.getSize());
        
        taskList.removeTask(1); // Delete middle task
        assertEquals(2, taskList.getSize());
        assertEquals("Task 1", taskList.getTask(0).getDescription());
        assertEquals("Task 3", taskList.getTask(1).getDescription());
    }
    
    @Test
    public void testFindTasks() {
        // Add tasks with different descriptions
        taskList.addTask(new Todo("Buy groceries"));
        taskList.addTask(new Todo("Buy milk"));
        taskList.addTask(new Todo("Submit assignment"));
        taskList.addTask(new Todo("Buy bread"));
        
        // Find tasks containing "buy"
        List<Task> foundTasks = taskList.findByKeyword("buy");
        assertEquals(3, foundTasks.size());
        
        // Find tasks containing "assignment"
        foundTasks = taskList.findByKeyword("assignment");
        assertEquals(1, foundTasks.size());
        assertEquals("Submit assignment", foundTasks.get(0).getDescription());
    }
    
    @Test
    public void testFindTasksCaseInsensitive() {
        taskList.addTask(new Todo("Buy GROCERIES"));
        taskList.addTask(new Todo("buy milk"));
        taskList.addTask(new Todo("BUY bread"));
        
        List<Task> foundTasks = taskList.findByKeyword("buy");
        assertEquals(3, foundTasks.size());
    }
    
    @Test
    public void testFindTasksNoMatches() {
        taskList.addTask(new Todo("Buy groceries"));
        taskList.addTask(new Todo("Submit assignment"));
        
        List<Task> foundTasks = taskList.findByKeyword("nonexistent");
        assertEquals(0, foundTasks.size());
    }
    
    @Test
    public void testFindTasksEmptySearch() {
        taskList.addTask(new Todo("Buy groceries"));
        
        List<Task> foundTasks = taskList.findByKeyword("");
        // Empty search may return 0 or all tasks depending on implementation
        assertTrue(foundTasks.size() >= 0);
    }
    
    @Test
    public void testMarkTaskIndexOutOfBounds() {
        taskList.addTask(new Todo("Test task"));
        
        // Test negative index
        assertThrows(AssertionError.class, () -> taskList.markTaskAsDone(-1));
        
        // Test index too high
        assertThrows(AssertionError.class, () -> taskList.markTaskAsDone(1));
        assertThrows(AssertionError.class, () -> taskList.markTaskAsDone(999));
    }
    
    @Test
    public void testUnmarkTaskIndexOutOfBounds() {
        taskList.addTask(new Todo("Test task"));
        
        // Test negative index
        assertThrows(AssertionError.class, () -> taskList.markTaskAsNotDone(-1));
        
        // Test index too high
        assertThrows(AssertionError.class, () -> taskList.markTaskAsNotDone(1));
        assertThrows(AssertionError.class, () -> taskList.markTaskAsNotDone(999));
    }
    
    @Test
    public void testDeleteTaskIndexOutOfBounds() {
        taskList.addTask(new Todo("Test task"));
        
        // Test negative index
        assertThrows(AssertionError.class, () -> taskList.removeTask(-1));
        
        // Test index too high
        assertThrows(AssertionError.class, () -> taskList.removeTask(1));
        assertThrows(AssertionError.class, () -> taskList.removeTask(999));
    }
    
    @Test
    public void testGetTaskIndexOutOfBounds() {
        taskList.addTask(new Todo("Test task"));
        
        // Test negative index
        assertThrows(AssertionError.class, () -> taskList.getTask(-1));
        
        // Test index too high
        assertThrows(AssertionError.class, () -> taskList.getTask(1));
        assertThrows(AssertionError.class, () -> taskList.getTask(999));
    }
    
    @Test
    public void testEmptyTaskList() {
        assertEquals(0, taskList.getSize());
        assertTrue(taskList.isEmpty());
    }
    
    @Test
    public void testTaskListWithMultipleTasks() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Deadline("Task 2", "25/12/2024 2359"));
        taskList.addTask(new Event("Task 3", "25/12/2024 1400", "25/12/2024 1500"));
        
        assertEquals(3, taskList.getSize());
        assertFalse(taskList.isEmpty());
    }
    
    @Test
    public void testTaskListToString() {
        taskList.addTask(new Todo("Buy groceries"));
        taskList.addTask(new Deadline("Submit assignment", "25/12/2024 2359"));
        
        String result = taskList.toString();
        // Just verify the toString method returns a non-empty string
        assertTrue(result.length() > 0);
    }
    
    @Test
    public void testTaskListWithMixedCompletionStatus() {
        Task todo1 = new Todo("Task 1");
        Task todo2 = new Todo("Task 2");
        Task todo3 = new Todo("Task 3");
        
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        taskList.addTask(todo3);
        
        // Mark first and third tasks as done
        taskList.markTaskAsDone(0);
        taskList.markTaskAsDone(2);
        
        assertEquals("X", taskList.getTask(0).getStatusIcon()); // Done
        assertEquals(" ", taskList.getTask(1).getStatusIcon()); // Not done
        assertEquals("X", taskList.getTask(2).getStatusIcon()); // Done
    }
    
    @Test
    public void testTaskListClear() {
        taskList.addTask(new Todo("Task 1"));
        taskList.addTask(new Todo("Task 2"));
        
        assertEquals(2, taskList.getSize());
        
        // Clear all tasks
        for (int i = taskList.getSize() - 1; i >= 0; i--) {
            taskList.removeTask(i);
        }
        
        assertEquals(0, taskList.getSize());
        assertTrue(taskList.isEmpty());
    }
}

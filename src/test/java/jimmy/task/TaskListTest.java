package jimmy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;

public class TaskListTest {
    @Test
    public void testTaskListCreation() {
        List<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        assertEquals(0, taskList.getSize());
    }
    
    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Task task = new Task("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.getSize());
        assertEquals(task, taskList.getTask(0));
    }
}

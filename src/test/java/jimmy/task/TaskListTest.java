package jimmy.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * AI-assisted test suite for TaskList class.
 * Enhanced with comprehensive test cases generated using AI assistance
 * to ensure robust coverage of all TaskList functionality.
 */
public class TaskListTest {
    private TaskList taskList;
    private Task todoTask;
    private Deadline deadlineTask;
    private Event eventTask;

    @BeforeEach
    public void setUp() {
        // AI-assisted: Comprehensive test setup with different task types
        taskList = new TaskList();
        todoTask = new Todo("Buy groceries");
        deadlineTask = new Deadline("Submit report", LocalDateTime.of(2024, 12, 25, 23, 59));
        eventTask = new Event("Team meeting", 
            LocalDateTime.of(2024, 12, 20, 10, 0), 
            LocalDateTime.of(2024, 12, 20, 11, 0));
    }

    @Test
    public void testTaskListCreation() {
        List<Task> tasks = new ArrayList<>();
        TaskList taskList = new TaskList(tasks);
        assertEquals(0, taskList.getSize());
        assertTrue(taskList.isEmpty());
    }
    
    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Task task = new Task("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.getSize());
        assertEquals(task, taskList.getTask(0));
        assertFalse(taskList.isEmpty());
    }

    // AI-assisted: Test for varargs addTasks method
    @Test
    public void testAddMultipleTasks() {
        Task task1 = new Todo("Task 1");
        Task task2 = new Todo("Task 2");
        Task task3 = new Todo("Task 3");
        
        taskList.addTasks(task1, task2, task3);
        assertEquals(3, taskList.getSize());
        assertEquals(task1, taskList.getTask(0));
        assertEquals(task2, taskList.getTask(1));
        assertEquals(task3, taskList.getTask(2));
    }

    // AI-assisted: Test task removal functionality
    @Test
    public void testRemoveTask() {
        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);
        assertEquals(2, taskList.getSize());
        
        taskList.removeTask(0);
        assertEquals(1, taskList.getSize());
        assertEquals(deadlineTask, taskList.getTask(0));
    }

    // AI-assisted: Test task marking functionality
    @Test
    public void testMarkTaskAsDone() {
        taskList.addTask(todoTask);
        assertFalse(todoTask.isDone);
        
        taskList.markTaskAsDone(0);
        assertTrue(todoTask.isDone);
    }

    @Test
    public void testMarkTaskAsNotDone() {
        taskList.addTask(todoTask);
        todoTask.markAsDone();
        assertTrue(todoTask.isDone);
        
        taskList.markTaskAsNotDone(0);
        assertFalse(todoTask.isDone);
    }

    // AI-assisted: Test getAllTasks method
    @Test
    public void testGetAllTasks() {
        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);
        
        List<Task> allTasks = taskList.getAllTasks();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(todoTask));
        assertTrue(allTasks.contains(deadlineTask));
        
        // Verify it returns a copy, not the original list
        allTasks.clear();
        assertEquals(2, taskList.getSize());
    }

    // AI-assisted: Test clear functionality
    @Test
    public void testClear() {
        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);
        assertEquals(2, taskList.getSize());
        
        taskList.clear();
        assertEquals(0, taskList.getSize());
        assertTrue(taskList.isEmpty());
    }

    // AI-assisted: Test findByKeyword with various scenarios
    @Test
    public void testFindByKeyword() {
        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);
        taskList.addTask(eventTask);
        
        // Test single keyword match
        List<Task> results = taskList.findByKeyword("groceries");
        assertEquals(1, results.size());
        assertTrue(results.contains(todoTask));
        
        // Test case insensitive search
        results = taskList.findByKeyword("GROCERIES");
        assertEquals(1, results.size());
        
        // Test multi-word search
        results = taskList.findByKeyword("team meeting");
        assertEquals(1, results.size());
        assertTrue(results.contains(eventTask));
        
        // Test no matches
        results = taskList.findByKeyword("nonexistent");
        assertEquals(0, results.size());
        
        // Test null/empty input
        results = taskList.findByKeyword(null);
        assertEquals(0, results.size());
        
        results = taskList.findByKeyword("");
        assertEquals(0, results.size());
    }

    // AI-assisted: Test edge cases and error conditions
    @Test
    public void testEdgeCases() {
        // Test empty task list operations
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.getSize());
        
        // Note: TaskList uses assertions for bounds checking, not exceptions
        // These would throw AssertionError in debug mode, not IndexOutOfBoundsException
        // In production builds, assertions are typically disabled
    }
}

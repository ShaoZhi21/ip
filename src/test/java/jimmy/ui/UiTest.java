package jimmy.ui;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import jimmy.task.Task;

public class UiTest {
    @Test
    public void testUiCreation() {
        Ui ui = new Ui();
        assertNotNull(ui);
    }
    
    @Test
    public void testShowTaskList() {
        Ui ui = new Ui();
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Test task"));
        // This test just ensures the method doesn't throw an exception
        ui.showTaskList(tasks);
        assertNotNull(ui); // If we get here, no exception was thrown
    }
}

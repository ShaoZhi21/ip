package jimmy.storage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import jimmy.task.Task;

public class StorageTest {
    @Test
    public void testStorageCreation() {
        Storage storage = new Storage("test.txt");
        assertNotNull(storage);
    }
    
    @Test
    public void testLoadEmptyFile() {
        Storage storage = new Storage("nonexistent.txt");
        List<Task> tasks = storage.load();
        assertEquals(0, tasks.size()); 
    }
}

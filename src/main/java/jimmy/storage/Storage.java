package jimmy.storage;

import jimmy.task.Task;
import jimmy.task.Todo;
import jimmy.task.Deadline;
import jimmy.task.Event;
import jimmy.exception.JimmyException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the persistence of tasks in the Jimmy task management system.
 * Handles loading tasks from files and saving tasks to files.
 * Supports various task types (Todo, Deadline, Event) with proper serialization.
 */
public class Storage {
    /** The file path where tasks are stored */
    private final Path filePath;

    /**
     * Constructs a new Storage object with the specified file path.
     * The file path will be used for both loading and saving tasks.
     *
     * @param filePath The path to the file for storing tasks
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
        assert this.filePath != null : "Storage path must not be null";
    }

    /**
     * Loads tasks from the storage file.
     * Parses the file content and reconstructs Task objects.
     * Creates the file and directory if they don't exist.
     *
     * @return A list of loaded tasks, or an empty list if the file doesn't exist
     * @throws JimmyException if there are file access issues or data corruption
     */
    public List<Task> load() throws JimmyException {
        List<Task> tasks = new ArrayList<>();
        try {
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] parts = line.split(" \\| ");
                    if (parts.length < 3) {
                        continue;
                    }
                    
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];
                    Task task;
                    
                    if (type.equals("T")) {
                        task = new Todo(description);
                    } else if (type.equals("D")) {
                        if (parts.length < 4) {
                            continue; 
                        }
                        String by = parts[3];
                        try {
                            task = new Deadline(description, by);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Warning: Could not parse deadline date '" + by + "' for task: " + description);
                            continue; 
                        }
                    } else if (type.equals("E")) {
                        if (parts.length < 5) {
                            continue; 
                        }
                        String from = parts[3];
                        String to = parts[4];
                        try {
                            task = new Event(description, from, to);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Warning: Could not parse event dates for task: " + description);
                            continue; 
                        }
                    } else {
                        // unknown record type; skip defensively
                        continue; 
                    }
                    
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new JimmyException("Cannot access storage file: " + e.getMessage());
        } catch (SecurityException e) {
            throw new JimmyException("Permission denied accessing storage file: " + e.getMessage());
        } catch (Exception e) {
            throw new JimmyException("Unexpected error loading tasks: " + e.getMessage());
        }
        assert tasks != null : "Loaded tasks list must not be null";
        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Converts each task to its file representation and writes to disk.
     * Creates the file and directory if they don't exist.
     *
     * @param tasks The list of tasks to save
     * @throws JimmyException if there are file access issues
     */
    public void save(List<Task> tasks) throws JimmyException {
        if (tasks == null) {
            throw new JimmyException("Tasks list cannot be null.");
        }
        
        List<String> lines = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            if (task == null) {
                throw new JimmyException("Task in list cannot be null.");
            }
            lines.add(task.toFileString());
        }
        
        try {
            // Ensure directory exists
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, lines); 
        } catch (IOException e) {
            throw new JimmyException("Cannot write to storage file: " + e.getMessage());
        } catch (SecurityException e) {
            throw new JimmyException("Permission denied writing to storage file: " + e.getMessage());
        } catch (Exception e) {
            throw new JimmyException("Unexpected error saving tasks: " + e.getMessage());
        }
    }
}

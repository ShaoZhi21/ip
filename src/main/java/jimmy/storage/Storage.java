package jimmy.storage;

import jimmy.task.Task;
import jimmy.task.Todo;
import jimmy.task.Deadline;
import jimmy.task.Event;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Task> load() {
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
                        continue; 
                    }
                    
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    public void save(List<Task> tasks) {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toFileString());
        }
        try {
            Files.write(filePath, lines); 
        } catch (Exception e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}

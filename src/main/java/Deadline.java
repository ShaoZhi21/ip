import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            // Try to parse the format "2/12/2019 1800"
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (Exception e) {
            // If parsing fails, try alternative formats
            try {
                // Try "yyyy-MM-dd HH:mm" format
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (Exception e2) {
                try {
                    return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch (Exception e3) {
                    throw new IllegalArgumentException("Invalid date format: " + dateTimeStr + 
                        ". Expected format: dd/MM/yyyy HHmm, yyyy-MM-dd HH:mm, or ISO format");
                }
            }
        }
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String toString() {
        return "[D] " + "[" + super.getStatusIcon() + "]" + " " + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
}

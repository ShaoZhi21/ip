import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
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
                // If all parsing fails, throw a runtime exception
                throw new IllegalArgumentException("Invalid date format: " + dateTimeStr + 
                    ". Expected format: dd/MM/yyyy HHmm or yyyy-MM-dd HH:mm");
            }
        }
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + 
               from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | " + 
               to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String toString() {
        return "[E] " + "[" + super.getStatusIcon() + "]" + " " + super.toString() + 
               " (from: " + from.format(DISPLAY_FORMATTER) + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}

package jimmy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import jimmy.exception.JimmyException;

/**
 * Represents a deadline task in the Jimmy task management system.
 * A deadline task has a description and a specific due date and time.
 * Inherits from the Task class and provides deadline-specific functionality.
 */
public class Deadline extends Task {
    /** The due date and time for the deadline task */
    protected LocalDateTime by;
    
    /** Formatter for parsing input date strings in "d/M/yyyy HHmm" format */
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    
    /** Formatter for displaying dates in "MMM dd yyyy, h:mm a" format */
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs a new Deadline task with the given description and due date string.
     * The due date string will be parsed into a LocalDateTime object.
     *
     * @param description The description of the deadline task
     * @param by The due date and time as a string
     * @throws JimmyException if the date format is invalid or date doesn't exist
     */
    public Deadline(String description, String by) throws JimmyException {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Constructs a new Deadline task with the given description and due date.
     *
     * @param description The description of the deadline task
     * @param by The due date and time as a LocalDateTime object
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses a date string into a LocalDateTime object.
     * Tries multiple date formats in order of preference.
     * Validates that the date actually exists (e.g., Feb 30 is invalid).
     *
     * @param dateTimeStr The date string to parse
     * @return The parsed LocalDateTime object
     * @throws JimmyException if the date string cannot be parsed or date doesn't exist
     */
    private LocalDateTime parseDateTime(String dateTimeStr) throws JimmyException {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            throw new JimmyException("Date cannot be empty.");
        }
        
        String trimmed = dateTimeStr.trim();
        
        try {
            // Try to parse the format "2/12/2019 1800"
            return LocalDateTime.parse(trimmed, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            // If parsing fails, try alternative formats
            try {
                // Try "yyyy-MM-dd HH:mm" format
                return LocalDateTime.parse(trimmed, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException e2) {
                try {
                    // Try "dd-MM-yyyy HH:mm" format
                    return LocalDateTime.parse(trimmed, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                } catch (DateTimeParseException e3) {
                    try {
                        // Try ISO format
                        return LocalDateTime.parse(trimmed, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    } catch (DateTimeParseException e4) {
                        throw new JimmyException("Invalid date format: '" + trimmed + 
                            "'. Expected formats: dd/MM/yyyy HHmm, yyyy-MM-dd HH:mm, dd-MM-yyyy HH:mm, or ISO format");
                    }
                }
            }
        }
    }

    /**
     * Returns the due date and time for this deadline task.
     *
     * @return The due date and time as a LocalDateTime object
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task suitable for file storage.
     * Format: "D | status | description | dueDateTime"
     *
     * @return A string representation for file storage
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Returns a string representation of the deadline task for display.
     * Format: "[D] [status] description (by: formattedDueDate)"
     *
     * @return A formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D] " + "[" + super.getStatusIcon() + "]" + " " + super.toString() + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
}

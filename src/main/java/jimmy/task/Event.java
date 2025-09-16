package jimmy.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import jimmy.exception.JimmyException;

/**
 * Represents an event task in the Jimmy task management system.
 * An event task has a description, start time, and end time.
 * Inherits from the Task class and provides event-specific functionality.
 */
public class Event extends Task {
    /** The start date and time of the event */
    protected LocalDateTime from;
    
    /** The end date and time of the event */
    protected LocalDateTime to;
    
    /** Formatter for parsing input date strings in "d/M/yyyy HHmm" format */
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    
    /** Formatter for displaying dates in "MMM dd yyyy, h:mm a" format */
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs a new Event task with the given description, start time, and end time.
     * The date strings will be parsed into LocalDateTime objects.
     * Validates that start time is before end time.
     *
     * @param description The description of the event task
     * @param from The start date and time as a string
     * @param to The end date and time as a string
     * @throws JimmyException if date format is invalid or start time is not before end time
     */
    public Event(String description, String from, String to) throws JimmyException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
        
        // Validate that start time is before end time
        if (!this.from.isBefore(this.to)) {
            throw new JimmyException("Start time must be before end time. Start: " + 
                this.from.format(DISPLAY_FORMATTER) + ", End: " + this.to.format(DISPLAY_FORMATTER));
        }
    }

    /**
     * Constructs a new Event task with the given description, start time, and end time.
     *
     * @param description The description of the event task
     * @param from The start date and time as a LocalDateTime object
     * @param to The end date and time as a LocalDateTime object
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
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
     * Returns the start date and time of this event task.
     *
     * @return The start date and time as a LocalDateTime object
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date and time of this event task.
     *
     * @return The end date and time as a LocalDateTime object
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the event task suitable for file storage.
     * Format: "E | status | description | startDateTime | endDateTime"
     *
     * @return A string representation for file storage
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + 
               from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " | " + 
               to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Returns a string representation of the event task for display.
     * Format: "[E] [status] description (from: startDateTime to: endDateTime)"
     *
     * @return A formatted string representation of the event task
     */
    @Override
    public String toString() {
        return "[E] " + "[" + super.getStatusIcon() + "]" + " " + super.toString() + 
               " (from: " + from.format(DISPLAY_FORMATTER) + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}

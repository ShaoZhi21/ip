package jimmy.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import jimmy.exception.JimmyException;

public class ParserTest {
    @Test
    public void testParseCommand() {
        Parser.ParsedCommand parsed = Parser.parseCommand("todo buy milk");
        assertEquals("todo", parsed.command);
        assertEquals("buy milk", parsed.fullInput);
    }
    
    @Test
    public void testParseCommandWithNoArguments() {
        Parser.ParsedCommand parsed = Parser.parseCommand("list");
        assertEquals("list", parsed.command);
        assertEquals("", parsed.fullInput);
    }
    
    // Test input validation and error handling
    @Test
    public void testParseCommandNullInput() {
        assertThrows(JimmyException.class, () -> Parser.parseCommand(null));
    }
    
    @Test
    public void testParseCommandEmptyInput() {
        assertThrows(JimmyException.class, () -> Parser.parseCommand(""));
        assertThrows(JimmyException.class, () -> Parser.parseCommand("   "));
    }
    
    @Test
    public void testParseCommandInvalidCharacters() {
        assertThrows(JimmyException.class, () -> Parser.parseCommand("todo buy@milk"));
        assertThrows(JimmyException.class, () -> Parser.parseCommand("todo buy#milk"));
        assertThrows(JimmyException.class, () -> Parser.parseCommand("todo buy$milk"));
    }
    
    @Test
    public void testParseCommandValidCharacters() {
        // Should not throw exceptions
        Parser.parseCommand("todo buy milk, eggs and bread!");
        Parser.parseCommand("deadline submit assignment /by 25/12/2024 2359");
        Parser.parseCommand("event meeting /from 25/12/2024 1400 /to 25/12/2024 1500");
    }
    
    @Test
    public void testParseCommandNormalizeSpaces() {
        Parser.ParsedCommand parsed = Parser.parseCommand("  todo   buy   milk  ");
        assertEquals("todo", parsed.command);
        assertEquals("buy milk", parsed.fullInput);
    }
    
    // Test command validation methods
    @Test
    public void testIsValidTodoCommand() {
        assertTrue(Parser.isValidTodoCommand("buy milk"));
        assertFalse(Parser.isValidTodoCommand(""));
        assertFalse(Parser.isValidTodoCommand("   "));
    }
    
    @Test
    public void testIsValidDeadlineCommand() {
        assertTrue(Parser.isValidDeadlineCommand("submit assignment /by 2024-12-25"));
        assertThrows(JimmyException.class, () -> Parser.isValidDeadlineCommand(""));
        assertThrows(JimmyException.class, () -> Parser.isValidDeadlineCommand("submit assignment"));
        assertThrows(JimmyException.class, () -> Parser.isValidDeadlineCommand("submit assignment /by"));
        assertThrows(JimmyException.class, () -> Parser.isValidDeadlineCommand("/by 2024-12-25"));
    }
    
    @Test
    public void testIsValidDeadlineCommandDuplicateKeywords() {
        assertThrows(JimmyException.class, () -> 
            Parser.isValidDeadlineCommand("submit assignment /by 2024-12-25 /by 2024-12-26"));
    }
    
    @Test
    public void testIsValidEventCommand() {
        assertTrue(Parser.isValidEventCommand("meeting /from 2pm /to 3pm"));
        assertThrows(JimmyException.class, () -> Parser.isValidEventCommand(""));
        assertThrows(JimmyException.class, () -> Parser.isValidEventCommand("meeting"));
        assertThrows(JimmyException.class, () -> Parser.isValidEventCommand("meeting /from 2pm"));
        assertThrows(JimmyException.class, () -> Parser.isValidEventCommand("meeting /to 3pm"));
    }
    
    @Test
    public void testIsValidEventCommandDuplicateKeywords() {
        assertThrows(JimmyException.class, () -> 
            Parser.isValidEventCommand("meeting /from 2pm /from 3pm /to 4pm"));
        assertThrows(JimmyException.class, () -> 
            Parser.isValidEventCommand("meeting /from 2pm /to 3pm /to 4pm"));
    }
    
    // Test extraction methods
    @Test
    public void testExtractDeadlineDescription() {
        assertEquals("submit assignment", Parser.extractDeadlineDescription("submit assignment /by 2024-12-25"));
        assertEquals("buy groceries", Parser.extractDeadlineDescription("buy groceries /by tomorrow"));
    }
    
    @Test
    public void testExtractDeadlineDate() {
        assertEquals("2024-12-25", Parser.extractDeadlineDate("submit assignment /by 2024-12-25"));
        assertEquals("tomorrow", Parser.extractDeadlineDate("buy groceries /by tomorrow"));
    }
    
    @Test
    public void testExtractEventDescription() {
        assertEquals("team meeting", Parser.extractEventDescription("team meeting /from 2pm /to 3pm"));
    }
    
    @Test
    public void testExtractEventFrom() {
        assertEquals("2pm", Parser.extractEventFrom("team meeting /from 2pm /to 3pm"));
    }
    
    @Test
    public void testExtractEventTo() {
        assertEquals("3pm", Parser.extractEventTo("team meeting /from 2pm /to 3pm"));
    }
    
    // Test task index parsing
    @Test
    public void testParseTaskIndex() {
        assertEquals(0, Parser.parseTaskIndex("1"));
        assertEquals(2, Parser.parseTaskIndex("3"));
    }
    
    @Test
    public void testParseTaskIndexInvalid() {
        assertThrows(JimmyException.class, () -> Parser.parseTaskIndex("abc"));
        assertThrows(JimmyException.class, () -> Parser.parseTaskIndex("0"));
        assertThrows(JimmyException.class, () -> Parser.parseTaskIndex("-1"));
        assertThrows(JimmyException.class, () -> Parser.parseTaskIndex(""));
    }
    
    // Test utility methods
    @Test
    public void testContainsAllKeywords() {
        assertTrue(Parser.containsAllKeywords("meeting /from 2pm /to 3pm", "/from", "/to"));
        assertFalse(Parser.containsAllKeywords("meeting /from 2pm", "/from", "/to"));
        assertTrue(Parser.containsAllKeywords("deadline /by tomorrow", "/by"));
    }
}

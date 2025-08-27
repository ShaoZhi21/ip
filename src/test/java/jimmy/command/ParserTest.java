package jimmy.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
}

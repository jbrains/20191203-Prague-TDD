package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConsumeTextCommandsTest {
    private List<String> commandsReceived = new ArrayList<>();

    @Test
    void oneCommand() throws Exception {
        checkLinesConsumedAsCommands(
                Arrays.asList("::command::"),
                Arrays.asList("::command::"));
    }

    @Test
    void noCommands() throws Exception {
        checkLinesConsumedAsCommands(
                Collections.emptyList(),
                Collections.emptyList());
    }

    @Test
    void severalCommands() throws Exception {
        checkLinesConsumedAsCommands(
                Arrays.asList("::command 1::", "::command 2::", "::command 3::"),
                Arrays.asList("::command 1::", "::command 2::", "::command 3::"));
    }

    @Test
    void canonicalizeLines() throws Exception {
        final CanonicalizeLines canonicalizeLines = Mockito.mock(CanonicalizeLines.class);

        consumeText(ignored -> {}, canonicalizeLines, new StreamLinesFromReader(new StringReader("::anything not empty::")));

        // Just try to canonicalize the lines!
        // SMELL This expectation seems silly!
        Mockito.verify(canonicalizeLines).canonicalizeLines(Mockito.any());
    }

    private void checkLinesConsumedAsCommands(List<String> lines, List<String> expectedCommands) throws IOException {
        consumeText(
                command -> this.commandsReceived.add(command),
                rawLines -> rawLines,
                new StreamLinesFromReader(new StringReader(unlines(lines))));

        Assertions.assertEquals(expectedCommands, commandsReceived, "Wrong commands received.");
    }

    private void consumeText(Consumer<String> handleCommand, CanonicalizeLines canonicalizeLines, final StreamLinesFromReader streamLinesFromReader) throws IOException {
        canonicalizeLines.canonicalizeLines(streamLinesFromReader.streamAsLines())
                .forEach(handleCommand::accept);
    }

    // REFACTOR Move to a generic text-processing library
    private static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

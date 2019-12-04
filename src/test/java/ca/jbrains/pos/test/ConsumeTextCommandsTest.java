package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
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

        consumeText(ignored -> {}, new StringReader("::anything not empty::"), canonicalizeLines);

        // Just try to canonicalize the lines!
        // SMELL This expectation seems silly!
        Mockito.verify(canonicalizeLines).canonicalizeLines(Mockito.any());
    }

    private void checkLinesConsumedAsCommands(List<String> lines, List<String> expectedCommands) throws IOException {
        final CanonicalizeLines canonicalizeLines = Mockito.mock(CanonicalizeLines.class);
        // SMELL A very complicated way to say "don't change the incoming lines".
        Mockito.when(canonicalizeLines.canonicalizeLines(Mockito.any())).thenReturn(lines.stream());

        consumeText(
                command -> this.commandsReceived.add(command),
                new StringReader(unlines(lines)),
                canonicalizeLines);

        Assertions.assertEquals(expectedCommands, commandsReceived, "Wrong commands received.");
    }

    private void consumeText(Consumer<String> handleCommand, Reader commandSource, CanonicalizeLines canonicalizeLines) throws IOException {
        canonicalizeLines.canonicalizeLines(new BufferedReader(commandSource).lines())
                .forEach(handleCommand::accept);
    }

    // REFACTOR Move to a generic text-processing library
    private static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

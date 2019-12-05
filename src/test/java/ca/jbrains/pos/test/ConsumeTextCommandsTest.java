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
import java.util.stream.Stream;

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

    private void checkLinesConsumedAsCommands(List<String> lines, List<String> expectedCommands) {
        consumeText(
                new StreamLinesFromReader(new StringReader(StreamLinesFromReaderTest.unlines(lines))), rawLines -> rawLines, command -> this.commandsReceived.add(command)
        );

        Assertions.assertEquals(expectedCommands, commandsReceived, "Wrong commands received.");
    }

    private void consumeText(final StreamLines streamLines, CanonicalizeLines canonicalizeLines, Consumer<String> handleCommand) {
        dispatchCommands(
                handleCommand,
                canonicalizeLines.canonicalizeLines(
                        streamLines.streamAsLines()));
    }

    private void dispatchCommands(Consumer<String> handleCommand, Stream<String> canonicalLines) {
        canonicalLines.forEach(handleCommand::accept);
    }
}

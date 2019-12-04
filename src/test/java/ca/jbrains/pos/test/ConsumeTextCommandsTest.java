package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void severalCommandsSomeAreEmpty() throws Exception {
        checkLinesConsumedAsCommands(
                Arrays.asList("::command 1::", "", "::command 2::", "", "", "::command 3::"),
                Arrays.asList("::command 1::", "::command 2::", "::command 3::"));
    }

    private void checkLinesConsumedAsCommands(List<String> lines, List<String> expectedCommands) throws IOException {
        consumeText(
                command -> this.commandsReceived.add(command),
                new StringReader(unlines(lines)));

        Assertions.assertEquals(expectedCommands, commandsReceived, "Wrong commands received.");
    }

    private void consumeText(Consumer<String> handleCommand, Reader commandSource) throws IOException {
        new BufferedReader(commandSource).lines()
                .filter(line -> !line.isEmpty())
                .forEach(handleCommand::accept);
    }

    // REFACTOR Move to a generic text-processing library
    private static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

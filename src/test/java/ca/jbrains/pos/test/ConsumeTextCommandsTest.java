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
import java.util.stream.Stream;

public class ConsumeTextCommandsTest {
    private boolean commandReceived = false;
    private List<String> commandsReceived = new ArrayList<>();

    @Test
    void oneCommand() throws Exception {
        final Consumer<String> expectCommand = command -> {
            Assertions.assertEquals("::command::", command);
            ConsumeTextCommandsTest.this.commandReceived = true;
        };

        consumeText(expectCommand, new StringReader(unlines(Arrays.asList("::command::"))));

        Assertions.assertEquals(true, commandReceived, "Command not received.");
    }

    @Test
    void noCommands() throws Exception {
        consumeText(
                command -> Assertions.fail(String.format("Unexpected command received: [%s].", command)),
                new StringReader(unlines(Collections.emptyList())));
    }

    @Test
    void severalCommands() throws Exception {
        consumeText(
                command -> ConsumeTextCommandsTest.this.commandsReceived.add(command),
                new StringReader(unlines(Arrays.asList("::command 1::", "::command 2::", "::command 3::"))));

        Assertions.assertEquals(
                Arrays.asList("::command 1::", "::command 2::", "::command 3::"),
                commandsReceived,
                "Wrong commands received.");
    }

    private void consumeText(Consumer<String> handleCommand, Reader commandSource) throws IOException {
        new BufferedReader(commandSource).lines().forEach(handleCommand::accept);
    }

    // REFACTOR Move to a generic text-processing library
    private static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

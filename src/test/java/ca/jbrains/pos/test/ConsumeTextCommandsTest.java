package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConsumeTextCommandsTest {
    private boolean commandReceived = false;

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

    private void consumeText(Consumer<String> handleCommand, Reader commandSource) throws IOException {
        final String line = new BufferedReader(commandSource).readLine();
        if (line != null)
            handleCommand.accept(line);
    }

    // REFACTOR Move to a generic text-processing library
    private static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

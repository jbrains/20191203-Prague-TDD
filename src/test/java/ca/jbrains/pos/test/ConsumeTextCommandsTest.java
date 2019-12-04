package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.Arrays;
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

    private void consumeText(Consumer<String> handleCommand, Reader commandSource) throws IOException {
        handleCommand.accept(new BufferedReader(commandSource).readLine());
    }

    // REFACTOR Move to a generic text-processing library
    private static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}

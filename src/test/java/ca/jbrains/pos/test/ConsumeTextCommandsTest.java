package ca.jbrains.pos.test;

import java.util.function.Consumer;

public class ConsumeTextCommandsTest {
    private void consumeText(final StreamLines streamLines, CanonicalizeLines canonicalizeLines, Consumer<String> handleCommand) {
        canonicalizeLines.canonicalizeLines(streamLines.streamAsLines()).forEach(handleCommand::accept);
    }
}

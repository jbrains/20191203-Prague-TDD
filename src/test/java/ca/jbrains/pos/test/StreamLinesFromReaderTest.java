package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StreamLinesFromReaderTest {
    @Test
    void empty() throws Exception {
        assertLinesStreamedAs(Collections.emptyList(), "");
    }

    @Test
    void trainingLineTerminatorNotNeeded() throws Exception {
        assertLinesStreamedAs(Collections.singletonList("::line 1::"), "::line 1::");
    }

    @Test
    void ignoresTrailingLineTerminator() throws Exception {
        assertLinesStreamedAs(Collections.singletonList("::line 1::"), "::line 1::" + System.lineSeparator());
    }

    private static void assertLinesStreamedAs(final List<Object> expectedLines, final String text) {
        final StreamLinesFromReader streamLinesFromReader = new StreamLinesFromReader(new StringReader(text));
        Assertions.assertEquals(
                expectedLines,
                streamLinesFromReader.streamAsLines().collect(Collectors.toList()));
    }
}

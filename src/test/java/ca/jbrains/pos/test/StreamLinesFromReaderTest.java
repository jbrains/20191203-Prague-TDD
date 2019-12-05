package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StreamLinesFromReaderTest {
    @Test
    void empty() throws Exception {
        assertLinesStreamedAs(Collections.emptyList(), "");
    }

    @Test
    void oneLine() throws Exception {
        assertLinesStreamedAs(Collections.singletonList("::line 1::"), "::line 1::");
    }

    @Test
    void severalLines() throws Exception {
        assertLinesStreamedAs(
                Arrays.asList("::line 1::", "::line 2::", "::line 3::"),
                unlines(Arrays.asList("::line 1::", "::line 2::", "::line 3::")));
    }

    @Test
    void ignoresTrailingLineTerminator() throws Exception {
        assertLinesStreamedAs(
                Arrays.asList("::line 1::", "::line 2::", "::line 3::"),
                unlines(Arrays.asList("::line 1::", "::line 2::", "::line 3::")) + System.lineSeparator());
    }

    @Test
    void preserveEmptyLines() throws Exception {
        assertLinesStreamedAs(
                Arrays.asList("::line 1::", "", "::line 2::", "", "::line 3::"),
                unlines(Arrays.asList("::line 1::", "", "::line 2::", "", "::line 3::")));
    }

    @Test
    void throwsAwayTrailingLineSeparator() throws Exception {
        // It looks like 6 empty lines, but it's really 5 empty lines plus a trailing line separator
        assertLinesStreamedAs(
                nEmptyLines(5),
                unlines(nEmptyLines(6)));

        assertLinesStreamedAs(
                nEmptyLines(5),
                unlines(nEmptyLines(5)) + System.lineSeparator());
    }

    private List<String> nEmptyLines(final int n) {
        return Collections.nCopies(n, "");
    }

    // REFACTOR Move to a generic text-processing library
    public static String unlines(List<String> lines) {
        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    private static void assertLinesStreamedAs(final List<String> expectedLines, final String text) {
        final StreamLinesFromReader streamLinesFromReader = new StreamLinesFromReader(new StringReader(text));
        Assertions.assertEquals(
                expectedLines,
                streamLinesFromReader.streamAsLines().collect(Collectors.toList()));
    }
}

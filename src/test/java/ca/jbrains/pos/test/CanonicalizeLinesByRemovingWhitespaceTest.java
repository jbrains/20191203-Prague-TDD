package ca.jbrains.pos.test;

import ca.jbrains.pos.CanonicalizeLinesByRemovingWhitespace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CanonicalizeLinesByRemovingWhitespaceTest {
    @Test
    void ignoreEmptyCommands() throws Exception {
        checkLinesCanonicalizedAs(
                Arrays.asList("::command 1::", "", "::command 2::", "", "", "::command 3::"),
                Arrays.asList("::command 1::", "::command 2::", "::command 3::"));
    }

    @Test
    void trimExtraWhitespace() throws Exception {
        checkLinesCanonicalizedAs(
                Arrays.asList("    ::command 1::   ", "::command 2::     ", "\t::command 3::\f"),
                Arrays.asList("::command 1::", "::command 2::", "::command 3::"));
    }

    @Test
    void treatWhitespaceOnlyLinesAsEmpty() throws Exception {
        checkLinesCanonicalizedAs(
                Arrays.asList("   \t\t   \r   ", "    "),
                Collections.emptyList());

    }

    private void checkLinesCanonicalizedAs(List<String> lines, List<String> expectedCanonicalLines) {
        Assertions.assertEquals(
                expectedCanonicalLines,
                new CanonicalizeLinesByRemovingWhitespace().canonicalizeLines(lines.stream()).collect(Collectors.toList()));
    }
}
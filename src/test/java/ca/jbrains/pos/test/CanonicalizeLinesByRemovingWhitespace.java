package ca.jbrains.pos.test;

import java.util.stream.Stream;

public class CanonicalizeLinesByRemovingWhitespace {
    public Stream<String> canonicalizeLines(Stream<String> lines) {
        return lines.map(String::trim).filter(line -> !line.isEmpty());
    }
}
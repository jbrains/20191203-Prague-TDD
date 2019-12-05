package ca.jbrains.pos;

import java.util.stream.Stream;

public class CanonicalizeLinesByRemovingWhitespace implements CanonicalizeLines {
    @Override
    public Stream<String> canonicalizeLines(Stream<String> lines) {
        return lines.map(String::trim).filter(line -> !line.isEmpty());
    }
}
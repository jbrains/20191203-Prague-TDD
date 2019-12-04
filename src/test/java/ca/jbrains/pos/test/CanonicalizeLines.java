package ca.jbrains.pos.test;

import java.util.stream.Stream;

public interface CanonicalizeLines {
    Stream<String> canonicalizeLines(Stream<String> lines);
}

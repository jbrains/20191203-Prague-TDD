package ca.jbrains.pos.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class StreamLinesFromReader {
    private final Reader linesSource;

    public StreamLinesFromReader(Reader linesSource) {
        this.linesSource = linesSource;
    }

    Stream<String> streamAsLines() {
        return new BufferedReader(linesSource).lines();
    }
}
package ca.jbrains.pos.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

public class StreamLinesFromReader implements StreamLines {
    private final Reader linesSource;

    public StreamLinesFromReader(Reader linesSource) {
        this.linesSource = linesSource;
    }

    @Override
    public Stream<String> streamAsLines() {
        return new BufferedReader(linesSource).lines();
    }
}
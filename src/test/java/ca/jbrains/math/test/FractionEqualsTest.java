package ca.jbrains.math.test;

import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;

public class FractionEqualsTest {
    @Data
    Iterable<Tuple.Tuple2<Fraction, Fraction>> unequalExamples() {
        return Table.of(
                Tuple.of(
                        new Fraction(1, 2),
                        new Fraction(1, 3)),
                Tuple.of(
                        new Fraction(3, 7),
                        new Fraction(4, 7)),
                Tuple.of(
                        new Fraction(5, 7),
                        new Fraction(26, 35))
        );
    }

    @Property
    @FromData("unequalExamples")
    void shouldNotBeEqual(@ForAll Fraction first, @ForAll Fraction second) {
        Assertions.assertNotEquals(first, second);
    }
}

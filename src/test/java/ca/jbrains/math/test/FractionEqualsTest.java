package ca.jbrains.math.test;

import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;

public class FractionEqualsTest {
    @Data
    Iterable<Tuple.Tuple2<AddFractionsTest.Fraction, AddFractionsTest.Fraction>> unequalExamples() {
        return Table.of(
                Tuple.of(
                        new AddFractionsTest.Fraction(1, 2),
                        new AddFractionsTest.Fraction(1, 3))
        );
    }

    @Property
    @FromData("unequalExamples")
    void shouldNotBeEqual(@ForAll AddFractionsTest.Fraction first, @ForAll AddFractionsTest.Fraction second) {
        Assertions.assertNotEquals(first, second);
    }
}

package ca.jbrains.math.test;

import net.jqwik.api.*;
import net.jqwik.api.Tuple.Tuple2;
import net.jqwik.api.arbitraries.IntegerArbitrary;
import org.junit.jupiter.api.Assertions;

public class FractionEqualsTest {
    @Data
    Iterable<Tuple2<Fraction, Fraction>> unequalExamples() {
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

    @Provide
    Arbitrary<Tuple2<Fraction, Fraction>> equalPairsOfFractions() {
        final IntegerArbitrary numerators = Arbitraries.integers();
        final IntegerArbitrary denominators = Arbitraries.integers().greaterOrEqual(1);
        final Arbitrary<Integer> multipliers = Arbitraries.integers().filter(n -> n != 0);
        return Combinators.combine(numerators, denominators, multipliers).as(
                (n, d, m) -> Tuple.of(new Fraction(n, d), new Fraction(n * m, d * m)));
    }

    @Property
    void shouldBeEqual(@ForAll("equalPairsOfFractions") Tuple2<Fraction, Fraction> equalPairs) {
        Assertions.assertEquals(equalPairs.get1(), equalPairs.get2());
    }
}

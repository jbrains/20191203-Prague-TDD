package ca.jbrains.math.test;

import net.jqwik.api.*;
import net.jqwik.api.Tuple.Tuple2;
import net.jqwik.api.arbitraries.IntegerArbitrary;
import org.junit.jupiter.api.Assertions;

public class FractionEqualsTest {
    @Provide
    Arbitrary<Tuple2<Fraction, Fraction>> unequalPairsOfFractions() {
        return Arbitraries.oneOf(sameDenominatorDifferentNumerators(), otherExamples());
    }

    @Property(tries = 50000)
    void shouldNotBeEqual(@ForAll("unequalPairsOfFractions") Tuple2<Fraction, Fraction> equalPairs) {
        Assertions.assertNotEquals(equalPairs.get1(), equalPairs.get2());
    }

    @Provide
    Arbitrary<Tuple2<Fraction, Fraction>> equalPairsOfFractions() {
        final IntegerArbitrary numerators = integersNotTooBig();
        final IntegerArbitrary denominators = integersNotTooBig().greaterOrEqual(1);
        final Arbitrary<Integer> multipliers = nonZeroIntegersNotTooBig();
        return Combinators.combine(numerators, denominators, multipliers).as(
                (n, d, m) -> Tuple.of(new Fraction(n, d), new Fraction(n * m, d * m)));
    }

    @Property(tries = 50000)
    void shouldBeEqual(@ForAll("equalPairsOfFractions") Tuple2<Fraction, Fraction> equalPairs) {
        Assertions.assertEquals(equalPairs.get1(), equalPairs.get2());
    }

    Arbitrary<Tuple2<Fraction, Fraction>> sameDenominatorDifferentNumerators() {
        return Combinators.combine(integersNotTooBig(), nonZeroIntegersNotTooBig(), nonZeroIntegersNotTooBig()).as(
                (numerator, denominator, offset) ->
                        Tuple.of(
                                new Fraction(numerator, denominator),
                                new Fraction(numerator + offset, denominator)));
    }

    Arbitrary<Tuple2<Fraction, Fraction>> otherExamples() {
        return Combinators.combine(integersNotTooBig(), nonZeroIntegersNotTooBig(), nonZeroIntegersNotTooBig(), nonZeroIntegersNotTooBig()).as(
                (numerator, denominator, multiplier, offset) ->
                        Tuple.of(
                                new Fraction(numerator, denominator),
                                new Fraction(numerator * multiplier + offset, denominator * multiplier)));
    }

    // Avoid overflow in a*d == b*c: let a, b, c, d < sqrt(MAX_VALUE)
    public static final int LIMIT = (int) Math.floor(Math.sqrt(Integer.MAX_VALUE));

    private IntegerArbitrary integersNotTooBig() {
        return Arbitraries.integers().between(-LIMIT, LIMIT);
    }

    private Arbitrary<Integer> nonZeroIntegersNotTooBig() {
        return integersNotTooBig().filter(n -> n != 0);
    }
}

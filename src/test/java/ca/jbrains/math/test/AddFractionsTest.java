package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assertions.assertEquals(new Fraction(0), sum);
    }

    @Test
    void notZeroPlusZero() throws Exception {
        Fraction sum = new Fraction(5).plus(new Fraction(0));
        Assertions.assertEquals(new Fraction(5), sum);
    }

    @Test
    void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(8));
        Assertions.assertEquals(new Fraction(8), sum);
    }

    @Test
    void notZeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(10).plus(new Fraction(4));
        Assertions.assertEquals(new Fraction(14), sum);
    }

    @Test
    void sameDenominators() throws Exception {
        Fraction sum = new Fraction(1, 5).plus(new Fraction(3, 5));
        Assertions.assertEquals(new Fraction(4, 5), sum);
    }

    @Test
    void relativelyPrimeDenominators() throws Exception {
        Fraction sum = new Fraction(2, 7).plus(new Fraction(4, 5));
        Assertions.assertEquals(new Fraction(38, 35), sum);
    }

    @Test
    void nonIntegerFractionsAddToAnInteger() throws Exception {
        Fraction sum = new Fraction(1, 4).plus(new Fraction(3, 4));
        Assertions.assertEquals(new Fraction(1), sum);
    }

    @Test
    void denominatorsWithACommonFactorButWhereNeitherIsAMultipleOfTheOther() throws Exception {
        Fraction sum = new Fraction(5, 6).plus(new Fraction(2, 9));
        Assertions.assertEquals(new Fraction(19, 18), sum);
    }

    public static class Fraction {
        private int numerator;
        private int denominator;

        public Fraction(int integerValue) {
            this(integerValue, 1);
        }

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        public Fraction plus(Fraction that) {
            if (this.denominator == that.denominator)
                return new Fraction(
                        this.numerator + that.numerator,
                        this.denominator);
            else
                return new Fraction(
                        this.numerator * that.denominator + this.denominator * that.numerator,
                        this.denominator * that.denominator);
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Fraction) {
                Fraction that = (Fraction) other;
                return this.numerator * that.denominator == this.numerator * that.denominator;
            }
            else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return -762;
        }

        @Override
        public String toString() {
            return String.format("%d/%d", numerator, denominator);
        }
    }
}

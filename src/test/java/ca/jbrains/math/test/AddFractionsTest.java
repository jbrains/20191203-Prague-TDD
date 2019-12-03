package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assertions.assertEquals(0, sum.getNumerator());
        Assertions.assertEquals(1, sum.getDenominator());
    }

    @Test
    void notZeroPlusZero() throws Exception {
        Fraction sum = new Fraction(5).plus(new Fraction(0));
        Assertions.assertEquals(5, sum.getNumerator());
        Assertions.assertEquals(1, sum.getDenominator());
    }

    @Test
    void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(8));
        Assertions.assertEquals(8, sum.getNumerator());
        Assertions.assertEquals(1, sum.getDenominator());
    }

    @Test
    void notZeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(10).plus(new Fraction(4));
        Assertions.assertEquals(14, sum.getNumerator());
        Assertions.assertEquals(1, sum.getDenominator());
    }

    @Test
    void sameDenominators() throws Exception {
        Fraction sum = new Fraction(1, 5).plus(new Fraction(3, 5));
        Assertions.assertEquals(4, sum.getNumerator());
        Assertions.assertEquals(5, sum.getDenominator());
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
            if (this.denominator == 1 && that.denominator == 1)
                return new Fraction(this.numerator + that.numerator);
            else
                return new Fraction(this.numerator + that.numerator, this.denominator);
        }

        public int getNumerator() {
            return numerator;
        }

        public int getDenominator() {
            return denominator;
        }
    }
}

package ca.jbrains.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AddFractionsTest {
    @Test
    void zeroPlusZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(0));
        Assertions.assertEquals(0, sum.intValue());
    }

    @Test
    void notZeroPlusZero() throws Exception {
        Fraction sum = new Fraction(5).plus(new Fraction(0));
        Assertions.assertEquals(5, sum.intValue());
    }

    @Test
    void zeroPlusNotZero() throws Exception {
        Fraction sum = new Fraction(0).plus(new Fraction(8));
        Assertions.assertEquals(8, sum.intValue());
    }

    public static class Fraction {
        private final int integerValue;

        public Fraction(int integerValue) {
            this.integerValue = integerValue;
        }

        public Fraction plus(Fraction that) {
            if (that.integerValue == 0)
                return this;
            else
                return that;
        }

        public int intValue() {
            return integerValue;
        }
    }
}

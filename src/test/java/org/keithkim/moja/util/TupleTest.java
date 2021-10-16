package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.util.Tuple.Tuple1;
import org.keithkim.moja.util.Tuple.Tuple2;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {
    @Test
    void canMakeValues() {
        Tuple1<Integer> one = Tuple1.of(1);
        assertEquals(1, one.width());
        assertEquals(1, one.value1());

        Tuple2<Integer, String> two = Tuple2.of(1, "two");
        assertEquals(2, two.width());
        assertEquals(1, two.value1());
        assertEquals("two", two.value2());
    }

    @Test
    void hashCode_isPredictable() {
        Tuple1<Integer> one = Tuple1.of(1);
        Tuple1<Integer> oneToo = Tuple1.of(1);
        assertEquals(oneToo.hashCode(), one.hashCode());

        Tuple2<Integer, String> two = Tuple2.of(1, "two");
        assertNotEquals(one.hashCode(), two.hashCode());
        assertEquals(1524004177, one.hashCode());
        assertEquals(-394532, two.hashCode());
    }

    @Test
    void equals_isValueBased() {
        Tuple1<Integer> one = Tuple1.of(1);
        Tuple1<Integer> oneToo = Tuple1.of(1);
        assertEquals(one, oneToo);

        Tuple2<Integer, String> oneTwo = Tuple2.of(1, "two");
        assertNotEquals(one, oneTwo);

        Tuple2<String, Integer> twoOne = Tuple2.of("two", 1);
        assertNotEquals(oneTwo, twoOne);
    }

    @Test
    void compareTo_isLeftBiasedValues() {
        Tuple2<String, String> ab = Tuple2.of("a", "b");
        Tuple2<String, String> ba = Tuple2.of("b", "a");
        assertTrue(ab.compareTo(ba) < 0);
        assertTrue(ba.compareTo(ab) > 0);

        Tuple2<String, String> aa = Tuple2.of("a", "a");
        assertTrue(aa.compareTo(ab) < 0);
        assertTrue(ab.compareTo(aa) > 0);

        Tuple1<Integer> one = Tuple1.of(1);
        Tuple1<Integer> oneToo = Tuple1.of(1);
        assertEquals(0, one.compareTo(oneToo));

        Tuple1<String> other = Tuple1.of("one");
        assertTrue(one.compareTo((Tuple) other) < 0);
        assertTrue(other.compareTo((Tuple) one) < 0);

        Tuple2<Integer, String> oneTwo = Tuple2.of(1, "two");
        Tuple2<String, Integer> twoOne = Tuple2.of("two", 1);
        assertTrue(oneTwo.compareTo(twoOne) < 0);
        assertTrue(twoOne.compareTo(oneTwo) < 0);
    }
}

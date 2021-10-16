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
}

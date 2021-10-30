package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {
    @Test
    void canMakeValues() {
        Pair<Integer, String> aPair = Pair.of(1, "two");
        assertEquals(2, aPair.width());
        assertEquals(1, aPair.value1());
        assertEquals("two", aPair.value2());

        Triple<Integer, String, Double> aTriple = Triple.of(1, "two", Math.PI);
        assertEquals(3, aTriple.width());
        assertEquals(1, aTriple.value1());
        assertEquals("two", aTriple.value2());
        assertEquals(Math.PI, aTriple.value3());

        Tuple4<Integer, String, Float, Double> tuple4 = Tuple.of(1, "two", 3.0f, 4.0);
        assertEquals(4, tuple4.width());
        assertEquals(1, tuple4.value1());
        assertEquals("two", tuple4.value2());
        assertEquals(3.0f, tuple4.value3());
        assertEquals(4.0, tuple4.value4());
    }

    @Test
    void toString_isFriendly() {
        assertEquals("Pair(1, two)", Pair.of(1, "two").toString());
        assertEquals("Triple(1, two, 3.0)", Triple.of(1, "two", 3.0).toString());

        assertEquals("Tuple4(1, two, 3.0, 4)", Tuple.of(1, "two", 3.0, 4L).toString());
        assertEquals("Tuple5(1, two, 3.0, 4, 5)", Tuple.of(1, "two", 3.0, 4L, 5).toString());
        assertEquals("Tuple6(1, two, 3.0, 4, 5, 6)", Tuple.of(1, "two", 3.0, 4L, 5, 6).toString());
        assertEquals("Tuple7(1, two, 3.0, 4, 5, 6, 7)", Tuple.of(1, "two", 3.0, 4L, 5, 6, 7).toString());
        assertEquals("Tuple8(1, two, 3.0, 4, 5, 6, 7, 8)", Tuple.of(1, "two", 3.0, 4L, 5, 6, 7, 8).toString());
        assertEquals("Tuple9(1, two, 3.0, 4, 5, 6, 7, 8, 9)", Tuple.of(1, "two", 3.0, 4L, 5, 6, 7, 8, 9).toString());
    }

    @Test
    void toString_namedTuples() {
        Pair<Integer, String> namedPair = Pair.named("NumberString", 1, "two");
        assertEquals("NumberString(1, two)", namedPair.toString());

        Triple<Integer, String, Double> namedTriple = Triple.named("IntStrDbl", 1, "two", 3.0);
        assertEquals("IntStrDbl(1, two, 3.0)", namedTriple.toString());

        Tuple4<Long, Long, Long, Long> named4 = Tuple.named("Quad", 1L, 2L, 3L, 4L);
        assertEquals("Quad(1, 2, 3, 4)", named4.toString());

        Tuple5<Long, Long, Long, Long, Long> named5 = Tuple.named("Penta", 1L, 2L, 3L, 4L, 5L);
        assertEquals("Penta(1, 2, 3, 4, 5)", named5.toString());

        Tuple6<Long, Long, Long, Long, Long, Long> named6 = Tuple.named("Hex", 1L, 2L, 3L, 4L, 5L, 6L);
        assertEquals("Hex(1, 2, 3, 4, 5, 6)", named6.toString());

        Tuple7<Long, Long, Long, Long, Long, Long, Long> named7 = Tuple.named("Septuple", 1L, 2L, 3L, 4L, 5L, 6L, 7L);
        assertEquals("Septuple(1, 2, 3, 4, 5, 6, 7)", named7.toString());

        Tuple8<Long, Long, Long, Long, Long, Long, Long, Long> named8 = Tuple.named("Octuple", 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        assertEquals("Octuple(1, 2, 3, 4, 5, 6, 7, 8)", named8.toString());

        Tuple9<Long, Long, Long, Long, Long, Long, Long, Long, Long> named9 = Tuple.named("Niner", 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        assertEquals("Niner(1, 2, 3, 4, 5, 6, 7, 8, 9)", named9.toString());
    }

    @Test
    void hashCode_isPredictable() {
        Pair<Integer, String> aPair = Pair.of(1, "two");
        Pair<Integer, String> equalPair = Pair.of(1, "two");
        assertEquals(aPair.hashCode(), equalPair.hashCode());
        assertEquals(-1927505772, aPair.hashCode());

        Pair<String, Integer> differentPair = Pair.of("two", 1);
        assertNotEquals(aPair.hashCode(), differentPair.hashCode());
        assertEquals(-1924047522, differentPair.hashCode());
    }

    @Test
    void hashCode_namedIsDifferent() {
        assertNotEquals(Pair.of(1, "two").hashCode(), Pair.named("NumberString", 1, "two").hashCode());
        assertNotEquals(Pair.named("IntStr", 1, "two").hashCode(), Pair.named("NumberString", 1, "two").hashCode());

        assertNotEquals(Triple.of(1, 2, 3).hashCode(), Triple.named("a", 1, 2, 3).hashCode());
        assertNotEquals(Triple.named("a", 1, 2, 3).hashCode(), Triple.named("b", 1, 2, 3).hashCode());

        assertNotEquals(Tuple4.of(1, 2, 3, 4).hashCode(),
                        Tuple4.named("a", 1, 2, 3, 4).hashCode());
        assertNotEquals(Tuple4.named("a", 1, 2, 3, 4).hashCode(),
                        Tuple4.named("b", 1, 2, 3, 4).hashCode());

        assertNotEquals(Tuple5.of(1, 2, 3, 4, 5).hashCode(),
                        Tuple5.named("a", 1, 2, 3, 4, 5).hashCode());
        assertNotEquals(Tuple5.named("a", 1, 2, 3, 4, 5).hashCode(),
                        Tuple5.named("b", 1, 2, 3, 4, 5).hashCode());

        assertNotEquals(Tuple6.of(1, 2, 3, 4, 5, 6).hashCode(),
                        Tuple6.named("a", 1, 2, 3, 4, 5, 6).hashCode());
        assertNotEquals(Tuple6.named("a", 1, 2, 3, 4, 5, 6).hashCode(),
                        Tuple6.named("b", 1, 2, 3, 4, 5, 6).hashCode());

        assertNotEquals(Tuple7.of(1, 2, 3, 4, 5, 6, 7).hashCode(),
                        Tuple7.named("a", 1, 2, 3, 4, 5, 6, 7).hashCode());
        assertNotEquals(Tuple7.named("a", 1, 2, 3, 4, 5, 6, 7).hashCode(),
                        Tuple7.named("b", 1, 2, 3, 4, 5, 6, 7).hashCode());

        assertNotEquals(Tuple8.of(1, 2, 3, 4, 5, 6, 7, 8).hashCode(),
                        Tuple8.named("a", 1, 2, 3, 4, 5, 6, 7, 8).hashCode());
        assertNotEquals(Tuple8.named("a", 1, 2, 3, 4, 5, 6, 7, 8).hashCode(),
                        Tuple8.named("b", 1, 2, 3, 4, 5, 6, 7, 8).hashCode());

        assertNotEquals(Tuple9.of(1, 2, 3, 4, 5, 6, 7, 8, 9).hashCode(),
                        Tuple9.named("a", 1, 2, 3, 4, 5, 6, 7, 8, 9).hashCode());
        assertNotEquals(Tuple9.named("a", 1, 2, 3, 4, 5, 6, 7, 8, 9).hashCode(),
                        Tuple9.named("b", 1, 2, 3, 4, 5, 6, 7, 8, 9).hashCode());
    }

    @Test
    void equals_isValueBased() {
        Pair<Integer, String> one2 = Pair.of(1, "two");
        Pair<Integer, String> oneTwo = Tuple.of(1, "two");
        assertEquals(one2, oneTwo);

        Pair<String, Integer> twoOne = Tuple.of("two", 1);
        assertNotEquals(oneTwo, twoOne);
    }

    @Test
    void compareTo_isLeftBiasedValues() {
        Pair<String, String> ab = Pair.of("a", "b");
        Pair<String, String> ba = Pair.of("b", "a");
        assertTrue(ab.compareTo(ba) < 0);
        assertTrue(ba.compareTo(ab) > 0);

        Pair<String, String> aa = Pair.of("a", "a");
        assertTrue(aa.compareTo(ab) < 0);
        assertTrue(ab.compareTo(aa) > 0);

        Pair<String, String> ab2 = Pair.of("a", "b");
        assertEquals(0, ab.compareTo(ab2));

        Pair<Integer, String> oneTwo = Pair.of(1, "two");
        Pair<String, Integer> twoOne = Pair.of("two", 1);
        assertTrue(oneTwo.compareTo(twoOne) < 0);
        assertTrue(twoOne.compareTo(oneTwo) < 0);
    }
}

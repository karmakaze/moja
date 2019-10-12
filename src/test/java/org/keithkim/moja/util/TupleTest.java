package org.keithkim.moja.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TupleTest {
    @Test
    void constructor() {
        Tuple<Integer> ints0 = new Tuple<>();
        Tuple<Integer> ints1 = new Tuple<>(1);
        Tuple<Number> ints2 = new Tuple<>(1, 2.0);
    }

    @Test
    void size() {
        Tuple<Integer> ints0 = new Tuple<>();
        Tuple<Integer> ints1 = new Tuple<>(1);
        Tuple<Number> ints2 = new Tuple<>(1, 2.0);

        assertEquals(0, ints0.size());
        assertEquals(1, ints1.size());
        assertEquals(2, ints2.size());
    }

    @Test
    void component() {
        Tuple<Integer> ints0 = new Tuple<>();
        Tuple<Integer> ints1 = new Tuple<>(1);
        Tuple<Number> ints2 = new Tuple<>(1, 2.0);

        assertEquals(1, ints1.component(0));
        assertEquals(1, ints2.component(0));
        assertEquals(2.0, ints2.component(1));

        assertThrows(IndexOutOfBoundsException.class, () -> ints0.component(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> ints1.component(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> ints2.component(-1));

        assertThrows(IndexOutOfBoundsException.class, () -> ints0.component(0));
        assertThrows(IndexOutOfBoundsException.class, () -> ints1.component(1));
        assertThrows(IndexOutOfBoundsException.class, () -> ints2.component(2));
    }

    @Test
    void string() {
        Tuple<Integer> ints0 = new Tuple<>();
        Tuple<Integer> ints1 = new Tuple<>(1);
        Tuple<Number> ints2 = new Tuple<>(1, 2.0);

        assertEquals("Tuple()", ints0.toString());
        assertEquals("Tuple(1)", ints1.toString());
        assertEquals("Tuple(1, 2.0)", ints2.toString());
    }

    @Test
    void tuple0() {
        Tuple.Tuple0 tuple = new Tuple.Tuple0();

        assertEquals(0, tuple.size());

        assertThrows(IndexOutOfBoundsException.class, () -> tuple.component(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.component(0));

        assertEquals("Tuple()", tuple.toString());
    }

    @Test
    void tuple1() {
        Tuple.Tuple1<Integer> tuple = new Tuple.Tuple1<>(1);

        assertEquals(1, tuple.size());
        assertEquals(1, (Integer) tuple.component(0));
        assertEquals(1, (Integer) tuple.component1());

        assertThrows(IndexOutOfBoundsException.class, () -> tuple.component(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.component(1));

        assertEquals("Tuple(1)", tuple.toString());
    }

    @Test
    void tuple2() {
        Tuple.Tuple2<Integer, Double> tuple = new Tuple.Tuple2<>(1, 2.0);

        assertEquals(2, tuple.size());
        assertEquals(1, (Integer) tuple.component(0));
        assertEquals(2.0, (Double) tuple.component(1));
        assertEquals(1, (Integer) tuple.component1());
        assertEquals(2.0, (Double) tuple.component2());

        assertThrows(IndexOutOfBoundsException.class, () -> tuple.component(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.component(2));

        assertEquals("Tuple(1, 2.0)", tuple.toString());
    }
}

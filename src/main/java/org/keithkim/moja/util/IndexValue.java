package org.keithkim.moja.util;

public interface IndexValue<T> {
    int index();
    T value();

    interface Value2<A extends T, B extends T, T> extends IndexValue<T> {
        default A value1() {
            return index() == 0 ? (A) value() : null;
        }

        default B value2() {
            return index() == 1 ? (B) value() : null;
        }
    }

    interface Value3<A extends T, B extends T, C extends T, T> extends Value2<A, B, T> {
        default C value3() {
            return index() == 2 ? (C) value() : null;
        }
    }

    interface Value4<A extends T, B extends T, C extends T, D extends T, T> extends Value3<A, B, C, T> {
        default D value4() {
            return index() == 3 ? (D) value() : null;
        }
    }

    interface Value5<A extends T, B extends T, C extends T, D extends T, E extends T, T> extends Value4<A, B, C, D, T> {
        default E value5() {
            return index() == 4 ? (E) value() : null;
        }
    }

    interface Value6<A extends T, B extends T, C extends T, D extends T, E extends T, F extends T, T>
            extends Value5<A, B, C, D, E, T> {
        default F value6() {
            return index() == 5 ? (F) value() : null;
        }
    }

    interface Value7<A extends T, B extends T, C extends T, D extends T,
                     E extends T, F extends T, G extends T, T> extends Value6<A, B, C, D, E, F, T> {
        default G value7() {
            return index() == 6 ? (G) value() : null;
        }
    }

    interface Value8<A extends T, B extends T, C extends T, D extends T, E extends T,
                     F extends T, G extends T, H extends T, T> extends Value7<A, B, C, D, E, F, G, T> {
        default H value8() {
            return index() == 7 ? (H) value() : null;
        }
    }

    interface Value9<A extends T, B extends T, C extends T, D extends T, E extends T,
                     F extends T, G extends T, H extends T, I extends T, T> extends Value8<A, B, C, D, E, F, G, H, T> {
        default I value9() {
            return index() == 8 ? (I) value() : null;
        }
    }
}

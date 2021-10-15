package org.keithkim.moja.util;

import java.util.function.Function;

public interface IndexValued<T> {
    int length();
    T value(int index);

    interface Indexed1<T, SELF, A extends T> extends IndexValued<T>, Valued.Value1<A> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
    }

    interface Indexed2<T, SELF, A extends T, B extends T> extends IndexValued<T>, Valued.Value2<A, B> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
    }

    interface Indexed3<T, SELF, A extends T, B extends T, C extends T> extends IndexValued<T>, Valued.Value3<A, B, C> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
    }

    interface Indexed4<T, SELF, A extends T, B extends T, C extends T, D extends T>
            extends IndexValued<T>, Valued.Value4<A, B, C, D> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
        default D value4() {return (D) value(3);}
    }

    interface Indexed5<T, SELF, A extends T, B extends T, C extends T, D extends T, E extends T>
            extends IndexValued<T>, Valued.Value5<A, B, C, D, E> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
        default D value4() {return (D) value(3);}
        default E value5() {return (E) value(4);}
    }

    interface Indexed6<T, SELF, A extends T, B extends T, C extends T, D extends T, E extends T, F extends T>
            extends IndexValued<T>, Valued.Value6<A, B, C, D, E, F> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
        default D value4() {return (D) value(3);}
        default E value5() {return (E) value(4);}
        default F value6() {return (F) value(5);}
    }

    interface Indexed7<T, SELF, A extends T, B extends T, C extends T,
                                D extends T, E extends T, F extends T, G extends T>
            extends IndexValued<T>, Valued.Value7<A, B, C, D, E, F, G> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
        default D value4() {return (D) value(3);}
        default E value5() {return (E) value(4);}
        default F value6() {return (F) value(5);}
        default G value7() {return (G) value(6);}
    }

    interface Indexed8<T, SELF, A extends T, B extends T, C extends T, D extends T,
                                E extends T, F extends T, G extends T, H extends T>
            extends IndexValued<T>, Valued.Value8<A, B, C, D, E, F, G, H> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
        default D value4() {return (D) value(3);}
        default E value5() {return (E) value(4);}
        default F value6() {return (F) value(5);}
        default G value7() {return (G) value(6);}
        default H value8() {return (H) value(7);}
    }

    interface Indexed9<T, SELF, A extends T, B extends T, C extends T, D extends T,
                                E extends T, F extends T, G extends T, H extends T, I extends T>
            extends IndexValued<T>, Valued.Value9<A, B, C, D, E, F, G, H, I> {
        default <U> U then(Function<SELF, U> f) {return f.apply((SELF) this);}
        default A value1() {return (A) value(0);}
        default B value2() {return (B) value(1);}
        default C value3() {return (C) value(2);}
        default D value4() {return (D) value(3);}
        default E value5() {return (E) value(4);}
        default F value6() {return (F) value(5);}
        default G value7() {return (G) value(6);}
        default H value8() {return (H) value(7);}
        default I value9() {return (I) value(8);}
    }
}

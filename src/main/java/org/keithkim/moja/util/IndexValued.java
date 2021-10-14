package org.keithkim.moja.util;

import java.util.function.Function;

public interface IndexValued<T> {
    int length();
    T value(int index);

    interface Indexed2<T, K, T1 extends T, T2 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
    }

    interface Indexed3<T, K, T1 extends T, T2 extends T, T3 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
    }

    interface Indexed4<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
        default T4 value4() {
            return (T4) value(3);
        }
    }

    interface Indexed5<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T,
                                                                       T5 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
        default T4 value4() {
            return (T4) value(3);
        }
        default T5 value5() {
            return (T5) value(4);
        }
    }

    interface Indexed6<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T,
                                                         T5 extends T, T6 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
        default T4 value4() {
            return (T4) value(3);
        }
        default T5 value5() {
            return (T5) value(4);
        }
        default T6 value6() {
            return (T6) value(5);
        }
    }

    interface Indexed7<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T,
                                           T5 extends T, T6 extends T, T7 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
        default T4 value4() {
            return (T4) value(3);
        }
        default T5 value5() {
            return (T5) value(4);
        }
        default T6 value6() {
            return (T6) value(5);
        }
        default T7 value7() {
            return (T7) value(6);
        }
    }

    interface Indexed8<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T,
                             T5 extends T, T6 extends T, T7 extends T, T8 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
        default T4 value4() {
            return (T4) value(3);
        }
        default T5 value5() {
            return (T5) value(4);
        }
        default T6 value6() {
            return (T6) value(5);
        }
        default T7 value7() {
            return (T7) value(6);
        }
        default T8 value8() {
            return (T8) value(7);
        }
    }

    interface Indexed9<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T, T5 extends T,
                             T6 extends T, T7 extends T, T8 extends T, T9 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default T1 value1() {
            return (T1) value(0);
        }
        default T2 value2() {
            return (T2) value(1);
        }
        default T3 value3() {
            return (T3) value(2);
        }
        default T4 value4() {
            return (T4) value(3);
        }
        default T5 value5() {
            return (T5) value(4);
        }
        default T6 value6() {
            return (T6) value(5);
        }
        default T7 value7() {
            return (T7) value(6);
        }
        default T8 value8() {
            return (T8) value(7);
        }
        default T9 value9() {
            return (T9) value(8);
        }
    }
}

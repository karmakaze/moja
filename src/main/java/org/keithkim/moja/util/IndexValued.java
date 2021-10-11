package org.keithkim.moja.util;

import java.util.function.Function;

public interface IndexValued<T> {
    int length();
    T value(int index);

    public interface Of2<T, K, T1 extends T, T2 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
    }

    public interface Of3<T, K, T1 extends T, T2 extends T, T3 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
    }

    public interface Of4<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
        default public T4 value4() {
            return (T4) value(3);
        }
    }

    public interface Of5<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T,
                                T5 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
        default public T4 value4() {
            return (T4) value(3);
        }
        default public T5 value5() {
            return (T5) value(4);
        }
    }

    public interface Of6<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T, T5 extends T,
                                T6 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
        default public T4 value4() {
            return (T4) value(3);
        }
        default public T5 value5() {
            return (T5) value(4);
        }
        default public T6 value6() {
            return (T6) value(5);
        }
    }

    public interface Of7<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T, T5 extends T,
                                T6 extends T, T7 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
        default public T4 value4() {
            return (T4) value(3);
        }
        default public T5 value5() {
            return (T5) value(4);
        }
        default public T6 value6() {
            return (T6) value(5);
        }
        default public T7 value7() {
            return (T7) value(6);
        }
    }

    public interface Of8<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T, T5 extends T,
                                T6 extends T, T7 extends T, T8 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
        default public T4 value4() {
            return (T4) value(3);
        }
        default public T5 value5() {
            return (T5) value(4);
        }
        default public T6 value6() {
            return (T6) value(5);
        }
        default public T7 value7() {
            return (T7) value(6);
        }
        default public T8 value8() {
            return (T8) value(7);
        }
    }

    public interface Of9<T, K, T1 extends T, T2 extends T, T3 extends T, T4 extends T, T5 extends T,
                                T6 extends T, T7 extends T, T8 extends T, T9 extends T> extends IndexValued<T> {
        default <U> U then(Function<K, U> f) {
            return f.apply((K) this);
        }
        default public T1 value1() {
            return (T1) value(0);
        }
        default public T2 value2() {
            return (T2) value(1);
        }
        default public T3 value3() {
            return (T3) value(2);
        }
        default public T4 value4() {
            return (T4) value(3);
        }
        default public T5 value5() {
            return (T5) value(4);
        }
        default public T6 value6() {
            return (T6) value(5);
        }
        default public T7 value7() {
            return (T7) value(6);
        }
        default public T8 value8() {
            return (T8) value(7);
        }
        default public T9 value9() {
            return (T9) value(8);
        }
    }
}

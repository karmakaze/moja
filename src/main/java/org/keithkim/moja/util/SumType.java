package org.keithkim.moja.util;

import java.util.function.Function;

public class SumType<T> implements IndexValued<T> {
    final int length;
    final int index;
    final T value;

    SumType(int length, int index, T value) {
        this.length = length;
        this.index = index;
        this.value = value;
    }
    @Override
    public T value(int index) {
        return index == this.index ? value : null;
    }
    @Override
    public int length() {
        return length;
    }
    public int index() {
        return index;
    }
    public <T, U, T1 extends T, T2 extends T, T3 extends T>
    U when(int i, Function<T, U> f, U elseValue) {
        return i == index + 1 ? f.apply((T) value) : elseValue;
    }
    public <T, U, T1 extends T, T2 extends T, T3 extends T>
    U when(Class<T> tClass, Function<T, U> f, U elseValue) {
        return tClass.isInstance(value) ? f.apply((T) value) : elseValue;
    }

    public static class OneOf2<T1, T2> extends SumType<Object>
            implements IndexValued.Of2<Object, OneOf2<T1, T2>, T1, T2> {
        public static <V> OneOf2 value1(V v1) {
            return new OneOf2(0, v1);
        }
        public static <V> OneOf2 value2(V v2) {
            return new OneOf2(1, v2);
        }
        OneOf2(int index, Object value) {
            super(2, index, value);
        }
        <U> U when1(Function<T1, U> f, U elseValue) {
            return index == 0 ? f.apply(value1()): elseValue;
        }
        <U> U when2(Function<T2, U> f, U elseValue) {
            return index == 1 ? f.apply(value2()): elseValue;
        }
    }

    public static class OneOf3<T1, T2, T3> extends SumType<Object>
            implements IndexValued.Of3<Object, OneOf3<T1, T2, T3>, T1, T2, T3> {
        public static <V> OneOf3 value1(V v1) {
            return new OneOf3(0, v1);
        }
        public static <V> OneOf3 value2(V v2) {
            return new OneOf3(1, v2);
        }
        public static <V> OneOf3 value3(V v3) {
            return new OneOf3(2, v3);
        }
        OneOf3(int index, Object value) {
            super(3, index, value);
        }
    }

    public static class OneOf4<T1, T2, T3, T4> extends SumType<Object>
            implements IndexValued.Of4<Object, OneOf4<T1, T2, T3, T4>, T1, T2, T3, T4> {
        public static <V> OneOf4 value1(V v1) {
            return new OneOf4(0, v1);
        }
        public static <V> OneOf4 value2(V v2) {
            return new OneOf4(1, v2);
        }
        public static <V> OneOf4 value3(V v3) {
            return new OneOf4(2, v3);
        }
        public static <V> OneOf4 value4(V v4) {
            return new OneOf4(3, v4);
        }
        OneOf4(int index, Object value) {
            super(4, index, value);
        }
    }

    public static class OneOf5<T1, T2, T3, T4, T5> extends SumType<Object>
            implements IndexValued.Of5<Object, OneOf5<T1, T2, T3, T4, T5>, T1, T2, T3, T4, T5> {
        public static <V> OneOf5 value1(V v1) {
            return new OneOf5(0, v1);
        }
        public static <V> OneOf5 value2(V v2) {
            return new OneOf5(1, v2);
        }
        public static <V> OneOf5 value3(V v3) {
            return new OneOf5(2, v3);
        }
        public static <V> OneOf5 value4(V v4) {
            return new OneOf5(3, v4);
        }
        public static <V> OneOf5 value5(V v5) {
            return new OneOf5(4, v5);
        }
        OneOf5(int index, Object value) {
            super(5, index, value);
        }
    }

    public static class OneOf6<T1, T2, T3, T4, T5, T6> extends SumType<Object>
            implements IndexValued.Of6<Object, OneOf6<T1, T2, T3, T4, T5, T6>, T1, T2, T3, T4, T5, T6> {
        public static <V> OneOf6 value1(V v1) {
            return new OneOf6(0, v1);
        }
        public static <V> OneOf6 value2(V v2) {
            return new OneOf6(1, v2);
        }
        public static <V> OneOf6 value3(V v3) {
            return new OneOf6(2, v3);
        }
        public static <V> OneOf6 value4(V v4) {
            return new OneOf6(3, v4);
        }
        public static <V> OneOf6 value5(V v5) {
            return new OneOf6(4, v5);
        }
        public static <V> OneOf6 value6(V v6) {
            return new OneOf6(5, v6);
        }
        OneOf6(int index, Object value) {
            super(6, index, value);
        }
    }

    public static class OneOf7<T1, T2, T3, T4, T5, T6, T7> extends SumType<Object>
            implements IndexValued.Of7<Object, OneOf7<T1, T2, T3, T4, T5, T6, T7>, T1, T2, T3, T4, T5, T6, T7> {
        public static <V> OneOf7 value1(V v1) {
            return new OneOf7(0, v1);
        }
        public static <V> OneOf7 value2(V v2) {
            return new OneOf7(1, v2);
        }
        public static <V> OneOf7 value3(V v3) {
            return new OneOf7(2, v3);
        }
        public static <V> OneOf7 value4(V v4) {
            return new OneOf7(3, v4);
        }
        public static <V> OneOf7 value5(V v5) {
            return new OneOf7(4, v5);
        }
        public static <V> OneOf7 value6(V v6) {
            return new OneOf7(5, v6);
        }
        public static <V> OneOf7 value7(V v7) {
            return new OneOf7(6, v7);
        }
        OneOf7(int index, Object value) {
            super(7, index, value);
        }
    }

    public static class OneOf8<T1, T2, T3, T4, T5, T6, T7, T8> extends SumType<Object>
            implements IndexValued.Of8<Object, OneOf8<T1, T2, T3, T4, T5, T6, T7, T8>, T1, T2, T3, T4, T5, T6, T7, T8> {
        public static <V> OneOf8 value1(V v1) {
            return new OneOf8(0, v1);
        }
        public static <V> OneOf8 value2(V v2) {
            return new OneOf8(1, v2);
        }
        public static <V> OneOf8 value3(V v3) {
            return new OneOf8(2, v3);
        }
        public static <V> OneOf8 value4(V v4) {
            return new OneOf8(3, v4);
        }
        public static <V> OneOf8 value5(V v5) {
            return new OneOf8(4, v5);
        }
        public static <V> OneOf8 value6(V v6) {
            return new OneOf8(5, v6);
        }
        public static <V> OneOf8 value7(V v7) {
            return new OneOf8(6, v7);
        }
        public static <V> OneOf8 value8(V v8) {
            return new OneOf8(7, v8);
        }
        OneOf8(int index, Object value) {
            super(8, index, value);
        }
    }

    public static class OneOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends SumType<Object>
            implements IndexValued.Of9<Object,
                                       OneOf9<T1, T2, T3, T4, T5, T6, T7, T8, T9>,
                                       T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        public static <V> OneOf9 value1(V v1) {
            return new OneOf9(0, v1);
        }
        public static <V> OneOf9 value2(V v2) {
            return new OneOf9(1, v2);
        }
        public static <V> OneOf9 value3(V v3) {
            return new OneOf9(2, v3);
        }
        public static <V> OneOf9 value4(V v4) {
            return new OneOf9(3, v4);
        }
        public static <V> OneOf9 value5(V v5) {
            return new OneOf9(4, v5);
        }
        public static <V> OneOf9 value6(V v6) {
            return new OneOf9(5, v6);
        }
        public static <V> OneOf9 value7(V v7) {
            return new OneOf9(6, v7);
        }
        public static <V> OneOf9 value8(V v8) {
            return new OneOf9(7, v8);
        }
        public static <V> OneOf9 value9(V v9) {
            return new OneOf9(8, v9);
        }
        OneOf9(int index, Object value) {
            super(9, index, value);
        }
    }
}

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
    public <S extends T, U> U when(int i, Function<S, U> f, U elseValue) {
        return i == index + 1 ? f.apply((S) value) : elseValue;
    }
    public <S extends T, U> U when(Class<S> tClass, Function<S, U> f, U elseValue) {
        return tClass.isInstance(value) ? f.apply((S) value) : elseValue;
    }

    public static class SumType2<T1, T2> extends SumType<Object>
            implements IndexValued.Indexed2<Object, SumType2<T1, T2>, T1, T2> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2) {
            Function<Object, U>[] fs = new Function[] { f1, f2 };
            return fs[index].apply(value);
        }
        public static <V> SumType2 value1(V v1) {
            return new SumType2(0, v1);
        }
        public static <V> SumType2 value2(V v2) {
            return new SumType2(1, v2);
        }
        SumType2(int index, Object value) {
            super(2, index, value);
        }
    }

    public static class SumType3<T1, T2, T3> extends SumType<Object>
            implements IndexValued.Indexed3<Object, SumType3<T1, T2, T3>, T1, T2, T3> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3 };
            return fs[index].apply(value);
        }
        public static <V> SumType3 value1(V v1) {
            return new SumType3(0, v1);
        }
        public static <V> SumType3 value2(V v2) {
            return new SumType3(1, v2);
        }
        public static <V> SumType3 value3(V v3) {
            return new SumType3(2, v3);
        }
        SumType3(int index, Object value) {
            super(3, index, value);
        }
    }

    public static class SumType4<T1, T2, T3, T4> extends SumType<Object>
            implements IndexValued.Indexed4<Object, SumType4<T1, T2, T3, T4>, T1, T2, T3, T4> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4 };
            return fs[index].apply(value);
        }
        public static <V> SumType4 value1(V v1) {
            return new SumType4(0, v1);
        }
        public static <V> SumType4 value2(V v2) {
            return new SumType4(1, v2);
        }
        public static <V> SumType4 value3(V v3) {
            return new SumType4(2, v3);
        }
        public static <V> SumType4 value4(V v4) {
            return new SumType4(3, v4);
        }
        SumType4(int index, Object value) {
            super(4, index, value);
        }
    }

    public static class SumType5<T1, T2, T3, T4, T5> extends SumType<Object>
            implements IndexValued.Indexed5<Object, SumType5<T1, T2, T3, T4, T5>,
                                            T1, T2, T3, T4, T5> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2,
                          Function<T3, U> f3, Function<T4, U> f4, Function<T5, U> f5) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5 };
            return fs[index].apply(value);
        }
        public static <V> SumType5 value1(V v1) {
            return new SumType5(0, v1);
        }
        public static <V> SumType5 value2(V v2) {
            return new SumType5(1, v2);
        }
        public static <V> SumType5 value3(V v3) {
            return new SumType5(2, v3);
        }
        public static <V> SumType5 value4(V v4) {
            return new SumType5(3, v4);
        }
        public static <V> SumType5 value5(V v5) {
            return new SumType5(4, v5);
        }
        SumType5(int index, Object value) {
            super(5, index, value);
        }
    }

    public static class SumType6<T1, T2, T3, T4, T5, T6> extends SumType<Object>
            implements IndexValued.Indexed6<Object, SumType6<T1, T2, T3, T4, T5, T6>,
                                            T1, T2, T3, T4, T5, T6> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6 };
            return fs[index].apply(value);
        }
        public static <V> SumType6 value1(V v1) {
            return new SumType6(0, v1);
        }
        public static <V> SumType6 value2(V v2) {
            return new SumType6(1, v2);
        }
        public static <V> SumType6 value3(V v3) {
            return new SumType6(2, v3);
        }
        public static <V> SumType6 value4(V v4) {
            return new SumType6(3, v4);
        }
        public static <V> SumType6 value5(V v5) {
            return new SumType6(4, v5);
        }
        public static <V> SumType6 value6(V v6) {
            return new SumType6(5, v6);
        }
        SumType6(int index, Object value) {
            super(6, index, value);
        }
    }

    public static class SumType7<T1, T2, T3, T4, T5, T6, T7> extends SumType<Object>
            implements IndexValued.Indexed7<Object, SumType7<T1, T2, T3, T4, T5, T6, T7>,
                                            T1, T2, T3, T4, T5, T6, T7> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6, Function<T7, U> f7) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7 };
            return fs[index].apply(value);
        }
        public static <V> SumType7 value1(V v1) {
            return new SumType7(0, v1);
        }
        public static <V> SumType7 value2(V v2) {
            return new SumType7(1, v2);
        }
        public static <V> SumType7 value3(V v3) {
            return new SumType7(2, v3);
        }
        public static <V> SumType7 value4(V v4) {
            return new SumType7(3, v4);
        }
        public static <V> SumType7 value5(V v5) {
            return new SumType7(4, v5);
        }
        public static <V> SumType7 value6(V v6) {
            return new SumType7(5, v6);
        }
        public static <V> SumType7 value7(V v7) {
            return new SumType7(6, v7);
        }
        SumType7(int index, Object value) {
            super(7, index, value);
        }
    }

    public static class SumType8<T1, T2, T3, T4, T5, T6, T7, T8> extends SumType<Object>
            implements IndexValued.Indexed8<Object, SumType8<T1, T2, T3, T4, T5, T6, T7, T8>,
                                            T1, T2, T3, T4, T5, T6, T7, T8> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4,
                          Function<T5, U> f5, Function<T6, U> f6, Function<T7, U> f7, Function<T8, U> f8) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8 };
            return fs[index].apply(value);
        }
        public static <V> SumType8 value1(V v1) {
            return new SumType8(0, v1);
        }
        public static <V> SumType8 value2(V v2) {
            return new SumType8(1, v2);
        }
        public static <V> SumType8 value3(V v3) {
            return new SumType8(2, v3);
        }
        public static <V> SumType8 value4(V v4) {
            return new SumType8(3, v4);
        }
        public static <V> SumType8 value5(V v5) {
            return new SumType8(4, v5);
        }
        public static <V> SumType8 value6(V v6) {
            return new SumType8(5, v6);
        }
        public static <V> SumType8 value7(V v7) {
            return new SumType8(6, v7);
        }
        public static <V> SumType8 value8(V v8) {
            return new SumType8(7, v8);
        }
        SumType8(int index, Object value) {
            super(8, index, value);
        }
    }

    public static class SumType9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends SumType<Object>
            implements IndexValued.Indexed9<Object, SumType9<T1, T2, T3, T4, T5, T6, T7, T8, T9>,
                                            T1, T2, T3, T4, T5, T6, T7, T8, T9> {

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6,
                          Function<T7, U> f7, Function<T8, U> f8, Function<T9, U> f9) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8, f9 };
            return fs[index].apply(value);
        }
        public static <V> SumType9 value1(V v1) {
            return new SumType9(0, v1);
        }
        public static <V> SumType9 value2(V v2) {
            return new SumType9(1, v2);
        }
        public static <V> SumType9 value3(V v3) {
            return new SumType9(2, v3);
        }
        public static <V> SumType9 value4(V v4) {
            return new SumType9(3, v4);
        }
        public static <V> SumType9 value5(V v5) {
            return new SumType9(4, v5);
        }
        public static <V> SumType9 value6(V v6) {
            return new SumType9(5, v6);
        }
        public static <V> SumType9 value7(V v7) {
            return new SumType9(6, v7);
        }
        public static <V> SumType9 value8(V v8) {
            return new SumType9(7, v8);
        }
        public static <V> SumType9 value9(V v9) {
            return new SumType9(8, v9);
        }
        SumType9(int index, Object value) {
            super(9, index, value);
        }
    }
}

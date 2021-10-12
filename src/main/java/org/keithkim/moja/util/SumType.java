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
    public <T, U> U when(Class<T> tClass, Function<T, U> f, U elseValue) {
        return tClass.isInstance(value) ? f.apply((T) value) : elseValue;
    }

    public static class Of2<T1, T2> extends SumType<Object>
            implements IndexValued.Indexed2<Object, Of2<T1, T2>, T1, T2> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2) {
            Function<Object, U>[] fs = new Function[] { f1, f2 };
            return fs[index].apply(value);
        }
        public static <V> Of2 value1(V v1) {
            return new Of2(0, v1);
        }
        public static <V> Of2 value2(V v2) {
            return new Of2(1, v2);
        }
        Of2(int index, Object value) {
            super(2, index, value);
        }
    }

    public static class Of3<T1, T2, T3> extends SumType<Object>
            implements IndexValued.Indexed3<Object, Of3<T1, T2, T3>, T1, T2, T3> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3 };
            return fs[index].apply(value);
        }
        public static <V> Of3 value1(V v1) {
            return new Of3(0, v1);
        }
        public static <V> Of3 value2(V v2) {
            return new Of3(1, v2);
        }
        public static <V> Of3 value3(V v3) {
            return new Of3(2, v3);
        }
        Of3(int index, Object value) {
            super(3, index, value);
        }
    }

    public static class Of4<T1, T2, T3, T4> extends SumType<Object>
            implements IndexValued.Indexed4<Object, Of4<T1, T2, T3, T4>, T1, T2, T3, T4> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4 };
            return fs[index].apply(value);
        }
        public static <V> Of4 value1(V v1) {
            return new Of4(0, v1);
        }
        public static <V> Of4 value2(V v2) {
            return new Of4(1, v2);
        }
        public static <V> Of4 value3(V v3) {
            return new Of4(2, v3);
        }
        public static <V> Of4 value4(V v4) {
            return new Of4(3, v4);
        }
        Of4(int index, Object value) {
            super(4, index, value);
        }
    }

    public static class Of5<T1, T2, T3, T4, T5> extends SumType<Object>
            implements IndexValued.Indexed5<Object, Of5<T1, T2, T3, T4, T5>, T1, T2, T3, T4, T5> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5 };
            return fs[index].apply(value);
        }
        public static <V> Of5 value1(V v1) {
            return new Of5(0, v1);
        }
        public static <V> Of5 value2(V v2) {
            return new Of5(1, v2);
        }
        public static <V> Of5 value3(V v3) {
            return new Of5(2, v3);
        }
        public static <V> Of5 value4(V v4) {
            return new Of5(3, v4);
        }
        public static <V> Of5 value5(V v5) {
            return new Of5(4, v5);
        }
        Of5(int index, Object value) {
            super(5, index, value);
        }
    }

    public static class Of6<T1, T2, T3, T4, T5, T6> extends SumType<Object>
            implements IndexValued.Indexed6<Object, Of6<T1, T2, T3, T4, T5, T6>, T1, T2, T3, T4, T5, T6> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6 };
            return fs[index].apply(value);
        }
        public static <V> Of6 value1(V v1) {
            return new Of6(0, v1);
        }
        public static <V> Of6 value2(V v2) {
            return new Of6(1, v2);
        }
        public static <V> Of6 value3(V v3) {
            return new Of6(2, v3);
        }
        public static <V> Of6 value4(V v4) {
            return new Of6(3, v4);
        }
        public static <V> Of6 value5(V v5) {
            return new Of6(4, v5);
        }
        public static <V> Of6 value6(V v6) {
            return new Of6(5, v6);
        }
        Of6(int index, Object value) {
            super(6, index, value);
        }
    }

    public static class Of7<T1, T2, T3, T4, T5, T6, T7> extends SumType<Object>
            implements IndexValued.Indexed7<Object, Of7<T1, T2, T3, T4, T5, T6, T7>, T1, T2, T3, T4, T5, T6, T7> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4,
                          Function<T5, U> f5, Function<T6, U> f6, Function<T7, U> f7) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7 };
            return fs[index].apply(value);
        }
        public static <V> Of7 value1(V v1) {
            return new Of7(0, v1);
        }
        public static <V> Of7 value2(V v2) {
            return new Of7(1, v2);
        }
        public static <V> Of7 value3(V v3) {
            return new Of7(2, v3);
        }
        public static <V> Of7 value4(V v4) {
            return new Of7(3, v4);
        }
        public static <V> Of7 value5(V v5) {
            return new Of7(4, v5);
        }
        public static <V> Of7 value6(V v6) {
            return new Of7(5, v6);
        }
        public static <V> Of7 value7(V v7) {
            return new Of7(6, v7);
        }
        Of7(int index, Object value) {
            super(7, index, value);
        }
    }

    public static class Of8<T1, T2, T3, T4, T5, T6, T7, T8> extends SumType<Object>
            implements IndexValued.Indexed8<Object, Of8<T1, T2, T3, T4, T5, T6, T7, T8>, T1, T2, T3, T4, T5, T6, T7, T8> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4,
                          Function<T5, U> f5, Function<T6, U> f6, Function<T7, U> f7, Function<T8, U> f8) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8 };
            return fs[index].apply(value);
        }
        public static <V> Of8 value1(V v1) {
            return new Of8(0, v1);
        }
        public static <V> Of8 value2(V v2) {
            return new Of8(1, v2);
        }
        public static <V> Of8 value3(V v3) {
            return new Of8(2, v3);
        }
        public static <V> Of8 value4(V v4) {
            return new Of8(3, v4);
        }
        public static <V> Of8 value5(V v5) {
            return new Of8(4, v5);
        }
        public static <V> Of8 value6(V v6) {
            return new Of8(5, v6);
        }
        public static <V> Of8 value7(V v7) {
            return new Of8(6, v7);
        }
        public static <V> Of8 value8(V v8) {
            return new Of8(7, v8);
        }
        Of8(int index, Object value) {
            super(8, index, value);
        }
    }

    public static class Of9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends SumType<Object>
            implements IndexValued.Indexed9<Object,
                                       Of9<T1, T2, T3, T4, T5, T6, T7, T8, T9>,
                                       T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6,
                          Function<T7, U> f7, Function<T8, U> f8, Function<T9, U> f9) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8, f9 };
            return fs[index].apply(value);
        }
        public static <V> Of9 value1(V v1) {
            return new Of9(0, v1);
        }
        public static <V> Of9 value2(V v2) {
            return new Of9(1, v2);
        }
        public static <V> Of9 value3(V v3) {
            return new Of9(2, v3);
        }
        public static <V> Of9 value4(V v4) {
            return new Of9(3, v4);
        }
        public static <V> Of9 value5(V v5) {
            return new Of9(4, v5);
        }
        public static <V> Of9 value6(V v6) {
            return new Of9(5, v6);
        }
        public static <V> Of9 value7(V v7) {
            return new Of9(6, v7);
        }
        public static <V> Of9 value8(V v8) {
            return new Of9(7, v8);
        }
        public static <V> Of9 value9(V v9) {
            return new Of9(8, v9);
        }
        Of9(int index, Object value) {
            super(9, index, value);
        }
    }
}

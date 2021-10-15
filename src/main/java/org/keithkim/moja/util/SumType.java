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

        public static <A, B> SumType2<A, B> value1(A v1) { return new SumType2(0, v1); }
        public static <A, B> SumType2<A, B> value2(B v2) { return new SumType2(1, v2); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2) {
            Function<Object, U>[] fs = new Function[] { f1, f2 };
            return fs[index].apply(value);
        }

        public <U1, U2> SumType2<U1, U2> then2(Function<T1, U1> f1, Function<T2, U2> f2) {
            switch (index) {
                case 0: return SumType2.value1(f1.apply(this.value1()));
                case 1: return SumType2.value2(f2.apply(this.value2()));
                default: return null;
            }
        }

        public static <A, B, U> Function<SumType2<A, B>, U> f(Function<SumType2<A, B>, U> f) {
            return f;
        }

        SumType2(int index, Object value) {
            super(2, index, value);
        }
    }

    public static class SumType3<T1, T2, T3> extends SumType<Object>
            implements IndexValued.Indexed3<Object, SumType3<T1, T2, T3>, T1, T2, T3> {

        public static <V> SumType3 value1(V v1) { return new SumType3(0, v1); }
        public static <V> SumType3 value2(V v2) { return new SumType3(1, v2); }
        public static <V> SumType3 value3(V v3) { return new SumType3(2, v3); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3> SumType3<U1, U2, U3> then3(Function<T1, U1> f1, Function<T2, U2> f2, Function<T3, U3> f3) {
            switch (index) {
                case 0: return SumType3.value1(f1.apply(this.value1()));
                case 1: return SumType3.value2(f2.apply(this.value2()));
                case 2: return SumType3.value3(f3.apply(this.value3()));
                default: return null;
            }
        }

        public static <A, B, C, U> Function<SumType3<A, B, C>, U> f(Function<SumType3<A, B, C>, U> f) {
            return f;
        }

        SumType3(int index, Object value) {
            super(3, index, value);
        }
    }

    public static class SumType4<T1, T2, T3, T4> extends SumType<Object>
            implements IndexValued.Indexed4<Object, SumType4<T1, T2, T3, T4>, T1, T2, T3, T4> {

        public static <V> SumType4 value1(V v1) { return new SumType4(0, v1); }
        public static <V> SumType4 value2(V v2) { return new SumType4(1, v2); }
        public static <V> SumType4 value3(V v3) { return new SumType4(2, v3); }
        public static <V> SumType4 value4(V v4) { return new SumType4(3, v4); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3, U4>
        SumType4<U1, U2, U3, U4> then4(Function<T1, U1> f1, Function<T2, U2> f2,
                                       Function<T3, U3> f3, Function<T4, U4> f4) {
            switch (index) {
                case 0: return SumType4.value1(f1.apply(this.value1()));
                case 1: return SumType4.value2(f2.apply(this.value2()));
                case 2: return SumType4.value3(f3.apply(this.value3()));
                case 3: return SumType4.value4(f4.apply(this.value4()));
                default: return null;
            }
        }

        public static <A, B, C, D, U> Function<SumType4<A, B, C, D>, U> f(Function<SumType4<A, B, C, D>, U> f) {
            return f;
        }

        SumType4(int index, Object value) {
            super(4, index, value);
        }
    }

    public static class SumType5<T1, T2, T3, T4, T5> extends SumType<Object>
            implements IndexValued.Indexed5<Object, SumType5<T1, T2, T3, T4, T5>,
                                            T1, T2, T3, T4, T5> {

        public static <V> SumType5 value1(V v1) { return new SumType5(0, v1); }
        public static <V> SumType5 value2(V v2) { return new SumType5(1, v2); }
        public static <V> SumType5 value3(V v3) { return new SumType5(2, v3); }
        public static <V> SumType5 value4(V v4) { return new SumType5(3, v4); }
        public static <V> SumType5 value5(V v5) { return new SumType5(4, v5); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2,
                          Function<T3, U> f3, Function<T4, U> f4, Function<T5, U> f5) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3, U4, U5>
        SumType5<U1, U2, U3, U4, U5> then5(Function<T1, U1> f1, Function<T2, U2> f2,
                                           Function<T3, U3> f3, Function<T4, U4> f4, Function<T5, U5> f5) {
            switch (index) {
                case 0: return SumType5.value1(f1.apply(this.value1()));
                case 1: return SumType5.value2(f2.apply(this.value2()));
                case 2: return SumType5.value3(f3.apply(this.value3()));
                case 3: return SumType5.value4(f4.apply(this.value4()));
                case 4: return SumType5.value5(f5.apply(this.value5()));
                default: return null;
            }
        }

        public static <A, B, C, D, E, U>
        Function<SumType5<A, B, C, D, E>, U> f(Function<SumType5<A, B, C, D, E>, U> f) {
            return f;
        }

        SumType5(int index, Object value) {
            super(5, index, value);
        }
    }

    public static class SumType6<T1, T2, T3, T4, T5, T6> extends SumType<Object>
            implements IndexValued.Indexed6<Object, SumType6<T1, T2, T3, T4, T5, T6>,
                                            T1, T2, T3, T4, T5, T6> {

        public static <V> SumType6 value1(V v1) { return new SumType6(0, v1); }
        public static <V> SumType6 value2(V v2) { return new SumType6(1, v2); }
        public static <V> SumType6 value3(V v3) { return new SumType6(2, v3); }
        public static <V> SumType6 value4(V v4) { return new SumType6(3, v4); }
        public static <V> SumType6 value5(V v5) { return new SumType6(4, v5); }
        public static <V> SumType6 value6(V v6) { return new SumType6(5, v6); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3, U4, U5, U6>
        SumType6<U1, U2, U3, U4, U5, U6> then6(Function<T1, U1> f1, Function<T2, U2> f2, Function<T3, U3> f3,
                                               Function<T4, U4> f4, Function<T5, U5> f5, Function<T6, U6> f6) {
            switch (index) {
                case 0: return SumType6.value1(f1.apply(this.value1()));
                case 1: return SumType6.value2(f2.apply(this.value2()));
                case 2: return SumType6.value3(f3.apply(this.value3()));
                case 3: return SumType6.value4(f4.apply(this.value4()));
                case 4: return SumType6.value5(f5.apply(this.value5()));
                case 5: return SumType6.value6(f6.apply(this.value6()));
                default: return null;
            }
        }

        public static <A, B, C, D, E, F, U>
        Function<SumType6<A, B, C, D, E, F>, U> f(Function<SumType6<A, B, C, D, E, F>, U> f) {
            return f;
        }

        SumType6(int index, Object value) {
            super(6, index, value);
        }
    }

    public static class SumType7<T1, T2, T3, T4, T5, T6, T7> extends SumType<Object>
            implements IndexValued.Indexed7<Object, SumType7<T1, T2, T3, T4, T5, T6, T7>,
                                            T1, T2, T3, T4, T5, T6, T7> {

        public static <V> SumType7 value1(V v1) { return new SumType7(0, v1); }
        public static <V> SumType7 value2(V v2) { return new SumType7(1, v2); }
        public static <V> SumType7 value3(V v3) { return new SumType7(2, v3); }
        public static <V> SumType7 value4(V v4) { return new SumType7(3, v4); }
        public static <V> SumType7 value5(V v5) { return new SumType7(4, v5); }
        public static <V> SumType7 value6(V v6) { return new SumType7(5, v6); }
        public static <V> SumType7 value7(V v7) { return new SumType7(6, v7); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6, Function<T7, U> f7) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3, U4, U5, U6, U7>
        SumType7<U1, U2, U3, U4, U5, U6, U7> then7(
                Function<T1, U1> f1, Function<T2, U2> f2, Function<T3, U3> f3,
                Function<T4, U4> f4, Function<T5, U5> f5, Function<T6, U6> f6, Function<T7, U7> f7) {
            switch (index) {
                case 0: return SumType7.value1(f1.apply(this.value1()));
                case 1: return SumType7.value2(f2.apply(this.value2()));
                case 2: return SumType7.value3(f3.apply(this.value3()));
                case 3: return SumType7.value4(f4.apply(this.value4()));
                case 4: return SumType7.value5(f5.apply(this.value5()));
                case 5: return SumType7.value6(f6.apply(this.value6()));
                case 6: return SumType7.value7(f7.apply(this.value7()));
                default: return null;
            }
        }

        public static <A, B, C, D, E, F, G, U>
        Function<SumType7<A, B, C, D, E, F, G>, U> f(Function<SumType7<A, B, C, D, E, F, G>, U> f) {
            return f;
        }

        SumType7(int index, Object value) {
            super(7, index, value);
        }
    }

    public static class SumType8<T1, T2, T3, T4, T5, T6, T7, T8> extends SumType<Object>
            implements IndexValued.Indexed8<Object, SumType8<T1, T2, T3, T4, T5, T6, T7, T8>,
                                            T1, T2, T3, T4, T5, T6, T7, T8> {

        public static <V> SumType8 value1(V v1) { return new SumType8(0, v1); }
        public static <V> SumType8 value2(V v2) { return new SumType8(1, v2); }
        public static <V> SumType8 value3(V v3) { return new SumType8(2, v3); }
        public static <V> SumType8 value4(V v4) { return new SumType8(3, v4); }
        public static <V> SumType8 value5(V v5) { return new SumType8(4, v5); }
        public static <V> SumType8 value6(V v6) { return new SumType8(5, v6); }
        public static <V> SumType8 value7(V v7) { return new SumType8(6, v7); }
        public static <V> SumType8 value8(V v8) { return new SumType8(7, v8); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3, Function<T4, U> f4,
                          Function<T5, U> f5, Function<T6, U> f6, Function<T7, U> f7, Function<T8, U> f8) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3, U4, U5, U6, U7, U8>
        SumType8<U1, U2, U3, U4, U5, U6, U7, U8> then8(
                Function<T1, U1> f1, Function<T2, U2> f2, Function<T3, U3> f3, Function<T4, U4> f4,
                Function<T5, U5> f5, Function<T6, U6> f6, Function<T7, U7> f7, Function<T8, U8> f8) {
            switch (index) {
                case 0: return SumType8.value1(f1.apply(this.value1()));
                case 1: return SumType8.value2(f2.apply(this.value2()));
                case 2: return SumType8.value3(f3.apply(this.value3()));
                case 3: return SumType8.value4(f4.apply(this.value4()));
                case 4: return SumType8.value5(f5.apply(this.value5()));
                case 5: return SumType8.value6(f6.apply(this.value6()));
                case 6: return SumType8.value7(f7.apply(this.value7()));
                case 7: return SumType8.value8(f8.apply(this.value8()));
                default: return null;
            }
        }

        public static <A, B, C, D, E, F, G, H, U>
        Function<SumType8<A, B, C, D, E, F, G, H>, U> f(Function<SumType8<A, B, C, D, E, F, G, H>, U> f) {
            return f;
        }

        SumType8(int index, Object value) {
            super(8, index, value);
        }
    }

    public static class SumType9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends SumType<Object>
            implements IndexValued.Indexed9<Object, SumType9<T1, T2, T3, T4, T5, T6, T7, T8, T9>,
                                            T1, T2, T3, T4, T5, T6, T7, T8, T9> {

        public static <V> SumType9 value1(V v1) { return new SumType9(0, v1); }
        public static <V> SumType9 value2(V v2) { return new SumType9(1, v2); }
        public static <V> SumType9 value3(V v3) { return new SumType9(2, v3); }
        public static <V> SumType9 value4(V v4) { return new SumType9(3, v4); }
        public static <V> SumType9 value5(V v5) { return new SumType9(4, v5); }
        public static <V> SumType9 value6(V v6) { return new SumType9(5, v6); }
        public static <V> SumType9 value7(V v7) { return new SumType9(6, v7); }
        public static <V> SumType9 value8(V v8) { return new SumType9(7, v8); }
        public static <V> SumType9 value9(V v9) { return new SumType9(8, v9); }

        public <U> U then(Function<T1, U> f1, Function<T2, U> f2, Function<T3, U> f3,
                          Function<T4, U> f4, Function<T5, U> f5, Function<T6, U> f6,
                          Function<T7, U> f7, Function<T8, U> f8, Function<T9, U> f9) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8, f9 };
            return fs[index].apply(value);
        }

        public <U1, U2, U3, U4, U5, U6, U7, U8, U9>
        SumType9<U1, U2, U3, U4, U5, U6, U7, U8, U9> then9(
                Function<T1, U1> f1, Function<T2, U2> f2, Function<T3, U3> f3,
                Function<T4, U4> f4, Function<T5, U5> f5, Function<T6, U6> f6,
                Function<T7, U7> f7, Function<T8, U8> f8, Function<T9, U9> f9) {
            switch (index) {
                case 0: return SumType9.value1(f1.apply(this.value1()));
                case 1: return SumType9.value2(f2.apply(this.value2()));
                case 2: return SumType9.value3(f3.apply(this.value3()));
                case 3: return SumType9.value4(f4.apply(this.value4()));
                case 4: return SumType9.value5(f5.apply(this.value5()));
                case 5: return SumType9.value6(f6.apply(this.value6()));
                case 6: return SumType9.value7(f7.apply(this.value7()));
                case 7: return SumType9.value8(f8.apply(this.value8()));
                case 8: return SumType9.value9(f9.apply(this.value9()));
                default: return null;
            }
        }

        public static <A, B, C, D, E, F, G, H, I, U>
        Function<SumType9<A, B, C, D, E, F, G, H, I>, U> f(Function<SumType9<A, B, C, D, E, F, G, H, I>, U> f) {
            return f;
        }

        SumType9(int index, Object value) {
            super(9, index, value);
        }
    }
}

package org.keithkim.moja.util;

import java.util.Arrays;

public class Tuple<T> implements IndexValued<T> {
    private final Object[] values;

    private static final Class<Object[]> ObjectArrayClass = (Class<Object[]>) (new Object[] {}).getClass();

    public static <T> Tuple<T> of(T... values) {
        return new Tuple<>(values);
    }

    protected Tuple(T... values) {
        this.values = Arrays.copyOf(values, values.length, ObjectArrayClass);
    }

    @Override
    public int length() {
        return values.length;
    }

    @Override
    public T value(int index) {
        return (T) values[index];
    }

    @Override
    public String toString() {
        String string = Arrays.toString(values);
        return "Tuple("+ string.substring(1, string.length() - 1) +")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tuple) {
            Tuple that = (Tuple) o;
            if (this.values.length == that.values.length) {
                return Arrays.equals(this.values, that.values);
            }
        }
        return false;
    }

    public static class Tuple0 extends Tuple<Void> {
        public static Tuple0 of() {
            return new Tuple0();
        }
    }

    public static class Tuple1<T1> extends Tuple<T1> {
        public static <T1> Tuple1<T1> of(T1 value) {
            return new Tuple1<>(value);
        }

        public Tuple1(T1 value) {
            super(value);
        }

        public T1 value1() {
            return value(0);
        }
    }

    public static class Tuple2<T1, T2> extends Tuple<Object> {
        public static <T1, T2> Tuple2<T1, T2> of(T1 v1, T2 v2) {
            return new Tuple2<>(v1, v2);
        }

        public Tuple2(T1 v1, T2 v2) {
            super(v1, v2);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }
    }

    public static class Tuple3<T1, T2, T3> extends Tuple<Object> {
        public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 v1, T2 v2, T3 v3) {
            return new Tuple3<>(v1, v2, v3);
        }

        public Tuple3(T1 v1, T2 v2, T3 v3) {
            super(v1, v2, v3);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }
    }

    public static class Tuple4<T1, T2, T3, T4> extends Tuple<Object> {
        public static <T1, T2, T3, T4>
        Tuple4<T1, T2, T3, T4>
        of(T1 v1, T2 v2, T3 v3, T4 v4) {
            return new Tuple4<>(v1, v2, v3, v4);
        }

        public Tuple4(T1 v1, T2 v2, T3 v3, T4 v4) {
            super(v1, v2, v3, v4);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }

        public T4 value4() {
            return (T4) value(3);
        }
    }

    public static class Tuple5<T1, T2, T3, T4, T5> extends Tuple<Object> {
        public static <T1, T2, T3, T4, T5>
        Tuple5<T1, T2, T3, T4, T5>
        of(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
            return new Tuple5<>(v1, v2, v3, v4, v5);
        }

        public Tuple5(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
            super(v1, v2, v3, v4, v5);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }

        public T4 value4() {
            return (T4) value(3);
        }

        public T5 value5() {
            return (T5) value(4);
        }
    }

    public static class Tuple6<T1, T2, T3, T4, T5, T6> extends Tuple<Object> {
        public static <T1, T2, T3, T4, T5, T6>
        Tuple6<T1, T2, T3, T4, T5, T6>
        of(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6) {
            return new Tuple6<>(v1, v2, v3, v4, v5, v6);
        }

        public Tuple6(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6) {
            super(v1, v2, v3, v4, v5, v6);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }

        public T4 value4() {
            return (T4) value(3);
        }

        public T5 value5() {
            return (T5) value(4);
        }

        public T6 value6() {
            return (T6) value(5);
        }
    }

    public static class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple<Object> {
        public static <T1, T2, T3, T4, T5, T6, T7>
        Tuple7<T1, T2, T3, T4, T5, T6, T7>
        of(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7) {
            return new Tuple7<>(v1, v2, v3, v4, v5, v6, v7);
        }

        public Tuple7(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7) {
            super(v1, v2, v3, v4, v5, v6, v7);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }

        public T4 value4() {
            return (T4) value(3);
        }

        public T5 value5() {
            return (T5) value(4);
        }

        public T6 value6() {
            return (T6) value(5);
        }

        public T7 value7() {
            return (T7) value(6);
        }
    }

    public static class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> extends Tuple<Object> {
        public static <T1, T2, T3, T4, T5, T6, T7, T8>
        Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>
        of(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8) {
            return new Tuple8<>(v1, v2, v3, v4, v5, v6, v7, v8);
        }

        public Tuple8(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8) {
            super(v1, v2, v3, v4, v5, v6, v7, v8);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }

        public T4 value4() {
            return (T4) value(3);
        }

        public T5 value5() {
            return (T5) value(4);
        }

        public T6 value6() {
            return (T6) value(5);
        }

        public T7 value7() {
            return (T7) value(6);
        }

        public T8 value8() {
            return (T8) value(7);
        }
    }

    public static class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Tuple<Object> {
        public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> 
        Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
        of(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8, T9 v9) {
            return new Tuple9<>(v1, v2, v3, v4, v5, v6, v7, v8, v9);
        }

        public Tuple9(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5, T6 v6, T7 v7, T8 v8, T9 v9) {
            super(v1, v2, v3, v4, v5, v6, v7, v8, v9);
        }

        public T1 value1() {
            return (T1) value(0);
        }

        public T2 value2() {
            return (T2) value(1);
        }

        public T3 value3() {
            return (T3) value(2);
        }

        public T4 value4() {
            return (T4) value(3);
        }

        public T5 value5() {
            return (T5) value(4);
        }

        public T6 value6() {
            return (T6) value(5);
        }

        public T7 value7() {
            return (T7) value(6);
        }

        public T8 value8() {
            return (T8) value(7);
        }

        public T9 value9() {
            return (T9) value(8);
        }
    }
}

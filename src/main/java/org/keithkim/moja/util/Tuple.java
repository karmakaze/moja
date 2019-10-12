package org.keithkim.moja.util;

import java.util.Arrays;

public class Tuple<T> {
    private final Object[] components;

    private static final Class<Object[]> ObjectArrayClass = (Class<Object[]>) (new Object[] {}).getClass();

    public static <T> Tuple<T> of(T... components) {
        return new Tuple<>(components);
    }

    protected Tuple(T... components) {
        this.components = Arrays.copyOf(components, components.length, ObjectArrayClass);
    }

    public int size() {
        return components.length;
    }

    public T component(int index) {
        return (T) components[index];
    }

    @Override
    public String toString() {
        String string = Arrays.toString(components);
        return "Tuple("+ string.substring(1, string.length() - 1) +")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tuple) {
            Tuple that = (Tuple) o;
            if (this.components.length == that.components.length) {
                return Arrays.equals(this.components, that.components);
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

        public T1 component1() {
            return component(0);
        }
    }

    public static class Tuple2<T1, T2> extends Tuple<Object> {
        public static <T1, T2> Tuple2<T1, T2> of(T1 v1, T2 v2) {
            return new Tuple2<>(v1, v2);
        }

        public Tuple2(T1 v1, T2 v2) {
            super(v1, v2);
        }

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }
    }

    public static class Tuple3<T1, T2, T3> extends Tuple<Object> {
        public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 v1, T2 v2, T3 v3) {
            return new Tuple3<>(v1, v2, v3);
        }

        public Tuple3(T1 v1, T2 v2, T3 v3) {
            super(v1, v2, v3);
        }

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
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

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
        }

        public T4 component4() {
            return (T4) component(3);
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

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
        }

        public T4 component4() {
            return (T4) component(3);
        }

        public T5 component5() {
            return (T5) component(4);
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

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
        }

        public T4 component4() {
            return (T4) component(3);
        }

        public T5 component5() {
            return (T5) component(4);
        }

        public T6 component6() {
            return (T6) component(5);
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

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
        }

        public T4 component4() {
            return (T4) component(3);
        }

        public T5 component5() {
            return (T5) component(4);
        }

        public T6 component6() {
            return (T6) component(5);
        }

        public T7 component7() {
            return (T7) component(6);
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

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
        }

        public T4 component4() {
            return (T4) component(3);
        }

        public T5 component5() {
            return (T5) component(4);
        }

        public T6 component6() {
            return (T6) component(5);
        }

        public T7 component7() {
            return (T7) component(6);
        }

        public T8 component8() {
            return (T8) component(7);
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

        public T1 component1() {
            return (T1) component(0);
        }

        public T2 component2() {
            return (T2) component(1);
        }

        public T3 component3() {
            return (T3) component(2);
        }

        public T4 component4() {
            return (T4) component(3);
        }

        public T5 component5() {
            return (T5) component(4);
        }

        public T6 component6() {
            return (T6) component(5);
        }

        public T7 component7() {
            return (T7) component(6);
        }

        public T8 component8() {
            return (T8) component(7);
        }

        public T9 component9() {
            return (T9) component(8);
        }
    }
}

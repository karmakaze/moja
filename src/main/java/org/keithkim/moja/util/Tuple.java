package org.keithkim.moja.util;

import java.util.Arrays;

public class Tuple<T> implements IndexValued<T> {
    private final Object[] values;

    public static <T> Tuple<T> of(T... values) {
        return new Tuple<>(values);
    }

    protected Tuple(T... values) {
        this.values = Arrays.copyOf(values, values.length, Object[].class);
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
        if (o instanceof Tuple<?>) {
            Tuple<?> that = (Tuple<?>) o;
            if (this.values.length == that.values.length) {
                return Arrays.equals(this.values, that.values);
            }
        }
        return false;
    }

    public static class Tuple1<A> extends Tuple<A> {
        public static <A> Tuple1<A> of(A value) {
            return new Tuple1<>(value);
        }

        public Tuple1(A value) {
            super(value);
        }

        public A value1() {return value(0);}
    }

    public static class Tuple2<A, B> extends Tuple<Object> {
        public static <A, B> Tuple2<A, B> of(A v1, B v2) {
            return new Tuple2<>(v1, v2);
        }

        public Tuple2(A v1, B v2) {
            super(v1, v2);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
    }

    public static class Tuple3<A, B, C> extends Tuple<Object> {
        public static <A, B, C> Tuple3<A, B, C> of(A v1, B v2, C v3) {
            return new Tuple3<>(v1, v2, v3);
        }

        public Tuple3(A v1, B v2, C v3) {
            super(v1, v2, v3);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
    }

    public static class Tuple4<A, B, C, D> extends Tuple<Object> {
        public static <A, B, C, D>
        Tuple4<A, B, C, D>
        of(A v1, B v2, C v3, D v4) {
            return new Tuple4<>(v1, v2, v3, v4);
        }

        public Tuple4(A v1, B v2, C v3, D v4) {
            super(v1, v2, v3, v4);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
    }

    public static class Tuple5<A, B, C, D, E> extends Tuple<Object> {
        public static <A, B, C, D, E>
        Tuple5<A, B, C, D, E>
        of(A v1, B v2, C v3, D v4, E v5) {
            return new Tuple5<>(v1, v2, v3, v4, v5);
        }

        public Tuple5(A v1, B v2, C v3, D v4, E v5) {
            super(v1, v2, v3, v4, v5);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
        public E value5() {return (E) value(4);}
    }

    public static class Tuple6<A, B, C, D, E, F> extends Tuple<Object> {
        public static <A, B, C, D, E, F>
        Tuple6<A, B, C, D, E, F>
        of(A v1, B v2, C v3, D v4, E v5, F v6) {
            return new Tuple6<>(v1, v2, v3, v4, v5, v6);
        }

        public Tuple6(A v1, B v2, C v3, D v4, E v5, F v6) {
            super(v1, v2, v3, v4, v5, v6);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
        public E value5() {return (E) value(4);}
        public F value6() {return (F) value(5);}
    }

    public static class Tuple7<A, B, C, D, E, F, G> extends Tuple<Object> {
        public static <A, B, C, D, E, F, G>
        Tuple7<A, B, C, D, E, F, G>
        of(A v1, B v2, C v3, D v4, E v5, F v6, G v7) {
            return new Tuple7<>(v1, v2, v3, v4, v5, v6, v7);
        }

        public Tuple7(A v1, B v2, C v3, D v4, E v5, F v6, G v7) {
            super(v1, v2, v3, v4, v5, v6, v7);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
        public E value5() {return (E) value(4);}
        public F value6() {return (F) value(5);}
        public G value7() {return (G) value(6);}
    }

    public static class Tuple8<A, B, C, D, E, F, G, H> extends Tuple<Object> {
        public static <A, B, C, D, E, F, G, H>
        Tuple8<A, B, C, D, E, F, G, H>
        of(A v1, B v2, C v3, D v4, E v5, F v6, G v7, H v8) {
            return new Tuple8<>(v1, v2, v3, v4, v5, v6, v7, v8);
        }

        public Tuple8(A v1, B v2, C v3, D v4, E v5, F v6, G v7, H v8) {
            super(v1, v2, v3, v4, v5, v6, v7, v8);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
        public E value5() {return (E) value(4);}
        public F value6() {return (F) value(5);}
        public G value7() {return (G) value(6);}
        public H value8() {return (H) value(7);}
    }

    public static class Tuple9<A, B, C, D, E, F, G, H, I> extends Tuple<Object> {
        public static <A, B, C, D, E, F, G, H, I> 
        Tuple9<A, B, C, D, E, F, G, H, I>
        of(A v1, B v2, C v3, D v4, E v5, F v6, G v7, H v8, I v9) {
            return new Tuple9<>(v1, v2, v3, v4, v5, v6, v7, v8, v9);
        }

        public Tuple9(A v1, B v2, C v3, D v4, E v5, F v6, G v7, H v8, I v9) {
            super(v1, v2, v3, v4, v5, v6, v7, v8, v9);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
        public E value5() {return (E) value(4);}
        public F value6() {return (F) value(5);}
        public G value7() {return (G) value(6);}
        public H value8() {return (H) value(7);}
        public I value9() {return (I) value(8);}
    }
}

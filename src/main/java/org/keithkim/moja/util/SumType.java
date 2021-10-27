package org.keithkim.moja.util;

import java.util.Objects;

public class SumType<A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                            G extends T, H extends T, I extends T, T>
        implements Either<A, B, T>, Option3<A, B, C, T>, Option4<A, B, C, D, T>, Option5<A, B, C, D, E, T>,
        Option6<A, B, C, D, E, F, T>, Option7<A, B, C, D, E, F, G, T>, Option8<A, B, C, D, E, F, G, H, T>,
        Option9<A, B, C, D, E, F, G, H, I, T>, Comparable<Object> {
    final int index;
    final T value;

    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value1(A value) {
        return new SumType<>(0, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value2(B value) {
        return new SumType<>(1, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value3(C value) {
        return new SumType<>(2, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value4(D value) {
        return new SumType<>(3, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value5(E value) {
        return new SumType<>(4, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value6(F value) {
        return new SumType<>(5, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value7(G value) {
        return new SumType<>(6, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value8(H value) {
        return new SumType<>(7, value);
    }
    public static <A extends T, B extends T, C extends T, D extends T, E extends T, F extends T,
                                                          G extends T, H extends T, I extends T, T>
    SumType<A, B, C, D, E, F, G, H, I, T> value9(I value) {
        return new SumType<>(8, value);
    }

    private SumType(int index, T value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }

    public int index() {
        return index;
    }

    @Override
    public String toString() {
        return "SumType<"+ value +": "+ value.getClass().getSimpleName() +">";
    }

    @Override
    public int hashCode() {
        int h = "moja.util.SumType".hashCode();
        h = h * 31 + Objects.hashCode(value);
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof SumType) {
            SumType that = (SumType) o;
            return Objects.equals(this.value, that.value);
        }
        return false;
    }

    @Override
    public int compareTo(Object other) {
        if (this == other) {
            return 0;
        }
        if (other == null) {
            return -1;
        }
        if (!(other instanceof SumType)) {
            return -1;
        }
        SumType<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> that = (SumType<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) other;

        Object a = this.value;
        Object b = that.value;
        int compare = -1;
        if (a instanceof Comparable && a.getClass().isInstance(b)) {
            compare = ((Comparable<Object>) a).compareTo(b);
        } else if (b instanceof Comparable && b.getClass().isInstance(a)) {
            compare = -((Comparable<Object>) b).compareTo(a);
        }
        return compare;
    }
}

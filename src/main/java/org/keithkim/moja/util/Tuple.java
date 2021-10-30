package org.keithkim.moja.util;

import java.util.Arrays;
import java.util.Objects;

public abstract class Tuple<T> implements Indexed<T>, Comparable<Tuple<T>> {
    final String name;
    final Object[] values;

    public static <A, B> Pair<A, B> pair(A a, B b) {
        return Pair.of(a, b);
    }
    public static <A, B> Pair<A, B> tuple(A a, B b) {
        return Pair.of(a, b);
    }
    public static <A, B, C> Triple<A, B, C> triple(A a, B b, C c) {
        return Triple.of(a, b, c);
    }
    public static <A, B, C> Triple<A, B, C> tuple(A a, B b, C c) {
        return Triple.of(a, b, c);
    }
    public static <A, B, C, D> Tuple4<A, B, C, D> tuple(A a, B b, C c, D d) {
        return Tuple4.of(a, b, c, d);
    }
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> tuple(A a, B b, C c, D d, E e) {
        return Tuple5.of(a, b, c, d, e);
    }
    public static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> tuple(A a, B b, C c, D d, E e, F f) {
        return Tuple6.of(a, b, c, d, e, f);
    }
    public static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> tuple(A a, B b, C c, D d, E e, F f, G g) {
        return Tuple7.of(a, b, c, d, e, f, g);
    }
    public static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> tuple(A a, B b, C c, D d, E e, F f, G g, H h) {
        return Tuple8.of(a, b, c, d, e, f, g, h);
    }
    public static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> tuple(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return Tuple9.of(a, b, c, d, e, f, g, h, i);
    }

    public static <A, B> Pair<A, B> named(String name, A a, B b) {
        return Pair.named(name, a, b);
    }
    public static <A, B, C> Triple<A, B, C> named(String name, A a, B b, C c) {
        return Triple.named(name, a, b, c);
    }
    public static <A, B, C, D> Tuple4<A, B, C, D> named(String name, A a, B b, C c, D d) {
        return Tuple4.named(name, a, b, c, d);
    }
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> named(String name, A a, B b, C c, D d, E e) {
        return Tuple5.named(name, a, b, c, d, e);
    }
    public static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> named(String name, A a, B b, C c, D d, E e, F f) {
        return Tuple6.named(name, a, b, c, d, e, f);
    }
    public static <A, B, C, D, E, F, G>
    Tuple7<A, B, C, D, E, F, G> named(String name, A a, B b, C c, D d, E e, F f, G g) {
        return Tuple7.named(name, a, b, c, d, e, f, g);
    }
    public static <A, B, C, D, E, F, G, H>
    Tuple8<A, B, C, D, E, F, G, H> named(String name, A a, B b, C c, D d, E e, F f, G g, H h) {
        return Tuple8.named(name, a, b, c, d, e, f, g, h);
    }
    public static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> named(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return Tuple9.named(name, a, b, c, d, e, f, g, h, i);
    }

    Tuple(String name, T... values) {
        this.name = name;
        this.values = Arrays.copyOf(values, values.length, Object[].class);
    }

    @Override
    public int width() {
        return values.length;
    }

    @Override
    public T value(int index) {
        return (T) values[index];
    }

    @Override
    public String toString() {
        String string = Arrays.toString(values);
        return name + "("+ string.substring(1, string.length() - 1) +")";
    }

    @Override
    public int hashCode() {
        int h = "moja.util.Tuple".hashCode();
        h = h * 31 + values.length;
        h = h * 31 + name.hashCode();
        for (Object o : values) {
            h = h * 31 + Objects.hashCode(o);
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Tuple<?>) {
            Tuple<?> that = (Tuple<?>) o;
            if (this.values.length == that.values.length && this.name.equals(that.name)) {
                return Arrays.equals(this.values, that.values);
            }
        }
        return false;
    }

    @Override
    public int compareTo(Tuple<T> that) {
        if (this == that) {
            return 0;
        }
        if (that == null) {
            return -1;
        }
        if (this.width() != that.width()) {
            return this.width() < that.width() ? -1 : 1;
        }

        int compare = this.name.compareTo(that.name);
        if (compare != 0) {
            return compare;
        }
        for (int i = 0; i < this.width(); i++) {
            Object a = this.values[i];
            Object b = that.values[i];
            if (a instanceof Comparable && a.getClass().isInstance(b)) {
                compare = ((Comparable<Object>) a).compareTo(b);
                if (compare != 0) {
                    return compare;
                }
            } else {
                return -1;
            }
        }
        return 0;
    }

}

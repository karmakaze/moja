package org.keithkim.moja.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public interface Tuple<T> extends Indexed<T> {
    String name();
    List<Object> values();

    static <A, B> Pair<A, B> pair(A a, B b) {
        return Pair.make(a, b);
    }

    static <A, B> Pair<A, B> tuple(A a, B b) {
        return Pair.make(a, b);
    }

    static <A, B, C> Triple<A, B, C> triple(A a, B b, C c) {
        return Triple.make(a, b, c);
    }

    static <A, B, C> Triple<A, B, C> tuple(A a, B b, C c) {
        return Triple.make(a, b, c);
    }

    static <A, B, C, D> Tuple4<A, B, C, D> tuple(A a, B b, C c, D d) {
        return Tuple4.make(a, b, c, d);
    }

    static <A, B, C, D, E> Tuple5<A, B, C, D, E> tuple(A a, B b, C c, D d, E e) {
        return Tuple5.make(a, b, c, d, e);
    }

    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> tuple(A a, B b, C c, D d, E e, F f) {
        return Tuple6.make(a, b, c, d, e, f);
    }

    static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> tuple(A a, B b, C c, D d, E e, F f, G g) {
        return Tuple7.make(a, b, c, d, e, f, g);
    }

    static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> tuple(A a, B b, C c, D d, E e, F f, G g, H h) {
        return Tuple8.make(a, b, c, d, e, f, g, h);
    }

    static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> tuple(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return Tuple9.make(a, b, c, d, e, f, g, h, i);
    }

    static <A, B> Pair<A, B> named(String name, A a, B b) {
        return Pair.named(name, a, b);
    }

    static <A, B, C> Triple<A, B, C> named(String name, A a, B b, C c) {
        return Triple.named(name, a, b, c);
    }

    static <A, B, C, D> Tuple4<A, B, C, D> named(String name, A a, B b, C c, D d) {
        return Tuple4.named(name, a, b, c, d);
    }

    static <A, B, C, D, E> Tuple5<A, B, C, D, E> named(String name, A a, B b, C c, D d, E e) {
        return Tuple5.named(name, a, b, c, d, e);
    }

    static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> named(String name, A a, B b, C c, D d, E e, F f) {
        return Tuple6.named(name, a, b, c, d, e, f);
    }

    static <A, B, C, D, E, F, G>
    Tuple7<A, B, C, D, E, F, G> named(String name, A a, B b, C c, D d, E e, F f, G g) {
        return Tuple7.named(name, a, b, c, d, e, f, g);
    }

    static <A, B, C, D, E, F, G, H>
    Tuple8<A, B, C, D, E, F, G, H> named(String name, A a, B b, C c, D d, E e, F f, G g, H h) {
        return Tuple8.named(name, a, b, c, d, e, f, g, h);
    }

    static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> named(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return Tuple9.named(name, a, b, c, d, e, f, g, h, i);
    }

    abstract class AbstractTuple<T> implements Tuple<T>, Comparable<Tuple<T>> {
        protected final String name;
        protected final Object[] values;

        protected AbstractTuple(String name, T... values) {
            this.name = name;
            this.values = Arrays.copyOf(values, values.length, Object[].class);
        }

        @Override
        public List<Object> values() {
            return asList(values);
        }

        @Override
        public String name() {
            return name;
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
            return name + "(" + string.substring(1, string.length() - 1) + ")";
        }

        @Override
        public int hashCode() {
            int h = "moja.Tuple".hashCode();
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
            if (o instanceof NamedTuple) {
                return false;
            }
            if (o instanceof Tuple<?>) {
                Tuple<?> that = (Tuple) o;
                if (!this.name.equals(that.name())) {
                    return false;
                }
                List<Object> thatValues = that.values();
                if (this.values.length != thatValues.size()) {
                    return false;
                }
                return this.values().equals(thatValues);
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

            int compare = this.name.compareTo(that.name());
            if (compare != 0) {
                return compare;
            }
            List<Object> thatValues = that.values();
            for (int i = 0; i < this.width(); i++) {
                Object a = this.values[i];
                Object b = thatValues.get(i);
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
}

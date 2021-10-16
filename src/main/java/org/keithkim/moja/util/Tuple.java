package org.keithkim.moja.util;

import java.util.Arrays;
import java.util.Objects;

abstract class Tuple<T> implements IndexValued<T>, Comparable<Tuple<T>> {
    final String name;
    final Object[] values;

    public static <A, B> Pair<A, B> of(A a, B b) {
        return Pair.of(a, b);
    }
    public static <A, B, C> Triple<A, B, C> of(A a, B b, C c) {
        return Triple.of(a, b, c);
    }
    public static <A, B, C, D> Tuple4<A, B, C, D> of(A a, B b, C c, D d) {
        return Tuple4.of(a, b, c, d);
    }
    public static <A, B, C, D, E> Tuple5<A, B, C, D, E> of(A a, B b, C c, D d, E e) {
        return Tuple5.of(a, b, c, d, e);
    }
    public static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> of(A a, B b, C c, D d, E e, F f) {
        return Tuple6.of(a, b, c, d, e, f);
    }
    public static <A, B, C, D, E, F, G> Tuple7<A, B, C, D, E, F, G> of(A a, B b, C c, D d, E e, F f, G g) {
        return Tuple7.of(a, b, c, d, e, f, g);
    }
    public static <A, B, C, D, E, F, G, H> Tuple8<A, B, C, D, E, F, G, H> of(A a, B b, C c, D d, E e, F f, G g, H h) {
        return Tuple8.of(a, b, c, d, e, f, g, h);
    }
    public static <A, B, C, D, E, F, G, H, I>
    Tuple9<A, B, C, D, E, F, G, H, I> of(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
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
        for (int i = 0; i < this.width(); i++) {
            Object a = this.values[i];
            Object b = that.values[i];
            if (a instanceof Comparable && a.getClass().isInstance(b)) {
                int compare = ((Comparable<Object>) a).compareTo(b);
                if (compare != 0) {
                    return compare;
                }
            } else {
                return -1;
            }
        }
        return 0;
    }

    public static class Pair<A, B> extends Tuple<Object> {
        public static <A, B> Pair<A, B> of(A v1, B v2) {
            return new Pair<>("Pair", v1, v2);
        }

        public static <A, B> Pair<A, B> named(String name, A a, B b) {
            return new Pair<>(name, a, b);
        }

        Pair(String name, A v1, B v2) {
            super(name, v1, v2);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
    }

    public static class Triple<A, B, C> extends Tuple<Object> {
        public static <A, B, C> Triple<A, B, C> of(A v1, B v2, C v3) {
            return new Triple<>("Triple", v1, v2, v3);
        }

        public static <A, B, C> Triple<A, B, C> named(String name, A a, B b, C c) {
            return new Triple<>(name, a, b, c);
        }

        Triple(String name, A a, B b, C c) {
            super(name, a, b, c);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
    }

    public static class Tuple4<A, B, C, D> extends Tuple<Object> {
        public static <A, B, C, D>
        Tuple4<A, B, C, D>
        of(A a, B b, C c, D d) {
            return new Tuple4<>("Tuple4", a, b, c, d);
        }

        public static <A, B, C, D> Tuple4<A, B, C, D> named(String name, A a, B b, C c, D d) {
            return new Tuple4<>(name, a, b, c, d);
        }

        Tuple4(String name, A a, B b, C c, D d) {
            super(name, a, b, c, d);
        }

        public A value1() {return (A) value(0);}
        public B value2() {return (B) value(1);}
        public C value3() {return (C) value(2);}
        public D value4() {return (D) value(3);}
    }

    public static class Tuple5<A, B, C, D, E> extends Tuple<Object> {
        public static <A, B, C, D, E>
        Tuple5<A, B, C, D, E>
        of(A a, B b, C c, D d, E e) {
            return new Tuple5<>("Tuple5", a, b, c, d, e);
        }

        public static <A, B, C, D, E> Tuple5<A, B, C, D, E> named(String name, A a, B b, C c, D d, E e) {
            return new Tuple5<>(name, a, b, c, d, e);
        }

        Tuple5(String name, A a, B b, C c, D d, E e) {
            super(name, a, b, c, d, e);
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
        of(A a, B b, C c, D d, E e, F f) {
            return new Tuple6<>("Tuple6", a, b, c, d, e, f);
        }

        public static <A, B, C, D, E, F> Tuple6<A, B, C, D, E, F> named(String name, A a, B b, C c, D d, E e, F f) {
            return new Tuple6<>(name, a, b, c, d, e, f);
        }

        Tuple6(String name, A a, B b, C c, D d, E e, F f) {
            super(name, a, b, c, d, e, f);
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
        of(A a, B b, C c, D d, E e, F f, G g) {
            return new Tuple7<>("Tuple7", a, b, c, d, e, f, g);
        }

        public static <A, B, C, D, E, F, G>
        Tuple7<A, B, C, D, E, F, G> named(String name, A a, B b, C c, D d, E e, F f, G g) {
            return new Tuple7<>(name, a, b, c, d, e, f, g);
        }

        Tuple7(String name, A a, B b, C c, D d, E e, F f, G g) {
            super(name, a, b, c, d, e, f, g);
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
        of(A a, B b, C c, D d, E e, F f, G g, H h) {
            return new Tuple8<>("Tuple8", a, b, c, d, e, f, g, h);
        }

        public static <A, B, C, D, E, F, G, H>
        Tuple8<A, B, C, D, E, F, G, H> named(String name, A a, B b, C c, D d, E e, F f, G g, H h) {
            return new Tuple8<>(name, a, b, c, d, e, f, g, h);
        }

        Tuple8(String name, A a, B b, C c, D d, E e, F f, G g, H h) {
            super(name, a, b, c, d, e, f, g, h);
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
        Tuple9<A, B, C, D, E, F, G, H, I> of(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            return new Tuple9<>("Tuple9", a, b, c, d, e, f, g, h, i);
        }

        public static <A, B, C, D, E, F, G, H, I>
        Tuple9<A, B, C, D, E, F, G, H, I> named(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            return new Tuple9<>(name, a, b, c, d, e, f, g, h, i);
        }

        public Tuple9(String name, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            super(name, a, b, c, d, e, f, g, h, i);
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

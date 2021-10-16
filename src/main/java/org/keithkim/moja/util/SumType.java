package org.keithkim.moja.util;

import java.util.Objects;
import java.util.function.Function;

public class SumType<T, SELF extends SumType> implements IndexValued<T>, Comparable<SumType<?, ?>> {
    final int width;
    final int index;
    final T value;

    SumType(int width, int index, T value) {
        this.width = width;
        this.index = index;
        this.value = value;
    }
    @Override
    public T value(int index) {
        return index == this.index ? value : null;
    }
    @Override
    public int width() {
        return width;
    }
    public int index() {
        return index;
    }

    @Override
    public String toString() {
        return toString("SumType"+width);
    }

    String toString(String name) {
        return name + "("+ value +")";
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
    public int compareTo(SumType<?, ?> that) {
        if (this == that) {
            return 0;
        }
        if (that == null) {
            return -1;
        }
        Object a = this.value;
        Object b = that.value;
        int compare = -1;
        if (a instanceof Comparable && a.getClass().isInstance(b)) {
            compare = ((Comparable<Object>) a).compareTo(b);
        }
        return compare;
    }

    public static class Either<A, B> extends SumType<Object, Either<A, B>>
            implements IndexValued.Indexed2<Object, Either<A, B>, A, B> {

        public static <A, B> Either<A, B> value1(A v1) { return new Either(0, v1); }
        public static <A, B> Either<A, B> value2(B v2) { return new Either(1, v2); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2) {
            Function<Object, U>[] fs = new Function[] { f1, f2 };
            return fs[index].apply(value);
        }

        public <AA, BB> Either<AA, BB> then2(Function<A, AA> f1, Function<B, BB> f2) {
            switch (index) {
                case 0: return Either.value1(f1.apply(this.value1()));
                case 1: return Either.value2(f2.apply(this.value2()));
                default: return null;
            }
        }

        public static <A, B, U> Function<Either<A, B>, U> f(Function<Either<A, B>, U> f) {
            return f;
        }

        Either(int index, Object value) {
            super(2, index, value);
        }

        @Override
        public String toString() {
            return toString("Either");
        }
    }

    public static class SumType3<A, B, C> extends SumType<Object, SumType3<A, B, C>>
            implements IndexValued.Indexed3<Object, SumType3<A, B, C>, A, B, C> {

        public static <V> SumType3 value1(V v1) { return new SumType3(0, v1); }
        public static <V> SumType3 value2(V v2) { return new SumType3(1, v2); }
        public static <V> SumType3 value3(V v3) { return new SumType3(2, v3); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC> SumType3<AA, BB, CC> then3(Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3) {
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

    public static class SumType4<A, B, C, D> extends SumType<Object, SumType4<A, B, C, D>>
            implements IndexValued.Indexed4<Object, SumType4<A, B, C, D>, A, B, C, D> {

        public static <V> SumType4 value1(V v1) { return new SumType4(0, v1); }
        public static <V> SumType4 value2(V v2) { return new SumType4(1, v2); }
        public static <V> SumType4 value3(V v3) { return new SumType4(2, v3); }
        public static <V> SumType4 value4(V v4) { return new SumType4(3, v4); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3, Function<D, U> f4) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC, DD>
        SumType4<AA, BB, CC, DD> then4(Function<A, AA> f1, Function<B, BB> f2,
                                       Function<C, CC> f3, Function<D, DD> f4) {
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

    public static class SumType5<A, B, C, D, E> extends SumType<Object, SumType5<A, B, C, D, E>>
            implements IndexValued.Indexed5<Object, SumType5<A, B, C, D, E>,
                                            A, B, C, D, E> {

        public static <V> SumType5 value1(V v1) { return new SumType5(0, v1); }
        public static <V> SumType5 value2(V v2) { return new SumType5(1, v2); }
        public static <V> SumType5 value3(V v3) { return new SumType5(2, v3); }
        public static <V> SumType5 value4(V v4) { return new SumType5(3, v4); }
        public static <V> SumType5 value5(V v5) { return new SumType5(4, v5); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2,
                          Function<C, U> f3, Function<D, U> f4, Function<E, U> f5) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC, DD, EE>
        SumType5<AA, BB, CC, DD, EE> then5(Function<A, AA> f1, Function<B, BB> f2,
                                           Function<C, CC> f3, Function<D, DD> f4, Function<E, EE> f5) {
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

    public static class SumType6<A, B, C, D, E, F> extends SumType<Object, SumType6<A, B, C, D, E, F>>
            implements IndexValued.Indexed6<Object, SumType6<A, B, C, D, E, F>,
                                            A, B, C, D, E, F> {

        public static <V> SumType6 value1(V v1) { return new SumType6(0, v1); }
        public static <V> SumType6 value2(V v2) { return new SumType6(1, v2); }
        public static <V> SumType6 value3(V v3) { return new SumType6(2, v3); }
        public static <V> SumType6 value4(V v4) { return new SumType6(3, v4); }
        public static <V> SumType6 value5(V v5) { return new SumType6(4, v5); }
        public static <V> SumType6 value6(V v6) { return new SumType6(5, v6); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3,
                          Function<D, U> f4, Function<E, U> f5, Function<F, U> f6) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC, DD, EE, FF>
        SumType6<AA, BB, CC, DD, EE, FF> then6(Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3,
                                               Function<D, DD> f4, Function<E, EE> f5, Function<F, FF> f6) {
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

    public static class SumType7<A, B, C, D, E, F, G>
            extends SumType<Object, SumType7<A, B, C, D, E, F, G>>
            implements IndexValued.Indexed7<Object, SumType7<A, B, C, D, E, F, G>,
                                            A, B, C, D, E, F, G> {

        public static <V> SumType7 value1(V v1) { return new SumType7(0, v1); }
        public static <V> SumType7 value2(V v2) { return new SumType7(1, v2); }
        public static <V> SumType7 value3(V v3) { return new SumType7(2, v3); }
        public static <V> SumType7 value4(V v4) { return new SumType7(3, v4); }
        public static <V> SumType7 value5(V v5) { return new SumType7(4, v5); }
        public static <V> SumType7 value6(V v6) { return new SumType7(5, v6); }
        public static <V> SumType7 value7(V v7) { return new SumType7(6, v7); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3,
                          Function<D, U> f4, Function<E, U> f5, Function<F, U> f6, Function<G, U> f7) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC, DD, EE, FF, GG>
        SumType7<AA, BB, CC, DD, EE, FF, GG> then7(
                Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3,
                Function<D, DD> f4, Function<E, EE> f5, Function<F, FF> f6, Function<G, GG> f7) {
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

    public static class SumType8<A, B, C, D, E, F, G, H>
            extends SumType<Object, SumType8<A, B, C, D, E, F, G, H>>
            implements IndexValued.Indexed8<Object, SumType8<A, B, C, D, E, F, G, H>,
                                            A, B, C, D, E, F, G, H> {

        public static <V> SumType8 value1(V v1) { return new SumType8(0, v1); }
        public static <V> SumType8 value2(V v2) { return new SumType8(1, v2); }
        public static <V> SumType8 value3(V v3) { return new SumType8(2, v3); }
        public static <V> SumType8 value4(V v4) { return new SumType8(3, v4); }
        public static <V> SumType8 value5(V v5) { return new SumType8(4, v5); }
        public static <V> SumType8 value6(V v6) { return new SumType8(5, v6); }
        public static <V> SumType8 value7(V v7) { return new SumType8(6, v7); }
        public static <V> SumType8 value8(V v8) { return new SumType8(7, v8); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3, Function<D, U> f4,
                          Function<E, U> f5, Function<F, U> f6, Function<G, U> f7, Function<H, U> f8) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC, DD, EE, FF, GG, HH>
        SumType8<AA, BB, CC, DD, EE, FF, GG, HH> then8(
                Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3, Function<D, DD> f4,
                Function<E, EE> f5, Function<F, FF> f6, Function<G, GG> f7, Function<H, HH> f8) {
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

    public static class SumType9<A, B, C, D, E, F, G, H, I>
            extends SumType<Object, SumType9<A, B, C, D, E, F, G, H, I>>
            implements IndexValued.Indexed9<Object, SumType9<A, B, C, D, E, F, G, H, I>,
                                            A, B, C, D, E, F, G, H, I> {

        public static <V> SumType9 value1(V v1) { return new SumType9(0, v1); }
        public static <V> SumType9 value2(V v2) { return new SumType9(1, v2); }
        public static <V> SumType9 value3(V v3) { return new SumType9(2, v3); }
        public static <V> SumType9 value4(V v4) { return new SumType9(3, v4); }
        public static <V> SumType9 value5(V v5) { return new SumType9(4, v5); }
        public static <V> SumType9 value6(V v6) { return new SumType9(5, v6); }
        public static <V> SumType9 value7(V v7) { return new SumType9(6, v7); }
        public static <V> SumType9 value8(V v8) { return new SumType9(7, v8); }
        public static <V> SumType9 value9(V v9) { return new SumType9(8, v9); }

        public <U> U then(Function<A, U> f1, Function<B, U> f2, Function<C, U> f3,
                          Function<D, U> f4, Function<E, U> f5, Function<F, U> f6,
                          Function<G, U> f7, Function<H, U> f8, Function<I, U> f9) {
            Function<Object, U>[] fs = new Function[] { f1, f2, f3, f4, f5, f6, f7, f8, f9 };
            return fs[index].apply(value);
        }

        public <AA, BB, CC, DD, EE, FF, GG, HH, II>
        SumType9<AA, BB, CC, DD, EE, FF, GG, HH, II> then9(
                Function<A, AA> f1, Function<B, BB> f2, Function<C, CC> f3,
                Function<D, DD> f4, Function<E, EE> f5, Function<F, FF> f6,
                Function<G, GG> f7, Function<H, HH> f8, Function<I, II> f9) {
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

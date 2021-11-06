package org.keithkim.moja.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public interface NamedTuple extends Map<String, Object>, Tuple<Object>, Comparable<Tuple<Object>> {
    interface NamedPairMaker<A, B> extends Maker {
        NamedPair<A, B> make(A a, B b);
    }
    interface NamedTripleMaker<A, B, C> extends Maker {
        NamedTriple<A, B, C> make(A a, B b, C c);
    }
    interface NamedTuple4Maker<A, B, C, D>  extends Maker {
        NamedTuple4<A, B, C, D> make(A a, B b, C c, D d);
    }
    interface NamedTuple5Maker<A, B, C, D, E> extends Maker {
        NamedTuple5<A, B, C, D, E> make(A a, B b, C c, D d, E e);
    }
    interface NamedTuple6Maker<A, B, C, D, E, F> extends Maker {
        NamedTuple6<A, B, C, D, E, F> make(A a, B b, C c, D d, E e, F f);
    }
    interface NamedTuple7Maker<A, B, C, D, E, F, G> extends Maker {
        NamedTuple7<A, B, C, D, E, F, G> make(A a, B b, C c, D d, E e, F f, G g);
    }
    interface NamedTuple8Maker<A, B, C, D, E, F, G, H> extends Maker {
        NamedTuple8<A, B, C, D, E, F, G, H> make(A a, B b, C c, D d, E e, F f, G g, H h);
    }
    interface NamedTuple9Maker<A, B, C, D, E, F, G, H, I> extends Maker {
        NamedTuple9<A, B, C, D, E, F, G, H, I> make(A a, B b, C c, D d, E e, F f, G g, H h, I i);
    }
    interface NamedTuple26Maker<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
            extends Maker {
        NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> make(
                A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m,
                N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z);
    }

    static <A, B> NamedPairMaker<A, B> pairMaker(String name, String nameA, String nameB) {
        return new MakeNamedPair<>(name, nameA, nameB);
    }
    static <A, B, C> NamedTripleMaker<A, B, C> tripleMaker(String name, String nameA, String nameB, String nameC) {
        return new MakeNamedTriple<>(name, nameA, nameB, nameC);
    }
    static <A, B, C, D>
    NamedTuple4Maker<A, B, C, D> maker(String name, String nameA, String nameB, String nameC, String nameD) {
        return new MakeNamedTuple4<>(name, nameA, nameB, nameC, nameD);
    }
    static <A, B, C, D, E>
    NamedTuple5Maker<A, B, C, D, E> maker(String name, String nameA, String nameB,
                                                       String nameC, String nameD, String nameE) {
        return new MakeNamedTuple5<>(name, nameA, nameB, nameC, nameD, nameE);
    }
    static <A, B, C, D, E, F>
    NamedTuple6Maker<A, B, C, D, E, F> maker(String name, String nameA, String nameB, String nameC,
                                                          String nameD, String nameE, String nameF) {
        return new MakeNamedTuple6<>(name, nameA, nameB, nameC, nameD, nameE, nameF);
    }
    static <A, B, C, D, E, F, G>
    NamedTuple7Maker<A, B, C, D, E, F, G> maker(String name, String nameA, String nameB, String nameC,
                                                             String nameD, String nameE, String nameF, String nameG) {
        return new MakeNamedTuple7<>(name, nameA, nameB, nameC, nameD, nameE, nameF, nameG);
    }
    static <A, B, C, D, E, F, G, H>
    NamedTuple8Maker<A, B, C, D, E, F, G, H> maker(String name, String nameA, String nameB, String nameC,
                                    String nameD, String nameE, String nameF, String nameG, String nameH) {
        return new MakeNamedTuple8<>(name, nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH);
    }
    static <A, B, C, D, E, F, G, H, I>
    NamedTuple9Maker<A, B, C, D, E, F, G, H, I> maker(String name, String nameA, String nameB, String nameC,
                         String nameD, String nameE, String nameF, String nameG, String nameH, String nameI) {
        return new MakeNamedTuple9<>(name, nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH, nameI);
    }

    Maker maker();
    String name();
    List<String> names();
    List<Object> values();
    Map<String, Object> namedValues();

    default int width() {
        return names().size();
    }

    default Object value(int index) {
        return values().get(index);
    }

    default Object get(Object key) {
        int i = names().indexOf(key);
        return i < 0 ? null : values().get(i);
    }

    default String getString(String name) {
        return (String) get(name);
    }
    default Boolean getBoolean(String name) {
        return (Boolean) get(name);
    }
    default Byte getByte(String name) {
        return (Byte) get(name);
    }
    default Character getCharacter(String name) {
        return (Character) get(name);
    }
    default Number getNumber(String name) {
        return (Number) get(name);
    }
    default Short getShort(String name) {
        return (Short) get(name);
    }
    default Integer getInteger(String name) {
        return (Integer) get(name);
    }
    default Long getLong(String name) {
        return (Long) get(name);
    }
    default BigInteger getBigInteger(String name) {
        return (BigInteger) get(name);
    }
    default BigDecimal getBigDecimal(String name) {
        return (BigDecimal) get(name);
    }
    default Float getFloat(String name) {
        return (Float) get(name);
    }
    default Double getDouble(String name) {
        return (Double) get(name);
    }
    default <V> List<V> getList(String name) {
        return (List<V>) get(name);
    }
    default <V> Set<V> getSet(String name) {
        return (Set<V>) get(name);
    }
    default <K, V> Map<K, V> getMap(String name) {
        return (Map<K, V>) get(name);
    }
    default <C extends Class<C>> Class<C> getClass(String name) {
        return (Class<C>) get(name);
    }
    default <E extends Enum<E>> Enum<E> getEnum(String name) {
        return (Enum<E>) get(name);
    }
    default Throwable getThrowable(String name) {
        return (Throwable) get(name);
    }

    default Object put(String key, Object value) {
        int i = names().indexOf(key);
        if (i < 0) {
            throw new UnsupportedOperationException();
        }
        Object oldValue = values().get(i);
        values().set(i, value);
        return oldValue;
    }

    default void putAll(Map<? extends String, ? extends Object> m) {
        for (Map.Entry<? extends String, ?> me : m.entrySet()) {
            put(me.getKey(), me.getValue());
        }
    }

    default int size() {
        return names().size();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean containsKey(Object key) {
        return names().contains(key);
    }

    default boolean containsValue(Object value) {
        return values().contains(value);
    }

    default Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    default void clear() {
        throw new UnsupportedOperationException();
    }

    default Set<String> keySet() {
        return new HashSet<String>((List<String>)names());
    }

    default Set<Map.Entry<String, Object>> entrySet() {
        return ((Map<String, Object>) namedValues()).entrySet();
    }

    interface Maker {
        String name();
        List<String> names();
        NamedTuple make(Map<String, ?> nameValues);

        static Map<String, Object> namedObjectValues(List<String> names, Object[] values) {
            Map<String, Object> map = new FixedKeysArrayMap<>(names);
            for (int i = 0; i < values.length; i++) {
                map.put(names.get(i), values[i]);
            }
            return map;
        }

        static int hashCode(NamedTuple tuple) {
            int h = "moja.NamedTuple".hashCode();
            h = h * 31 + tuple.name().hashCode();
            Iterator<String> nameIt = tuple.names().iterator();
            Iterator<Object> valueIt = tuple.values().iterator();
            while (nameIt.hasNext() && valueIt.hasNext()) {
                h = h * 31 + nameIt.next().hashCode();
                h = h * 31 + valueIt.next().hashCode();
            }
            return h;
        }

        static boolean equals(NamedTuple a, NamedTuple b) {
            if (!Objects.equals(a.name(), b.name()) || a.names().size() != b.names().size()) {
                return false;
            }
            if (a.maker() != b.maker()) {
                Iterator<String> aNames = a.names().iterator();
                Iterator<String> bNames = b.names().iterator();
                while (aNames.hasNext() && bNames.hasNext()) {
                    if (!Objects.equals(aNames.next(), bNames.next())) {
                        return false;
                    }
                }
            }
            Iterator<Object> aValues = a.values().iterator();
            Iterator<Object> bValues = b.values().iterator();
            while (aValues.hasNext() && bValues.hasNext()) {
                if (!Objects.equals(aValues.next(), bValues.next())) {
                    return false;
                }
            }
            return true;
        }

        static int compare(NamedTuple a, Tuple<Object> bTuple) {
            if (a == bTuple) {
                return 0;
            }
            if (bTuple == null) {
                return -1;
            }
            if (!(bTuple instanceof NamedTuple)) {
                return 1;
            }
            NamedTuple b = (NamedTuple) bTuple;
            int c = a.names().size() - b.names().size();
            if (c != 0) { return c; }

            c = a.name().compareTo(b.name());
            if (c != 0) { return c; }

            if (a.maker() != b.maker()) {
                Iterator<String> aNames = a.names().iterator();
                Iterator<String> bNames = b.names().iterator();
                while (aNames.hasNext() && bNames.hasNext()) {
                    c = aNames.next().compareTo(bNames.next());
                    if (c != 0) { return c; }
                }
            }
            Iterator<Object> aValues = a.values().iterator();
            Iterator<Object> bValues = b.values().iterator();
            while (aValues.hasNext() && bValues.hasNext()) {
                Object aValue = aValues.next();
                Object bValue = bValues.next();
                if (aValue != bValue) {
                    if (aValue instanceof Comparable && aValue.getClass().isInstance(bValue)) {
                        c = ((Comparable<Object>) aValue).compareTo(bValue);
                        if (c != 0) {
                            return c;
                        }
                    } else if (bValue instanceof Comparable && bValue.getClass().isInstance(aValue)) {
                        c = ((Comparable<Object>) bValue).compareTo(aValue);
                        if (c != 0) {
                            return -c;
                        }
                    } else {
                        return aValue.equals(bValue) ? 0 : -1;
                    }
                }
            }
            return 0;
        }

        static String toString(NamedTuple namedTuple) {
            Map<String, Object> map = namedTuple.namedValues();
            StringBuilder buffer = new StringBuilder(namedTuple.name() + "(");
            for (Map.Entry<String, ?> me : map.entrySet()) {
                buffer.append(me.getKey());
                buffer.append(":");
                buffer.append(me.getValue());
                buffer.append(", ");
            }
            buffer.setLength(buffer.length() - 2);
            buffer.append(")");
            return buffer.toString();
        }
    }

    static <A, B> Map<String, Object> namedValues(NamedPairImpl<A, B> namedPair) {
        Map<String, Object> map = new FixedKeysArrayMap<>(namedPair.makeTuple.names);
        for (int i = 0; i < namedPair.width(); i++) {
            map.put(namedPair.makeTuple.names.get(i), namedPair.values[i]);
        }
        return map;
    }

    static <A, B, C>
    Map<String, Object> namedValues(NamedTripleImpl<A, B, C> namedTriple) {
        Map<String, Object> map = new FixedKeysArrayMap<>(namedTriple.makeTuple.names);
        for (int i = 0; i < namedTriple.width(); i++) {
            map.put(namedTriple.makeTuple.names.get(i), namedTriple.values[i]);
        }
        return map;
    }

    abstract class MakeNamedTuple {
        final String name;
        final List<String> names;

        MakeNamedTuple(String name, List<String> names) {
            this.name = name;
            this.names = unmodifiableList(new ArrayList<>(names));
        }

        public String name() {
            return name;
        }

        public List<String> names() {
            return names;
        }
    }

    class MakeNamedPair<A, B> extends MakeNamedTuple implements NamedPairMaker<A, B> {
        public MakeNamedPair(String name, String nameA, String nameB) {
            super(name, asList(nameA, nameB));
        }

        public NamedPair<A, B> make(A a, B b) {
            requireNonNull(a);
            requireNonNull(b);
            return new NamedPairImpl<>(this, a, b);
        }
        @Override
        public NamedTuple make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            return make(a, b);
        }
    }

    class MakeNamedTriple<A, B, C> extends MakeNamedTuple implements NamedTripleMaker<A, B, C> {
        MakeNamedTriple(String name, String nameA, String nameB, String nameC) {
            super(name, asList(nameA, nameB, nameC));
        }

        public NamedTriple<A, B, C> make(A a, B b, C c) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            return new NamedTripleImpl<>(this, a, b, c);
        }
        @Override
        public NamedTuple make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            return make(a, b, c);
        }
    }

    class MakeNamedTuple4<A, B, C, D> extends MakeNamedTuple implements NamedTuple4Maker<A, B, C, D> {
        MakeNamedTuple4(String name, String nameA, String nameB, String nameC, String nameD) {
            super(name, asList(nameA, nameB, nameC, nameD));
        }

        public NamedTuple4<A, B, C, D> make(A a, B b, C c, D d) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            return new NamedTuple4Impl<>(this, a, b, c, d);
        }
        public NamedTuple4<A, B, C, D> make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            return make(a, b, c, d);
        }
    }

    class MakeNamedTuple5<A, B, C, D, E> extends MakeNamedTuple implements NamedTuple5Maker<A, B, C, D, E> {
        MakeNamedTuple5(String name, String nameA, String nameB, String nameC, String nameD, String nameE) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE));
        }

        public NamedTuple5<A, B, C, D, E> make(A a, B b, C c, D d, E e) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            requireNonNull(e);
            return new NamedTuple5Impl<>(this, a, b, c, d, e);
        }
        public NamedTuple5<A, B, C, D, E> make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            E e = (E) nameValues.get(names.get(4));
            return make(a, b, c, d, e);
        }
    }

    class MakeNamedTuple6<A, B, C, D, E, F> extends MakeNamedTuple implements NamedTuple6Maker<A, B, C, D, E, F> {
        MakeNamedTuple6(String name, String nameA, String nameB, String nameC,
                                     String nameD, String nameE, String nameF) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF));
        }

        public NamedTuple6<A, B, C, D, E, F> make(A a, B b, C c, D d, E e, F f) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            requireNonNull(e);
            requireNonNull(f);
            return new NamedTuple6Impl<>(this, a, b, c, d, e, f);
        }
        public NamedTuple6<A, B, C, D, E, F> make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            E e = (E) nameValues.get(names.get(4));
            F f = (F) nameValues.get(names.get(5));
            return make(a, b, c, d, e, f);
        }
    }

    class MakeNamedTuple7<A, B, C, D, E, F, G> extends MakeNamedTuple implements NamedTuple7Maker<A, B, C, D, E, F, G> {
        MakeNamedTuple7(String name, String nameA, String nameB, String nameC,
                                     String nameD, String nameE, String nameF, String nameG) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG));
        }

        public NamedTuple7<A, B, C, D, E, F, G> make(A a, B b, C c, D d, E e, F f, G g) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            requireNonNull(e);
            requireNonNull(f);
            requireNonNull(g);
            return new NamedTuple7Impl<>(this, a, b, c, d, e, f, g);
        }
        public NamedTuple7<A, B, C, D, E, F, G> make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            E e = (E) nameValues.get(names.get(4));
            F f = (F) nameValues.get(names.get(5));
            G g = (G) nameValues.get(names.get(6));
            return make(a, b, c, d, e, f, g);
        }
    }

    class MakeNamedTuple8<A, B, C, D, E, F, G, H> extends MakeNamedTuple
            implements NamedTuple8Maker<A, B, C, D, E, F, G, H> {
        MakeNamedTuple8(String name, String nameA, String nameB, String nameC, String nameD,
                                     String nameE, String nameF, String nameG, String nameH) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH));
        }

        public NamedTuple8<A, B, C, D, E, F, G, H> make(A a, B b, C c, D d, E e, F f, G g, H h) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            requireNonNull(e);
            requireNonNull(f);
            requireNonNull(g);
            requireNonNull(h);
            return new NamedTuple8Impl<>(this, a, b, c, d, e, f, g, h);
        }
        public NamedTuple8<A, B, C, D, E, F, G, H> make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            E e = (E) nameValues.get(names.get(4));
            F f = (F) nameValues.get(names.get(5));
            G g = (G) nameValues.get(names.get(6));
            H h = (H) nameValues.get(names.get(7));
            return make(a, b, c, d, e, f, g, h);
        }
    }

    class MakeNamedTuple9<A, B, C, D, E, F, G, H, I> extends MakeNamedTuple
            implements NamedTuple9Maker<A, B, C, D, E, F, G, H, I> {
        MakeNamedTuple9(String name, String nameA, String nameB, String nameC, String nameD,
                                     String nameE, String nameF, String nameG, String nameH, String nameI) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH, nameI));
        }

        public NamedTuple9<A, B, C, D, E, F, G, H, I> make(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            requireNonNull(e);
            requireNonNull(f);
            requireNonNull(g);
            requireNonNull(h);
            requireNonNull(i);
            return new NamedTuple9Impl<>(this, a, b, c, d, e, f, g, h, i);
        }
        public NamedTuple9<A, B, C, D, E, F, G, H, I> make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            E e = (E) nameValues.get(names.get(4));
            F f = (F) nameValues.get(names.get(5));
            G g = (G) nameValues.get(names.get(6));
            H h = (H) nameValues.get(names.get(7));
            I i = (I) nameValues.get(names.get(8));
            return make(a, b, c, d, e, f, g, h, i);
        }
    }

    class MakeNamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> extends MakeNamedTuple
            implements NamedTuple26Maker<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> {
        MakeNamedTuple26(String name, String nameA, String nameB, String nameC, String nameD, String nameE,
                String nameF, String nameG, String nameH, String nameI, String nameJ, String nameK, String nameL,
                String nameM, String nameN, String nameO, String nameP, String nameQ, String nameR, String nameS,
                String nameT, String nameU, String nameV, String nameW, String nameX, String nameY, String nameZ) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH, nameI, nameJ, nameK, nameL,
                 nameM, nameN, nameO, nameP, nameQ, nameR, nameS, nameT, nameU, nameV, nameW, nameX, nameY, nameZ));
        }

        public NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
        make(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l, M m,
             N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
            requireNonNull(a);
            requireNonNull(b);
            requireNonNull(c);
            requireNonNull(d);
            requireNonNull(e);
            requireNonNull(f);
            requireNonNull(g);
            requireNonNull(h);
            requireNonNull(i);
            requireNonNull(j);
            requireNonNull(k);
            requireNonNull(l);
            requireNonNull(m);
            requireNonNull(n);
            requireNonNull(o);
            requireNonNull(p);
            requireNonNull(q);
            requireNonNull(r);
            requireNonNull(s);
            requireNonNull(t);
            requireNonNull(u);
            requireNonNull(v);
            requireNonNull(w);
            requireNonNull(x);
            requireNonNull(y);
            requireNonNull(z);
            return new NamedTuple26Impl<>(this, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
        }
        public NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
        make(Map<String, ?> nameValues) {
            A a = (A) nameValues.get(names.get(0));
            B b = (B) nameValues.get(names.get(1));
            C c = (C) nameValues.get(names.get(2));
            D d = (D) nameValues.get(names.get(3));
            E e = (E) nameValues.get(names.get(4));
            F f = (F) nameValues.get(names.get(5));
            G g = (G) nameValues.get(names.get(6));
            H h = (H) nameValues.get(names.get(7));
            I i = (I) nameValues.get(names.get(8));
            J j = (J) nameValues.get(names.get(9));
            K k = (K) nameValues.get(names.get(10));
            L l = (L) nameValues.get(names.get(11));
            M m = (M) nameValues.get(names.get(12));
            N n = (N) nameValues.get(names.get(13));
            O o = (O) nameValues.get(names.get(14));
            P p = (P) nameValues.get(names.get(15));
            Q q = (Q) nameValues.get(names.get(16));
            R r = (R) nameValues.get(names.get(17));
            S s = (S) nameValues.get(names.get(18));
            T t = (T) nameValues.get(names.get(19));
            U u = (U) nameValues.get(names.get(20));
            V v = (V) nameValues.get(names.get(21));
            W w = (W) nameValues.get(names.get(22));
            X x = (X) nameValues.get(names.get(23));
            Y y = (Y) nameValues.get(names.get(24));
            Z z = (Z) nameValues.get(names.get(25));
            return make(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
        }
    }

    interface NamedPair<A, B> extends NamedTuple {
        @Override NamedPairMaker<A, B> maker();
    }
    class NamedPairImpl<A, B> extends Pair<A, B> implements NamedPair<A, B> {
        private final MakeNamedPair<A, B> makeTuple;

        public NamedPairImpl(MakeNamedPair<A, B> makeTuple, A a, B b) {
            super(makeTuple.name, a, b);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedPairMaker<A, B> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTriple<A, B, C> extends NamedTuple {
        @Override NamedTripleMaker<A, B, C> maker();
    }
    class NamedTripleImpl<A, B, C> extends Triple<A, B, C> implements NamedTriple<A, B, C> {
        private final MakeNamedTriple<A, B, C> makeTuple;

        NamedTripleImpl(MakeNamedTriple<A, B, C> makeTuple, A a, B b, C c) {
            super(makeTuple.name, a, b, c);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTripleMaker<A, B, C> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple4<A, B, C, D> extends NamedTuple {
        @Override NamedTuple4Maker<A, B, C, D> maker();
    }
    class NamedTuple4Impl<A, B, C, D> extends Tuple4<A, B, C, D> implements NamedTuple4<A, B, C, D> {
        private final MakeNamedTuple4<A, B, C, D> makeTuple;

        NamedTuple4Impl(MakeNamedTuple4<A, B, C, D> makeTuple, A a, B b, C c, D d) {
            super(makeTuple.name, a, b, c, d);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTuple4Maker<A, B, C, D> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple5<A, B, C, D, E> extends NamedTuple {
        @Override NamedTuple5Maker<A, B, C, D, E> maker();
    }
    class NamedTuple5Impl<A, B, C, D, E> extends Tuple5<A, B, C, D, E> implements NamedTuple5<A, B, C, D, E> {
        private final MakeNamedTuple5<A, B, C, D, E> makeTuple;

        NamedTuple5Impl(MakeNamedTuple5<A, B, C, D, E> makeTuple, A a, B b, C c, D d, E e) {
            super(makeTuple.name, a, b, c, d, e);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTuple5Maker<A, B, C, D, E> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple6<A, B, C, D, E, F> extends NamedTuple {
        @Override NamedTuple6Maker<A, B, C, D, E, F> maker();
    }
    class NamedTuple6Impl<A, B, C, D, E, F> extends Tuple6<A, B, C, D, E, F> implements NamedTuple6<A, B, C, D, E, F> {
        private final MakeNamedTuple6<A, B, C, D, E, F> makeTuple;

        NamedTuple6Impl(MakeNamedTuple6<A, B, C, D, E, F> makeTuple, A a, B b, C c, D d, E e, F f) {
            super(makeTuple.name, a, b, c, d, e, f);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTuple6Maker<A, B, C, D, E, F> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple7<A, B, C, D, E, F, G> extends NamedTuple {
        @Override NamedTuple7Maker<A, B, C, D, E, F, G> maker();
    }
    class NamedTuple7Impl<A, B, C, D, E, F, G> extends Tuple7<A, B, C, D, E, F, G>
            implements NamedTuple7<A, B, C, D, E, F, G> {
        private final NamedTuple7Maker<A, B, C, D, E, F, G> makeTuple;

        NamedTuple7Impl(NamedTuple7Maker<A, B, C, D, E, F, G> makeTuple, A a, B b, C c, D d, E e, F f, G g) {
            super(makeTuple.name(), a, b, c, d, e, f, g);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names();
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names(), values);
        }
        @Override
        public NamedTuple7Maker<A, B, C, D, E, F, G> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple8<A, B, C, D, E, F, G, H> extends NamedTuple {
        @Override NamedTuple8Maker<A, B, C, D, E, F, G, H> maker();
    }
    class NamedTuple8Impl<A, B, C, D, E, F, G, H> extends Tuple8<A, B, C, D, E, F, G, H>
            implements NamedTuple8<A, B, C, D, E, F, G, H> {
        private final MakeNamedTuple8<A, B, C, D, E, F, G, H> makeTuple;

        NamedTuple8Impl(MakeNamedTuple8<A, B, C, D, E, F, G, H> makeTuple, A a, B b, C c, D d, E e, F f, G g, H h) {
            super(makeTuple.name, a, b, c, d, e, f, g, h);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTuple8Maker<A, B, C, D, E, F, G, H> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple9<A, B, C, D, E, F, G, H, I> extends NamedTuple {
        @Override NamedTuple9Maker<A, B, C, D, E, F, G, H, I> maker();
    }
    class NamedTuple9Impl<A, B, C, D, E, F, G, H, I> extends Tuple9<A, B, C, D, E, F, G, H, I>
            implements NamedTuple9<A, B, C, D, E, F, G, H, I> {
        private final MakeNamedTuple9<A, B, C, D, E, F, G, H, I> makeTuple;

        NamedTuple9Impl(MakeNamedTuple9<A, B, C, D, E, F, G, H, I> makeTuple, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            super(makeTuple.name, a, b, c, d, e, f, g, h, i);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTuple9Maker<A, B, C, D, E, F, G, H, I> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }

    interface NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
            extends NamedTuple {
        @Override
        NamedTuple26Maker<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> maker();
    }
    class NamedTuple26Impl<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
            extends Tuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
            implements NamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> {
        private final MakeNamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
                makeTuple;

        NamedTuple26Impl(MakeNamedTuple26<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z>
                                 makeTuple, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k,
                         L l, M m, N n, O o, P p, Q q, R r, S s, T t, U u, V v, W w, X x, Y y, Z z) {
            super(makeTuple.name, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }
        @Override
        public List<String> names() {
            return makeTuple.names;
        }
        @Override
        public List<Object> values() {
            return asList(values);
        }
        @Override
        public Map<String, Object> namedValues() {
            return Maker.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public NamedTuple26Maker<A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z> maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return Maker.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && Maker.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return Maker.toString(this);
        }
    }
}

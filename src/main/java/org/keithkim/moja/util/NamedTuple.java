package org.keithkim.moja.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.Arrays.asList;

public interface NamedTuple<K extends String, V> extends Map<K, V> {
    MakeNamedTuple maker();
    String name();
    List<String> names();
    List<V> values();
    LinkedHashMap<String, Object> namedValues();

    static <S, A extends S, B extends S>
    LinkedHashMap<String, S> namedValues(NamedPair<A, B> namedPair) {
        LinkedHashMap<String, S> map = new LinkedHashMap<>(namedPair.width());
        for (int i = 0; i < namedPair.width(); i++) {
            map.put(namedPair.makeTuple.names.get(i), (S) namedPair.values[i]);
        }
        return map;
    }

    static <S, A extends S, B extends S, C extends S>
    LinkedHashMap<String, S> namedValues(NamedTriple<A, B, C> namedTriple) {
        LinkedHashMap<String, S> map = new LinkedHashMap<>(namedTriple.width());
        for (int i = 0; i < namedTriple.width(); i++) {
            map.put(namedTriple.makeTuple.names.get(i), (S) namedTriple.values[i]);
        }
        return map;
    }


    default V get(Object key) {
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
        values().set(i, (V) value);
        return oldValue;
    }

    default void putAll(Map<? extends K, ? extends V> m) {
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

    default V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    default void clear() {
        throw new UnsupportedOperationException();
    }

    default Set<K> keySet() {
        return new HashSet<K>((List<K>)names());
    }

    default Set<Map.Entry<K, V>> entrySet() {
        return ((Map<K, V>) namedValues()).entrySet();
    }

    class MakeNamedTuple {
        final String name;
        final List<String> names;

        static LinkedHashMap<String, Object> namedObjectValues(List<String> names, Object[] values) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(values.length);
            for (int i = 0; i < values.length; i++) {
                map.put(names.get(i), values[i]);
            }
            return map;
        }

        static <K extends String, V> int hashCode(NamedTuple<K, V> tuple) {
            int h = "moja.NamedTuple".hashCode();
            h = h * 31 + tuple.name().hashCode();
            Iterator<String> nameIt = tuple.names().iterator();
            Iterator<V> valueIt = tuple.values().iterator();
            while (nameIt.hasNext() && valueIt.hasNext()) {
                h = h * 31 + nameIt.next().hashCode();
                h = h * 31 + valueIt.next().hashCode();
            }
            return h;
        }

        static <K extends String, V> boolean equals(NamedTuple<K, V> a, NamedTuple<K, V> b) {
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
            Iterator<V> aValues = a.values().iterator();
            Iterator<V> bValues = b.values().iterator();
            while (aValues.hasNext() && bValues.hasNext()) {
                if (!Objects.equals(aValues.next(), bValues.next())) {
                    return false;
                }
            }
            return true;
        }

        static <K extends String, V> String toString(NamedTuple<K, V> namedTuple) {
            LinkedHashMap<String, Object> map = namedTuple.namedValues();
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

        MakeNamedTuple(String name, List<String> names) {
            this.name = name;
            this.names = names;
        }
    }

    class MakeNamedPair<A, B> extends MakeNamedTuple {
        public MakeNamedPair(String name, String nameA, String nameB) {
            super(name, asList(nameA, nameB));
        }

        public NamedPair<A, B> make(A a, B b) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            return new NamedPair<>(this, a, b);
        }
    }

    class MakeNamedTriple<A, B, C> extends MakeNamedTuple {
        MakeNamedTriple(String name, String nameA, String nameB, String nameC) {
            super(name, asList(nameA, nameB, nameC));
        }

        NamedTriple<A, B, C> make(A a, B b, C c) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            return new NamedTriple<>(this, a, b, c);
        }
    }

    class MakeNamedTuple4<A, B, C, D> extends MakeNamedTuple {
        MakeNamedTuple4(String name, String nameA, String nameB, String nameC, String nameD) {
            super(name, asList(nameA, nameB, nameC, nameD));
        }

        NamedTuple4<A, B, C, D> make(A a, B b, C c, D d) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            Objects.requireNonNull(d);
            return new NamedTuple4<>(this, a, b, c, d);
        }
    }

    class MakeNamedTuple5<A, B, C, D, E> extends MakeNamedTuple {
        MakeNamedTuple5(String name, String nameA, String nameB, String nameC, String nameD, String nameE) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE));
        }

        NamedTuple5<A, B, C, D, E> make(A a, B b, C c, D d, E e) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            Objects.requireNonNull(d);
            Objects.requireNonNull(e);
            return new NamedTuple5<>(this, a, b, c, d, e);
        }
    }

    class MakeNamedTuple6<A, B, C, D, E, F> extends MakeNamedTuple {
        MakeNamedTuple6(String name, String nameA, String nameB, String nameC,
                                     String nameD, String nameE, String nameF) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF));
        }

        NamedTuple6<A, B, C, D, E, F> make(A a, B b, C c, D d, E e, F f) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            Objects.requireNonNull(d);
            Objects.requireNonNull(e);
            Objects.requireNonNull(f);
            return new NamedTuple6<>(this, a, b, c, d, e, f);
        }
    }

    class MakeNamedTuple7<A, B, C, D, E, F, G> extends MakeNamedTuple {
        MakeNamedTuple7(String name, String nameA, String nameB, String nameC,
                                     String nameD, String nameE, String nameF, String nameG) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG));
        }

        NamedTuple7<A, B, C, D, E, F, G> make(A a, B b, C c, D d, E e, F f, G g) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            Objects.requireNonNull(d);
            Objects.requireNonNull(e);
            Objects.requireNonNull(f);
            Objects.requireNonNull(g);
            return new NamedTuple7<>(this, a, b, c, d, e, f, g);
        }
    }

    class MakeNamedTuple8<A, B, C, D, E, F, G, H> extends MakeNamedTuple {
        MakeNamedTuple8(String name, String nameA, String nameB, String nameC, String nameD,
                                     String nameE, String nameF, String nameG, String nameH) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH));
        }

        NamedTuple8<A, B, C, D, E, F, G, H> make(A a, B b, C c, D d, E e, F f, G g, H h) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            Objects.requireNonNull(d);
            Objects.requireNonNull(e);
            Objects.requireNonNull(f);
            Objects.requireNonNull(g);
            Objects.requireNonNull(h);
            return new NamedTuple8<>(this, a, b, c, d, e, f, g, h);
        }
    }

    class MakeNamedTuple9<A, B, C, D, E, F, G, H, I> extends MakeNamedTuple {
        MakeNamedTuple9(String name, String nameA, String nameB, String nameC, String nameD,
                                     String nameE, String nameF, String nameG, String nameH, String nameI) {
            super(name, asList(nameA, nameB, nameC, nameD, nameE, nameF, nameG, nameH, nameI));
        }

        NamedTuple9<A, B, C, D, E, F, G, H, I> make(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            Objects.requireNonNull(d);
            Objects.requireNonNull(e);
            Objects.requireNonNull(f);
            Objects.requireNonNull(g);
            Objects.requireNonNull(h);
            Objects.requireNonNull(i);
            return new NamedTuple9<>(this, a, b, c, d, e, f, g, h, i);
        }
    }

    class NamedPair<A, B> extends Pair<A, B> implements NamedTuple<String, Object> {
        private final MakeNamedPair<A, B> makeTuple;

        public NamedPair(MakeNamedPair<A, B> makeTuple, A a, B b) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public int hashCode() {
            return MakeNamedTuple.hashCode(this);
        }
        @Override
        public boolean equals(Object o) {
            return (o instanceof NamedTuple) && MakeNamedTuple.equals(this, (NamedTuple) o);
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTriple<A, B, C> extends Triple<A, B, C> implements NamedTuple<String, Object> {
        private final MakeNamedTriple<A, B, C> makeTuple;

        NamedTriple(MakeNamedTriple<A, B, C> makeTuple, A a, B b, C c) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTuple4<A, B, C, D> extends Tuple4<A, B, C, D> implements NamedTuple<String, Object> {
        private final MakeNamedTuple4<A, B, C, D> makeTuple;

        NamedTuple4(MakeNamedTuple4<A, B, C, D> makeTuple, A a, B b, C c, D d) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTuple5<A, B, C, D, E> extends Tuple5<A, B, C, D, E> implements NamedTuple<String, Object> {
        private final MakeNamedTuple5<A, B, C, D, E> makeTuple;

        NamedTuple5(MakeNamedTuple5<A, B, C, D, E> makeTuple, A a, B b, C c, D d, E e) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTuple6<A, B, C, D, E, F> extends Tuple6<A, B, C, D, E, F> implements NamedTuple<String, Object> {
        private final MakeNamedTuple6<A, B, C, D, E, F> makeTuple;

        NamedTuple6(MakeNamedTuple6<A, B, C, D, E, F> makeTuple, A a, B b, C c, D d, E e, F f) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTuple7<A, B, C, D, E, F, G> extends Tuple7<A, B, C, D, E, F, G>
            implements NamedTuple<String, Object> {
        private final MakeNamedTuple7<A, B, C, D, E, F, G> makeTuple;

        NamedTuple7(MakeNamedTuple7<A, B, C, D, E, F, G> makeTuple, A a, B b, C c, D d, E e, F f, G g) {
            super(makeTuple.name, a, b, c, d, e, f, g);
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTuple8<A, B, C, D, E, F, G, H> extends Tuple8<A, B, C, D, E, F, G, H>
            implements NamedTuple<String, Object> {
        private final MakeNamedTuple8<A, B, C, D, E, F, G, H> makeTuple;

        NamedTuple8(MakeNamedTuple8<A, B, C, D, E, F, G, H> makeTuple, A a, B b, C c, D d, E e, F f, G g, H h) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTuple9<A, B, C, D, E, F, G, H, I> extends Tuple9<A, B, C, D, E, F, G, H, I>
            implements NamedTuple<String, Object> {
        private final MakeNamedTuple9<A, B, C, D, E, F, G, H, I> makeTuple;

        NamedTuple9(MakeNamedTuple9<A, B, C, D, E, F, G, H, I> makeTuple, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
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
        public LinkedHashMap<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }
        @Override
        public MakeNamedTuple maker() {
            return makeTuple;
        }
        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }
}

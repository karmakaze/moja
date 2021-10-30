package org.keithkim.moja.util;

import java.util.*;

import static java.util.Arrays.asList;

public interface NamedTuple<K extends String, V> extends Map<K, V> {
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

    public class MakeNamedTuple {
        final String name;
        final List<String> names;

        static LinkedHashMap<String, Object> namedObjectValues(List<String> names, Object[] values) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>(values.length);
            for (int i = 0; i < values.length; i++) {
                map.put(names.get(i), values[i]);
            }
            return map;
        }

        static String toString(NamedTuple namedTuple) {
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

    public static class MakeNamedPair<A, B> extends MakeNamedTuple {
        public MakeNamedPair(String name, String nameA, String nameB) {
            super(name, asList(nameA, nameB));
        }

        public NamedPair<A, B> of(A a, B b) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            return new NamedPair<>(this, a, b);
        }
    }

    public static class MakeNamedTriple<A, B, C> extends MakeNamedTuple {
        MakeNamedTriple(String name, String nameA, String nameB, String nameC) {
            super(name, asList(nameA, nameB, nameC));
        }

        NamedTriple<A, B, C> of(A a, B b, C c) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            Objects.requireNonNull(c);
            return new NamedTriple<>(this, a, b, c);
        }
    }

    public static class NamedPair<A, B> extends Pair<A, B> implements NamedTuple<String, Object> {
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
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
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
    default Integer getInteger(String name) {
        return (Integer) get(name);
    }
    default Long getLong(String name) {
        return (Long) get(name);
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
}

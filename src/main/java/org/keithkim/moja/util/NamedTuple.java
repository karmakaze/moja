package org.keithkim.moja.util;

import org.keithkim.moja.util.Tuple.Pair;
import org.keithkim.moja.util.Tuple.Triple;

import java.util.*;

import static java.util.Arrays.asList;

public interface NamedTuple {
    String name();
    Map<String, Object> namedValues();

    static <S, A extends S, B extends S>
    Map<String, S> namedValues(NamedPair<A, B> namedPair) {
        Map<String, S> map = new LinkedHashMap<>(namedPair.width());
        for (int i = 0; i < namedPair.width(); i++) {
            map.put(namedPair.makeTuple.names.get(i), (S) namedPair.values[i]);
        }
        return map;
    }

    static <S, A extends S, B extends S, C extends S>
    Map<String, S> namedValues(NamedTriple<A, B, C> namedTriple) {
        Map<String, S> map = new LinkedHashMap<>(namedTriple.width());
        for (int i = 0; i < namedTriple.width(); i++) {
            map.put(namedTriple.makeTuple.names.get(i), (S) namedTriple.values[i]);
        }
        return map;
    }

    class MakeNamedTuple {
        final String name;
        final List<String> names;

        static Map<String, Object> namedObjectValues(List<String> names, Object[] values) {
            Map<String, Object> map = new LinkedHashMap<>(values.length);
            for (int i = 0; i < values.length; i++) {
                map.put(names.get(i), values[i]);
            }
            return map;
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

        MakeNamedTuple(String name, List<String> names) {
            this.name = name;
            this.names = names;
        }
    }

    class MakeNamedPair<A, B> extends MakeNamedTuple {
        MakeNamedPair(String name, String nameA, String nameB) {
            super(name, asList(nameA, nameB));
        }

        NamedPair<A, B> of(A a, B b) {
            Objects.requireNonNull(a);
            Objects.requireNonNull(b);
            return new NamedPair<>(this, a, b);
        }
    }

    class MakeNamedTriple<A, B, C> extends MakeNamedTuple {
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

    class NamedPair<A, B> extends Pair<A, B> implements NamedTuple {
        private final MakeNamedPair<A, B> makeTuple;

        NamedPair(MakeNamedPair<A, B> makeTuple, A a, B b) {
            super(makeTuple.name, a, b);
            this.makeTuple = makeTuple;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Map<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }

        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }

    class NamedTriple<A, B, C> extends Triple<A, B, C> implements NamedTuple {
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
        public Map<String, Object> namedValues() {
            return MakeNamedTuple.namedObjectValues(makeTuple.names, values);
        }

        @Override
        public String toString() {
            return MakeNamedTuple.toString(this);
        }
    }
}

package org.keithkim.moja.util;

public class Types {
    public static Unit unit() {
        return Unit.UNIT;
    }

    public static <T> T bottom() {
        return null;
    }

    public static Class<Object> topClass() {
        return Object.class;
    }

    public static Class<Unit> unitClass() {
        return Unit.class;
    }

    public static Class<Void> bottomClass() {
        return Void.class;
    }

    public static final class Unit {
        public static final Unit UNIT = new Unit();

        private Unit() {}

        @Override
        public String toString() {
            return "Unit";
        }

        @Override
        public int hashCode() {
            return "Unit".hashCode() * 3137;
        }

        @Override
        public boolean equals(Object o) {
            return o == UNIT;
        }
    }
}

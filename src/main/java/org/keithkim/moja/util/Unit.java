package org.keithkim.moja.util;

public final class Unit {
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

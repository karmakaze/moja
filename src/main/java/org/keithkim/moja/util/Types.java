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
}

package org.keithkim.moja.util;

public class ValueSet {
    public static <V1, V2> ValueSet2<V1, V2> of(V1 value1, V2 value2) {
        return new ValueSet2<>(value1, value2);
    }

    public static class ValueSet2<T1, T2> {
        T1 value1;
        T2 value2;

        ValueSet2(T1 v1, T2 v2) {
            value1 = v1;
            value2 = v2;
        }
    }
}

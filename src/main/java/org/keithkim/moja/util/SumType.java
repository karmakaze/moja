package org.keithkim.moja.util;

public class SumType<T> implements IndexValued<T> {
    final int length;
    final int index;
    final T value;

    SumType(int length, int index, T value) {
        this.length = length;
        this.index = index;
        this.value = value;
    }
    @Override
    public T value(int index) {
        return index == this.index ? value : null;
    }
    @Override
    public int length() {
        return length;
    }
    public int index() {
        return index;
    }

    public static class Either<T1, T2> extends SumType<Object> {
        public static <V1, V2> Either value1(V1 v1) {
            return new Either(0, v1);
        }
        public static <V1, V2> Either value2(V2 v2) {
            return new Either(1, v2);
        }
        Either(int type, Object value) {
            super(2, type, value);
        }
    }

    public static class OneOf3<T1, T2, T3> extends SumType<Object> {
        public static <V1, V2, V3> OneOf3 value1(V1 v1) {
            return new OneOf3(1, v1);
        }
        public static <V1, V2, V3> OneOf3 value2(V2 v2) {
            return new OneOf3(2, v2);
        }
        public static <V1, V2, V3> OneOf3 value3(V3 v3) {
            return new OneOf3(3, v3);
        }
        OneOf3(int type, Object value) {
            super(2, type, value);
        }
    }
}

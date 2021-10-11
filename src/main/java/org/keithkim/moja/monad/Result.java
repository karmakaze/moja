package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

public class Result implements Monad<Result, Object> {
    private static final Result monad = new Result();

    public static Result monad() {
        return monad;
    }

    public static <V> ResultValue<V> value(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new ResultValue<>(null, v);
    }

    public static <V> ResultValue<V> error(Object error) {
        return new ResultValue<V>(error, null);
    }

    private Result() {
    }

    @Override
    public <V> ResultValue<V> zero() {
        return error(null);
    }

    @Override
    public <V> ResultValue<V> unit(V v) {
        return value(v);
    }

//    @Override
//    public <V> MValue<Result, V> join(MValue<Result, MValue<Result, V>> mmv) {
//        ResultValue<MValue<Result, V>> rr = ResultValue.cast(mmv);
//        return rr.then(mv -> mv);
//    }
}

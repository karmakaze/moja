package org.keithkim.moja.monad;

import org.keithkim.moja.core.Monad;

public class ResultM<E> implements Monad<ResultM, Object> {
    private static final ResultM<?> monad = new ResultM<>();
    static final Result<?, ?> zero = new Result(null, null);

    public static <X> ResultM<X> monad() {
        return (ResultM<X>) monad;
    }

    private ResultM() {
    }

    @Override
    public <V> Result<E, V> zero() {
        return (Result<E, V>) ResultM.zero;
    }

    @Override
    public <V> Result<E, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return Result.value(v);
    }
}

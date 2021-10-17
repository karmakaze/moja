package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.core.MonadPlus;

public final class ResultM<E> implements Monad<ResultM, Object>, MonadPlus<ResultM, Object> {
    private static final ResultM<?> monad = new ResultM<>();
    static final Result<?, ?> mzero = new Result(null, null);

    public static <X> ResultM<X> monad() {
        return (ResultM<X>) monad;
    }

    private ResultM() {
    }

    @Override
    public <V> Result<E, V> mzero() {
        return (Result<E, V>) ResultM.mzero;
    }

    @Override
    public <V> Result<E, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return Result.value(v);
    }

    @Override
    public <V> Result<E, V> mplus(MValue<ResultM, V> a, MValue<ResultM, V> b) {
        return Result.narrow(Result.narrow(a).isError() ? b : a);
    }
}

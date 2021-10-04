package org.keithkim.moja.monad;

import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

import java.util.function.Function;

public class Result implements Monad<Result> {
    private static final Result monad = new Result();

    public static Result monad() {
        return monad;
    }

    public static <T, U> MFunction<T, Result, U> function(Function<T, MValue<Result, U>> f) {
        return new MFunction<T, Result, U>() {
            @Override
            public MValue<Result, U> apply(T t) {
                return f.apply(t);
            }
            @Override
            public MValue<Result, U> zero() {
                return Result.monad.zero();
            }
            @Override
            public MValue<Result, U> unit(U u) {
                return Result.monad.unit(u);
            }
            @Override
            public Monad<Result> monad() {
                return Result.monad;
            }
        };
    }

    private Result() {
    }

    @Override
    public <V> ResultValue<V> zero() {
        return ResultValue.error(null);
    }

    @Override
    public <V> ResultValue<V> unit(V v) {
        return ResultValue.value(v);
    }

    @Override
    public <V> MValue<Result, V> join(MValue<Result, V> mv1, MValue<Result, V> mv2) {
        throw new UnsupportedOperationException("Not implemented " + this.getClass().getCanonicalName() + ".join()");
//        ResultValue<V> r1 = ResultValue.cast(mv1);
//        ResultValue<V> r2 = ResultValue.cast(mv2);
//        return rt.then() ? this : otherResult;
//
//        if (!mv1.isZero()) {
//            return (ResultValue<V>) mv1;
//        } else {
//            return (ResultValue<V>) mv2;
//        }
    }
}

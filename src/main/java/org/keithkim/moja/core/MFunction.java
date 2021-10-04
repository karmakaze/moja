package org.keithkim.moja.core;

import java.util.function.Function;

public interface MFunction<T, M extends Monad, U> extends Function<T, MValue<M, U>> {
    default MValue<M, U> zero() {
        return monad().zero();
    }
    default MValue<M, U> unit(U u) {
        return monad().unit(u);
    }
    default MValue<M, U> join(MValue<M, U> mu1, MValue<M, U> mu2) {
        return mu1.monad().join(mu1, mu2);
    }
    default Monad<M> monad() {
        throw new UnsupportedOperationException("Not implemented " + this.getClass().getCanonicalName() + ".monad()");
    }
}

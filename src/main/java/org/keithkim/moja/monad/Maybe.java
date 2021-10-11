package org.keithkim.moja.monad;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;

public class Maybe implements Monad<Maybe, Object> {
    private static final Maybe monad = new Maybe();

    public static Maybe monad() {
        return monad;
    }

    private Maybe() {
    }

    @Override
    public <V> MValue<Maybe, V> zero() {
        return new MaybeValue<>();
    }

    @Override
    public <V> MValue<Maybe, V> unit(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return new MaybeValue<>(v);
    }

//    @Override
//    public <V> MaybeValue<V> join(MValue<Maybe, MValue<Maybe, V>> mmv) {
//        MaybeValue<MValue<Maybe, V>> mm = MaybeValue.narrow(mmv);
//        return MaybeValue.narrow(mm.then(mv -> mv));
//    }
}

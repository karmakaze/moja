package org.keithkim.moja.math;

import org.keithkim.moja.core.MFunction;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.MaybeValue;
import org.keithkim.moja.monad.Multi;
import org.keithkim.moja.monad.MultiValue;

public class Functions {
    public static MFunction<Integer, Maybe, Integer> POSITIVE_ROOT = Maybe.function((Integer i) -> {
        if (i == null || i < 0) {
            return Maybe.monad().zero();
        }
        int pos_root = (int) Math.floor(Math.sqrt(i));
        if (i != pos_root * pos_root) {
            return Maybe.monad().zero();
        }
        return Maybe.monad().unit(pos_root);
    });

    public static MFunction<Integer, Multi, Integer> INTEGER_ROOT = Multi.function((Integer i) -> {
        if (i == null || i < 0) {
            return Multi.monad().zero();
        }
        int int_root = (int) Math.floor(Math.sqrt(i));
        if (i != int_root * int_root) {
            return Multi.monad().zero();
        }
        return int_root == 0 ? Multi.monad().unit(int_root) : new MultiValue(-int_root, int_root);
    });
}

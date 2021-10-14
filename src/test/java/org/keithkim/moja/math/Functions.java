package org.keithkim.moja.math;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.monad.MaybeMonad;
import org.keithkim.moja.monad.MultiMonad;

import java.util.function.Function;

public class Functions {
    public static Function<Integer, MValue<MaybeMonad, Integer>> POSITIVE_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return MaybeMonad.monad().zero();
        }
        int pos_root = (int) Math.floor(Math.sqrt(i));
        if (i != pos_root * pos_root) {
            return MaybeMonad.monad().zero();
        }
        return MaybeMonad.monad().unit(pos_root);
    };

    public static Function<Integer, MValue<MultiMonad, Integer>> INTEGER_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return MultiMonad.monad().zero();
        }
        int int_root = (int) Math.floor(Math.sqrt(i));
        if (i != int_root * int_root) {
            return MultiMonad.monad().zero();
        }
        return int_root == 0 ? MultiMonad.monad().unit(int_root) : MultiMonad.of(-int_root, int_root);
    };
}

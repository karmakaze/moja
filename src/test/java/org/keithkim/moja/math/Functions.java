package org.keithkim.moja.math;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Multi;

import java.util.function.Function;

public class Functions {
    public static Function<Integer, MValue<Maybe, Integer>> POSITIVE_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return Maybe.monad().zero();
        }
        int pos_root = (int) Math.floor(Math.sqrt(i));
        if (i != pos_root * pos_root) {
            return Maybe.monad().zero();
        }
        return Maybe.monad().unit(pos_root);
    };

    public static Function<Integer, MValue<Multi, Integer>> INTEGER_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return Multi.monad().zero();
        }
        int int_root = (int) Math.floor(Math.sqrt(i));
        if (i != int_root * int_root) {
            return Multi.monad().zero();
        }
        return int_root == 0 ? Multi.monad().unit(int_root) : Multi.of(-int_root, int_root);
    };
}

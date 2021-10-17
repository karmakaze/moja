package org.keithkim.moja.math;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.monad.MaybeM;
import org.keithkim.moja.monad.Multi;
import org.keithkim.moja.monad.MultiM;

import java.util.function.Function;

public class Functions {
    public static Function<Integer, MValue<MaybeM, Integer>> POSITIVE_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return MaybeM.monad().mzero();
        }
        int pos_root = (int) Math.floor(Math.sqrt(i));
        if (i != pos_root * pos_root) {
            return MaybeM.monad().mzero();
        }
        return MaybeM.monad().unit(pos_root);
    };

    public static Function<Integer, MValue<MultiM, Integer>> INTEGER_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return MultiM.monad().mzero();
        }
        int int_root = (int) Math.floor(Math.sqrt(i));
        if (i != int_root * int_root) {
            return MultiM.monad().mzero();
        }
        return int_root == 0 ? Multi.of(int_root) : Multi.of(-int_root, int_root);
    };
}

package org.keithkim.moja.math;

import org.keithkim.moja.monad.Maybe;
import org.keithkim.moja.monad.Multi;

public class Functions {
    public static Maybe<Integer> positiveRoot(Integer i) {
        if (i < 0) {
            return Maybe.none();
        }
        int pos_root = (int) Math.floor(Math.sqrt(i));
        if (i != pos_root * pos_root) {
            return Maybe.none();
        }
        return Maybe.some(pos_root);
    };

    public static Multi<Integer> integerRoot(Integer i) {
        if (i < 0) {
            return Multi.of();
        }
        int int_root = (int) Math.floor(Math.sqrt(i));
        if (i != int_root * int_root) {
            return Multi.of();
        }
        return int_root == 0 ? Multi.of(int_root) : Multi.of(-int_root, int_root);
    }
}

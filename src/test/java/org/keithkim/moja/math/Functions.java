package org.keithkim.moja.math;

import org.keithkim.moja.core.MValue;
import org.keithkim.moja.monad.OptionM;
import org.keithkim.moja.monad.Multi;
import org.keithkim.moja.monad.MultiM;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Functions {
    public static Function<Integer, MValue<OptionM, Integer>> POSITIVE_ROOT = (Integer i) -> {
        if (i == null || i < 0) {
            return OptionM.monad().mzero();
        }
        int pos_root = (int) Math.floor(Math.sqrt(i));
        if (i != pos_root * pos_root) {
            return OptionM.monad().mzero();
        }
        return OptionM.monad().unit(pos_root);
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

    public static List<Integer> primesUpTo(int n) {
        boolean nonPrime[] = new boolean[n];

        for (int p = 2; p*p <= n; p++)
        {
            if (!nonPrime[p-1]) {
                for(int i = p*p; i <= n; i += p)
                    nonPrime[i-1] = true;
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!nonPrime[i-1]) {
                primes.add(i);
            }
        }
        return primes;
    }
}

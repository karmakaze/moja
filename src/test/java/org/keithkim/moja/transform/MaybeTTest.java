package org.keithkim.moja.transform;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.core.Monad;
import org.keithkim.moja.monad.MaybeMonad;
import org.keithkim.moja.monad.MultiMonad;

import static org.junit.jupiter.api.Assertions.*;

class MaybeTTest {
    @Test
    void multiMaybe_zero() {
        MultiMonad multi = MultiMonad.monad();
        Monad<Monad<MultiMonad, MaybeMonad>, Object> m = MaybeT.monad(multi);

        MValue<Monad<MultiMonad, MaybeMonad>, Integer> zero = m.zero();

        assertEquals("Multi()", zero.toString());
    }

    @Test
    void multiMaybe_unit() {
        Monad<MultiMonad, Object> multi = MultiMonad.monad();
        Monad<Monad<MultiMonad, MaybeMonad>, Object> m = MaybeT.monad(multi);

        MValue<Monad<MultiMonad, MaybeMonad>, Integer> unit = m.unit(3);
        assertEquals("Multi(Maybe(3))", unit.toString());

        MValue<Monad<MultiMonad, MaybeMonad>, Integer> x = unit.then((Integer t) -> {
            assertEquals(3, t);
            MValue<Monad<MultiMonad, MaybeMonad>, Integer> mmi = m.unit(t * 2);
            assertEquals("Multi(Maybe(6))", mmi.toString());
            return mmi;
        });
        assertEquals("Multi(Maybe(6))", x.toString());
    }
}

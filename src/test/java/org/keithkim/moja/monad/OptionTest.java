package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class OptionTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
        String a = "a string";
        Function<String, MValue<OptionM, Integer>> f = (s) -> OptionM.monad().unit(s.length());

        MValue<OptionM, String> ma = OptionM.monad().unit(a);
        MValue<OptionM, Integer> left = ma.then(f);
        MValue<OptionM, Integer> right = f.apply(a);

        assertEquals(left, right);
        assertEquals("Option(8)", left.toString());
        assertEquals("Option(8)", right.toString());
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
        String a = "a string";
        MValue<OptionM, String> ma = OptionM.monad().unit(a);
        Function<String, MValue<OptionM, String>> f = (s) -> OptionM.monad().unit(s);
        MValue<OptionM, String> left = ma.then(f);
        MValue<OptionM, String> right = ma;

        assertEquals(left, right);
        assertEquals("Option(a string)", left.toString());
        assertEquals("Option(a string)", right.toString());
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
        String[] months = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        String a = "test";
        MValue<OptionM, String> ma = OptionM.monad().unit(a);
        Function<String, MValue<OptionM, Integer>> f = (s) -> OptionM.monad().unit(s.length());
        Function<Integer, MValue<OptionM, String>> g = (i) -> OptionM.monad().unit(months[i]);
        MValue<OptionM, String> left = ma.then(f).then(g);
        MValue<OptionM, String> right = ma.then((x) -> f.apply(x).then(g));

        assertEquals(left, right);
        assertEquals("Option(May)", left.toString());
        assertEquals("Option(May)", right.toString());
    }

    @Test
    void new_canMakeZero() {
        Option<String> mzero = Option.narrow(OptionM.monad().mzero());

        assertTrue(mzero.isZero());
        assertEquals("Option.mzero", mzero.toString());

        assertTrue(mzero.toOptional().isEmpty());
    }

    @Test
    void new_canMakeUnit() {
        Option<String> unit = Option.narrow(OptionM.monad().unit("unit"));
        assertEquals("Option(unit)", unit.toString());

        assertFalse(unit.toOptional().isEmpty());
        assertEquals("unit", unit.toOptional().get());
    }

    @Test
    void mzeroThen_givesZero() {
        Option<Integer> input = Option.ofNullable(null);
        AtomicInteger invocationCount = new AtomicInteger();
        var stringer = Option.f((Integer t) -> {
            invocationCount.incrementAndGet();
            return OptionM.monad().unit(t.toString());
        });

        Option<String> output = Option.narrow(input.then(stringer));

        assertEquals("Option.mzero", output.toString());
        assertEquals(0, invocationCount.get());
    }

    @Test
    void thenNonEmpty_givesFunctionValue() {
        Option<Integer> input = Option.of(5);
        AtomicInteger invocationCount = new AtomicInteger();
        var stringer = Option.f((Integer t) -> {
            invocationCount.incrementAndGet();
            return Option.of(t.toString());
        });

        Option<String> output = Option.narrow(input.then(stringer));

        assertEquals(1, invocationCount.get());
        assertEquals("Option(5)", output.toString());
    }
}

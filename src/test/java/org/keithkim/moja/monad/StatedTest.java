package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;
import org.keithkim.moja.core.MValue;
import org.keithkim.moja.util.Pair;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class StatedTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
//        String a = "a string";
//        Function<String, MValue<StateM, Integer>> f = (s) -> StateM.monad().unit(s.length());
//
//        Stated<String> ma = State.narrow(StateM.monad().unit(a));
//        Stated<Integer> left = ma.then(f);
//        Stated<Integer> right = State.narrow(f.apply(a));
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals(8, left.getNow(null));
//        assertEquals(8, right.getNow(null));
    }

    @Test
    // Right identity: m >>= return ≡ m
    void rightIdentityLaw() {
//        String a = "a string";
//        Stated<String> ma = State.narrow(StateM.monad().unit(a));
//        Function<String, MValue<StateM, String>> f = (s) -> StateM.monad().unit(s);
//        Stated<String> left = ma.then(f);
//        Stated<String> right = ma;
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals("a string", left.getNow(null));
//        assertEquals("a string", right.getNow(null));
    }

    @Test
    // Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)
    void associativityLaw() {
//        String[] months = new String[] {
//            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
//            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
//        String a = "test";
//        MValue<StateM, String> ma = StateM.monad().unit(a);
//        Function<String, MValue<StateM, Integer>> f = (s) -> StateM.monad().unit(s.length());
//        Function<Integer, MValue<StateM, String>> g = (i) -> StateM.monad().unit(months[i]);
//        Stated<String> left = State.narrow(ma.then(f).then(g));
//        Stated<String> right = State.narrow(ma.then((x) -> f.apply(x).then(g)));
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals("May", left.getNow(null));
//        assertEquals("May", right.getNow(null));
    }

    @Test
    void new_canMakeUnit() {
        MValue<StatedM, String> unit = StatedM.monad().unit("unit");
        assertEquals("State@", unit.toString().substring(0, 6));
    }

    @Test
    void stateThen_givesNextState() {
        AtomicInteger invocationCount1 = new AtomicInteger();

        Stated<String, Integer> input = new Stated((s) -> {
            invocationCount1.incrementAndGet();
            return Pair.of(s + " + First", 0);
        });
        AtomicInteger invocationCount2 = new AtomicInteger();

        Function<Integer, MValue<StatedM, String>> stringer = (Integer i) ->
            new Stated<>((String s) -> {
                invocationCount2.incrementAndGet();
                return Pair.of(s + " + Second", Integer.toString(i));
            });

        Stated<String, String> output = Stated.narrow(input.then(stringer));

        assertEquals(0, invocationCount1.get());
        assertEquals(0, invocationCount2.get());

        Pair<String, String> stateValue = output.inject("initial");
        assertEquals("initial + First + Second", stateValue.first());
        assertEquals("0", stateValue.second());
        assertEquals(1, invocationCount1.get());
        assertEquals(1, invocationCount2.get());
    }
}

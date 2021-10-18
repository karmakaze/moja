package org.keithkim.moja.monad;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerTest {
    @Test
    // Left identity: return a >>= f ≡ f a
    void leftIdentityLaw() {
//        String a = "a string";
//        Function<String, MValue<LoggerM, Integer>> f = (s) -> LoggerM.monad().unit(s.length());
//
//        Logger<String> ma = Logger.narrow(LoggerM.monad().unit(a));
//        Logger<Integer> left = ma.then(f);
//        Logger<Integer> right = Logger.narrow(f.apply(a));
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
//        Logger<String> ma = Logger.narrow(LoggerM.monad().unit(a));
//        Function<String, MValue<LoggerM, String>> f = (s) -> LoggerM.monad().unit(s);
//        Logger<String> left = ma.then(f);
//        Logger<String> right = ma;
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
//        MValue<LoggerM, String> ma = LoggerM.monad().unit(a);
//        Function<String, MValue<LoggerM, Integer>> f = (s) -> LoggerM.monad().unit(s.length());
//        Function<Integer, MValue<LoggerM, String>> g = (i) -> LoggerM.monad().unit(months[i]);
//        Logger<String> left = Logger.narrow(ma.then(f).then(g));
//        Logger<String> right = Logger.narrow(ma.then((x) -> f.apply(x).then(g)));
//
//        assertEquals(left.join(), right.join());
//        assertTrue(left.isDone());
//        assertTrue(right.isDone());
//        assertEquals("May", left.getNow(null));
//        assertEquals("May", right.getNow(null));
    }

    @Test
    void new_canMakeUnit() {
//        Logger<Unit, Integer> unit = Logger.narrow(LoggerM.monad().unit(42));
//        assertEquals("Logger@", unit.toString().substring(0, 7));
//
//        Logger<Unit, String> output = Logger.narrow(unit.then((Integer i) -> {
//            assertEquals(42, i);
//            return LoggerM.monad().unit(i.toString());
//        }));
//
//        String out = output.inject(Unit.UNIT);
//        assertEquals("42", out);
    }

    @Test
    void demo() {
        AtomicInteger doublerCount = new AtomicInteger();
        Function<Integer, Logger<BiConsumer<String, Object>, Integer>> doubler = (i) -> {
            doublerCount.incrementAndGet();
            return Logger.of((w) -> i * 2);
        };

        AtomicInteger logCount = new AtomicInteger();
        List<String> log = new ArrayList<>();
        BiConsumer<String, Object> consumer = (message, value) -> {
            logCount.incrementAndGet();
            log.add(message + value);
        };

        Integer startValue = 2;
        Logger<BiConsumer<String, Object>, Integer> pipeline = Logger.of((w) -> startValue)
            .then(doubler).log("applied once: ")
            .then(doubler)
            .then(doubler).log("applied thrice: ")
            .then(doubler)
            .then(doubler).log("applied fifth and last time: ");

        assertEquals(0, doublerCount.get());
        assertEquals(0, logCount.get());

        Integer out = pipeline.inject(consumer);

        assertEquals(64, out);
        assertEquals(5, doublerCount.get());
        assertEquals(3, logCount.get());
        assertEquals(List.of("applied once: 4", "applied thrice: 16", "applied fifth and last time: 64"), log);
    }

    @Test
    void stateThen_givesNextLogger() {
//        AtomicInteger invocationCount1 = new AtomicInteger();
//
//        Logger<String, Integer> input = new Logger((s) -> {
//            invocationCount1.incrementAndGet();
//            return Pair.of(s + " + First", 0);
//        });
//        AtomicInteger invocationCount2 = new AtomicInteger();
//
//        Function<Integer, MValue<LoggerM, String>> stringer = (Integer i) ->
//            new Logger<>((String s) -> {
//                invocationCount2.incrementAndGet();
//                return Pair.of(s + " + Second", Integer.toString(i));
//            });
//
//        Logger<String, String> output = input.then(stringer);
//
//        assertEquals(0, invocationCount1.get());
//        assertEquals(0, invocationCount2.get());
//
//        Pair<String, String> stateValue = output.inject("initial");
//        assertEquals("initial + First + Second", stateValue.first());
//        assertEquals("0", stateValue.second());
//        assertEquals(1, invocationCount1.get());
//        assertEquals(1, invocationCount2.get());
    }
}

package org.keithkim.moja.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;

public class FixedKeysArrayMapTest {
    FixedKeysArrayMap<String, Integer> fkam0;
    FixedKeysArrayMap<String, Integer> fkam1;
    FixedKeysArrayMap<String, Integer> fkamA;

    FixedKeysArrayMap<String, Double> fkam;
    String[] names;

    @BeforeEach
    void setup() {
        fkam0 = new FixedKeysArrayMap<>(new String[] {});
        fkam1 = new FixedKeysArrayMap<>(new String[] {"1"});
        fkam1.put("1", 1);
        fkamA = new FixedKeysArrayMap<>(new String[] {"A"});
        fkamA.put("A", 1);
    }

    void setupFkamNames(int size) {
        names = new String[size];
        for (int i = 0; i < size; i++) {
            names[i] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz" + i;
        }
        fkam = new FixedKeysArrayMap<>(names);
        for (int i = 0; i < size; i++) {
            fkam.put(names[i], (double) i);
        }
    }

    @Test
    void size_matchesIntialNamesLength() {
        assertEquals(0, fkam0.size());
        assertEquals(1, fkam1.size());
        setupFkamNames(2);
        assertEquals(2, fkam.size());
    }

    @Test
    void isEmpty_reflectsSize() {
        assertEquals(0, fkam0.size());
        assertTrue(fkam0.isEmpty());

        assertEquals(1, fkam1.size());
        assertFalse(fkam1.isEmpty());
    }

    @Test
    void hashCode_isStableAcrossRuns() {
        assertEquals(32, fkam0.hashCode());
        assertEquals(2512, fkam1.hashCode());
        assertEquals(3008, fkamA.hashCode());
        setupFkamNames(26);
        assertEquals(-1597323949, fkam.hashCode());
    }

    @Test
    void hashCode_dependsOnKeysAndValues() {
        assertEquals(2512, fkam1.hashCode());
        fkam1.put("1", 2);
        assertNotEquals(2512, fkam1.hashCode());

        fkamA.put("A", 2);
        assertNotEquals(fkam1.hashCode(), fkamA.hashCode());
    }

    @Test
    void toString_isEasyToRead() {
        assertEquals("{ }", fkam0.toString());
        assertEquals("{ 1:1 }", fkam1.toString());
        assertEquals("{ A:1 }", fkamA.toString());
        setupFkamNames(2);
        assertEquals("{ 0123456789abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0:0.0, "+
                "0123456789abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz1:1.0 }", fkam.toString());
    }

    @Test
    void get_whenFoundIsFast() {
        setupFkamNames(1_000);
        Instant start = Instant.now();
        for (int j = 0; j < 1_000; j++) {
            for (int i = 0; i < 1_000; i++) {
                fkam.get(names[i]);
            }
        }
        Instant finish = Instant.now();
        Duration duration = Duration.between(start, finish);
        assertThat(duration.toMillis(), lessThanOrEqualTo(500L));
    }

    @Test
    void get_whenNotFoundIsFast() {
        setupFkamNames(1_000);
        Instant start = Instant.now();
        for (int j = 0; j < 1_000; j++) {
            for (int i = 0; i < 1_000; i++) {
                fkam.get("");
            }
        }
        Instant finish = Instant.now();
        Duration duration = Duration.between(start, finish);
        assertThat(duration.toMillis(), lessThanOrEqualTo(500L));
    }

    @Test
    void put_whenAllowedIsFast() {
        setupFkamNames(1_000);
        Double zero = 0.0;

        Instant start = Instant.now();
        for (int j = 0; j < 1_000; j++) {
            for (int i = 0; i < 1_000; i++) {
                fkam.put(names[i], zero);
            }
        }
        Instant finish = Instant.now();
        Duration duration = Duration.between(start, finish);
        assertThat(duration.toMillis(), lessThanOrEqualTo(500L));
    }

    @Test
    void put_whenNotAllowedIsKindOfFast() {
        setupFkamNames(1_000);
        Double zero = 0.0;

        Instant start = Instant.now();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 1_000; i++) {
                try {
                    fkam.put("", zero);
                } catch (IllegalArgumentException e) {
                }
            }
        }
        Instant finish = Instant.now();
        Duration duration = Duration.between(start, finish);
        assertThat(duration.toMillis(), lessThanOrEqualTo(100L));
    }
}

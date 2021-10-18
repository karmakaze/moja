package org.keithkim.moja.util;

import java.util.Collections;
import java.util.HashMap;

public class ImmutableMap {
    public static <K, V> java.util.Map<K, V> of(K k1, V v1) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3,
                                                K k4, V v4, K k5, V v5, K k6, V v6) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3,
                                                K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                                K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> java.util.Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4,
                                                K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        HashMap<K, V> map = new HashMap<K, V>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        return Collections.unmodifiableMap(map);
    }
}

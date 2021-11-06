package org.keithkim.moja.util;

import java.util.*;

public class FixedKeysArrayMap<K, V> extends AbstractMap<K, V> {
    private final Object[] keys;
    private final Object[] values;
    private final int[] hashes;

    public FixedKeysArrayMap(Object[] allowedKeys) {
        super();
        this.keys = allowedKeys;
        this.values = new Object[keys.length];
        this.hashes = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            hashes[i] = keys[i].hashCode();
        }
    }

    public FixedKeysArrayMap(List<K> allowedKeys) {
        this(allowedKeys.toArray(new Object[allowedKeys.size()]));
    }

    @Override
    public V get(Object key) {
        Objects.requireNonNull(key);
        int index = findKey(key);
        if (index < 0) {
            return null;
        }
        return (V) values[index];
    }

    @Override
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Object oldValue = null;
        int index = findKey(key);
        if (index < 0) {
            throw new IllegalArgumentException("Key is not allowed.");
        }
        oldValue = values[index];
        values[index] = value;
        return (V) oldValue;
    }

    @Override
    public boolean containsKey(Object key) {
        return findKey(key) >= 0;
    }

    @Override
    public boolean containsValue(Object value) {
        return findValue(value) >= 0;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public Set<K> keySet() {
        Set<K> ks = new AbstractSet<K>() {
            public Iterator<K> iterator() {
                return new Iterator<K>() {
                    private Iterator<Entry<K,V>> i = entrySet().iterator();

                    public boolean hasNext() {
                        return i.hasNext();
                    }

                    public K next() {
                        return i.next().getKey();
                    }

                    public void remove() {
                        i.remove();
                    }
                };
            }

            public int size() {
                return FixedKeysArrayMap.this.size();
            }

            public boolean isEmpty() {
                return FixedKeysArrayMap.this.isEmpty();
            }

            public void clear() {
                FixedKeysArrayMap.this.clear();
            }

            public boolean contains(Object k) {
                return FixedKeysArrayMap.this.containsKey(k);
            }
        };
        return ks;
    }

    @Override
    public List<V> values() {
        return (List<V>) Arrays.asList(values);
    }

    @Override
    public int size() {
        return keys.length;
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        int h = Arrays.hashCode(keys);
        h = h * 31 + Arrays.hashCode(values);
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FixedKeysArrayMap) {
            FixedKeysArrayMap that = (FixedKeysArrayMap) o;
            return this.keys.equals(that.keys) && this.values.equals(that.values);
        }
        return false;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{ }";
        }
        StringBuilder buffer = new StringBuilder("{ ");
        for (Map.Entry<K, V> me : entrySet()) {
            K key = me.getKey();
            V value = me.getValue();
            buffer.append(key);
            buffer.append(':');
            buffer.append(value);
            buffer.append(", ");
        }
        buffer.setLength(buffer.length() - 2);
        buffer.append(" }");
        return buffer.toString();
    }

    private int findKey(Object key) {
        int hash = key.hashCode();
        int len = keys.length;
        for (int i = 0; i < len; i++) {
            if (hash == hashes[i] && key.equals(keys[i])) {
                return i;
            }
        }
        return -1;
    }

    private int findValue(Object value) {
        int len = values.length;
        for (int i = 0; i < len; i++) {
            if (Objects.equals(value, values[i])) {
                return i;
            }
        }
        return -1;
    }

    class KeySet extends AbstractSet<K> {
        KeySet() {
        }

        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    while (index < keys.length) {
                        if (keys[index] != null) {
                            return true;
                        }
                        index++;
                    }
                    return false;
                }

                @Override
                public K next() {
                    if (hasNext()) {
                        K key = (K) keys[index];
                        index++;
                        return key;
                    }
                    throw new NoSuchElementException();
                };
            };
        }

        @Override
        public int size() {
            return FixedKeysArrayMap.this.size();
        }

        @Override
        public boolean isEmpty() {
            return FixedKeysArrayMap.this.isEmpty();
        }

        @Override
        public void clear() {
            FixedKeysArrayMap.this.clear();
        }

        @Override
        public boolean contains(Object k) {
            return FixedKeysArrayMap.this.containsKey(k);
        }
    }

    class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new Iterator<Map.Entry<K, V>>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    while (index < keys.length) {
                        if (keys[index] != null) {
                            return true;
                        }
                        index++;
                    }
                    return false;
                }

                @Override
                public Map.Entry<K, V> next() {
                    if (hasNext()) {
                        SimpleEntry<K, V> entry = new SimpleEntry<>((K) keys[index], (V) values[index]);
                        index++;
                        return entry;
                    }
                    throw new NoSuchElementException();
                };
            };
        }

        @Override
        public int size() {
            return FixedKeysArrayMap.this.size();
        }

        @Override
        public boolean isEmpty() {
            return FixedKeysArrayMap.this.isEmpty();
        }

        @Override
        public void clear() {
            FixedKeysArrayMap.this.clear();
        }

        @Override
        public boolean contains(Object e) {
            if (e instanceof Map.Entry) {
                Map.Entry<K, V> me = (Map.Entry<K, V>) e;
                int i = FixedKeysArrayMap.this.findKey(me.getKey());
                if (i >= 0) {
                    return Objects.equals(((Entry<?, ?>) e).getValue(), FixedKeysArrayMap.this.values[i]);
                }
            }
            return false;
        }
    }
}

package org.keithkim.moja.core;

public interface Boxed<T> {
    Boolean isEmpty();
    T getElse(T zero);
}

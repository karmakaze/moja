package org.keithkim.moja.util;

public interface IndexValued<T> {
    int length();
    T value(int index);
}

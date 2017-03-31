package com.will.utils;

/**
 * Created by DJ on 2016/1/26.
 */
public class KVEntry<K, V> {
    private K k;
    private V v;

    public KVEntry(K k, V v) {
        setKey(k);
        setValue(v);
    }

    public K getKey() {
        return k;
    }

    public void setKey(K k) {
        this.k = k;
    }

    public V getValue() {
        return v;
    }

    public void setValue(V v) {
        this.v = v;
    }
}

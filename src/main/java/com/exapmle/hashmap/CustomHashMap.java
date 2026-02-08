package main.java.com.exapmle.hashmap;

import lombok.ToString;

import java.util.Objects;

@ToString
public class CustomHashMap<K, V> {

    private static class Entry<K,V> {
        final K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{key=" + key +
                    ", value=" + value + "}";
        }
    }

    private Entry<K, V>[] buckets;
    private int size;

    public CustomHashMap() {
        int INITIAL_CAPACITY = 16;
        buckets = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    private int index(Object key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        hash = hash ^ (hash >>> 16);
        return hash & (buckets.length - 1);
    }

    public void put(K key, V value) {
        int index = index(key);

        for (Entry<K, V> e = buckets[index]; e != null; e = e.next) {
            if (Objects.equals(key, e.key)) {
                e.value = value;  // Обновление значения
                return;
            }
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;

        if (size > buckets.length * 0.75) {
            resize();
        }
    }

    public V get(K key) {
        int index = index(key);
        for (Entry<K, V> e = buckets[index]; e != null; e = e.next) {
            if (Objects.equals(key, e.key)) {
                return e.value;
            }
        }
        return null;
    }

    public boolean remove(K key) {
        int index = index(key);

        Entry<K, V> prev = null;
        for (Entry<K, V> e = buckets[index]; e != null; prev = e, e = e.next) {
            if (Objects.equals(key, e.key)) {
                if (prev == null) {
                    buckets[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = new Entry[buckets.length * 2];
        size = 0;

        for (Entry<K, V> head : oldBuckets) {
            while (head != null) {
                int idx = index(head.key);  // Пересчитываем индекс
                Entry<K, V> temp = head;
                head = head.next;

                temp.next = buckets[idx];  // Вставляем в новую корзину
                buckets[idx] = temp;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}

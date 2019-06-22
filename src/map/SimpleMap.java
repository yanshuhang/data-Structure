package map;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 使用数组的简单map实现 键值对应，key不能为null
 *
 * @param <K>
 * @param <V>
 */
public class SimpleMap<K, V> {
    /**
     * 存储键值对的数组
     */
    private Node<K, V>[] data;
    private int size;
    private static final int DEFARLT_CAPACITY = 10;

    public SimpleMap() {
        data = (Node<K, V>[]) new Node[DEFARLT_CAPACITY];
    }

    public SimpleMap(int capacity) {
        data = (Node<K, V>[]) new Node[capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(K key, V value) {
        ensureCapacity(size + 1);
        if (indexOfKey(key) == -1) {
            data[size] = new Node<>(key, value);
            size++;
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity == data.length) {
            int newCapacity = minCapacity << 1;
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    public V get(K key) {
        int index = indexOfKey(key);
        if (index != -1) {
            return data[index].value;
        }
        return null;
    }

    public V remove(K key) {
        int index = indexOfKey(key);
        if (index != -1) {
            System.arraycopy(data, index + 1, data, index, size - index - 1);
            data[--size] = null;
        }
        return null;
    }

    /**
     * 查找key在数组中的索引
     *
     * @param key 键
     * @return 返回key的索引，未找到时返回 -1
     */
    private int indexOfKey(K key) {
        if (key == null) {
            throw new NullPointerException("key could not be null");
        }
        for (int i = 0; i < size; i++) {
            if (key.equals(data[i].key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "simpleMap {}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("simpleMap {");
        for (int i = 0; i < size; i++) {
            builder.append(data[i]);
            if (i != size - 1) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * key-value 的存储结构
     *
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V> {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}

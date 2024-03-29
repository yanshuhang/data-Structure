package list;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 数据结构之基于数组的顺序表
 * @param <E>
 * @author 严书航
 */
public class LinearList<E> implements Iterable<E> {
    /**
     * 存储数据的数组
     */
    private Object[] data;

    /**
     * 数组中存储的元素数量
     */
    private int size;

    /**
     * 数组默认的容量
     */
    private int defaultCapacity = 10;

    /**
     * 默认数组容量：10
     */
    public LinearList() {
        this.data = new Object[defaultCapacity];
    }

    /**
     * 自定义数组的容量
     *
     * @param initalSize 数组容量
     */
    public LinearList(int initalSize) {
        if (initalSize < 0) {
            throw new IllegalArgumentException("初始化大小不能小于0：initalSize " + initalSize);
        }
        this.data = new Object[initalSize];
    }

    /**
     * @return 数组中的元素个数
     */
    public int size() {
        return this.size;
    }

    /**
     * 返回数组是否为空
     *
     * @return 空时返回true
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param element 添加的元素
     * @return 成功返回true
     */
    public boolean add(E element) {
        ensureCapacity(size + 1);
        this.data[size] = element;
        size++;
        return true;
    }

    /**
     * @param index 元素的索引
     * @return 返回该索引存储的元素
     */
    public E get(int index) {
        indexCheck(index);
        return (E) this.data[index];
    }

    /**
     * @param index   索引
     * @param element 设置的新元素
     * @return 返回旧元素
     */
    public E set(int index, E element) {
        indexCheck(index);
        E oldVal = get(index);
        data[index] = element;
        return oldVal;
    }

    /**
     * 在index前插入元素
     *
     * @param index   索引
     * @param element 插入的元素
     * @return 成功返回true
     */
    public boolean insert(int index, E element) {
        indexCheck(index);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
        return true;
    }

    /**
     * 根据索引删除元素
     *
     * @param index 索引
     * @return 返回删除的元素
     */
    public E remove(int index) {
        indexCheck(index);
        E oldVal = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        // 将size处索引的元素设为null 方便垃圾回收
        data[--size] = null;
        return oldVal;
    }

    /**
     * 删除第一个匹配的元素
     *
     * @param element 要删除的元素
     * @return 成功返回true
     */
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(data[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "LinearList []";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("LinearList [");
        for (int i = 0; i < size; i++) {
            builder.append(data[i]);
            if (i < size - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * 索引的合理性检查
     *
     * @param index 索引
     */
    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("outofbound list.LinearList size " + size + " index " + index);
        }
    }

    /**
     * 数组满时容量翻倍
     *
     * @param minCapacity 数组的最新容量
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity == data.length) {
            int newCapacity = data.length << 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    /**
     * 迭代器
     */
    private class Itr implements Iterator<E> {
        private int current;
        private int lastReturn;

        @Override
        public boolean hasNext() {
            return current != size;
        }

        @Override
        public E next() {
            lastReturn = current;
            current++;
            return (E) data[lastReturn];
        }
    }

    /**
     *
     * @return 返回迭代器
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
}

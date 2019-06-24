package list;

import java.util.Iterator;

/**
 * 数据结构之单链表
 *
 * @author 严书航
 */
public class SingleLinkedList<E> implements Iterable<E> {
    /**
     * 指向第一个节点
     */
    private Node<E> first;
    /**
     * 链表中的元素数量
     */
    private int size;

    //默认构造器
    public SingleLinkedList() {
    }

    /**
     * @return 返回元素数量
     */
    public int size() {
        return size;
    }

    public boolean isempty() {
        return size == 0;
    }

    /**
     * 添加元素
     *
     * @param element
     * @return
     */
    public boolean add(E element) {
        // 新节点
        Node<E> newNode = new Node<>(element);
        if (size == 0) {
            first = newNode;
            size++;
            return true;
        }
        // 获得最后一个节点
        Node<E> lastNode = getNode(size - 1);
        // 新节点添加到最后一个节点
        lastNode.addNext(newNode);
        // 元素数量+1
        size++;
        return true;
    }

    public E get(int index) {
        indexCheck(index);
        return getNode(index).element;
    }

    public E getFirst() {
        return first.element;
    }

    public E removeFirst() {
        if (first == null) {
            return null;
        }
        E oldVal = first.element;
        first = first.next;
        size--;
        return oldVal;
    }

    public E getLast() {
        return getNode(size - 1).element;
    }

    public E set(int index, E element) {
        indexCheck(index);
        Node<E> node = getNode(index);
        E oldVal = node.element;
        node.element = element;
        return oldVal;
    }

    public boolean insert(int index, E element) {
        indexCheck(index);
        Node<E> node = new Node<>(element);
        if (index == 0) {
            node.next = first;
            first = node;
            size++;
            return true;
        }
        Node<E> preNode = getNode(index - 1);
        node.next = preNode.next;
        preNode.next = node;
        size++;
        return true;
    }

    /**
     * 返回第一个匹配到的节点索引
     *
     * @param element 要查找的元素
     * @return 查找到返回index值，为查找到返回-1
     */
    public int indexOf(E element) {
        int index = 0;
        if (element == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (null == node.element) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (element.equals(node.element)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    /**
     * 返回最后一个匹配到的节点索引
     *
     * @param element 匹配的元素
     * @return 未匹配到返回 -1
     */
    public int lastIndexOf(E element) {
        int index = 0;
        int lastIndex = -1;
        if (element != null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (element.equals(node.element)) {
                    lastIndex = index;
                }
                index++;
            }
            return lastIndex;
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.element == null) {
                    lastIndex = index;
                }
                index++;
            }
            return lastIndex;
        }
    }

    /**
     * 删除索引位置的元素
     *
     * @param index 索引
     * @return 返回被删除的元素
     */
    public E remove(int index) {
        indexCheck(index);
        if (index == 0) {
            return removeFirst();
        }
        Node<E> preNode = getNode(index - 1);
        Node<E> currNode = preNode.next;
        preNode.next = currNode.next;
        size--;
        return currNode.element;
    }

    /**
     * @param element 删除的元素
     * @return 没有匹配的元素返回false，删除成功返回true
     */
    public boolean remove(E element) {
        int index = indexOf(element);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "singleLinkedList []";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("singleLinkedlist [");
        for (Node<E> node = first; node != null; node = node.next) {
            stringBuilder.append(node.element);
            if (node.next != null) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * 遍历列表获得当前索引对应的元素
     *
     * @param index 索引
     * @return 索引对应的元素
     */
    private Node<E> getNode(int index) {
        indexCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * 检查索引的正确性
     *
     * @param index 索引
     */
    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("SingleLinkedList size " + size + " index " + index);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr(0);
    }

    /**
     * 链表中存储的节点
     *
     * @param <E>
     */
    private static class Node<E> {
        /**
         * 节点中存储的元素
         */
        E element;
        /**
         * 指向的下一个节点
         */
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }

        void addNext(Node<E> node) {
            next = node;
        }
    }

    private class Itr implements Iterator<E>{
        private Node<E> lastRuturn;
        private Node<E> next;
        private int nextIndex;

        public Itr(int index) {
            next = getNode(index);
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            lastRuturn = next;
            next = next.next;
            nextIndex++;
            return lastRuturn.element;
        }
    }
}

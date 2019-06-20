package list;

import com.sun.org.apache.xpath.internal.operations.Lt;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * 数据结构之双链表
 *
 * @author 严书航
 */
public class DoubleLinkedList<E> implements Iterable<E>{
    private int size;
    private Node<E> first;
    private Node<E> last;

    public DoubleLinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 元素添加到尾部
     *
     * @param element 元素
     * @return 成功返回true；
     */
    public boolean add(E element) {
        Node<E> node = new Node<>(last, element, null);
        if (last == null) {
            last = node;
            first = node;
        } else {
            last.next = node;
            last = node;
        }
        size++;
        return true;
    }

    // 元素添加到头部
    public boolean addFirst(E element) {
        Node<E> node = new Node<>(null, element, first);
        if (first == null) {
            first = node;
            last = node;
        } else {
            first.pre = node;
            first = node;
        }
        size++;
        return true;
    }

    // 元素添加到尾部
    public boolean addLast(E element) {
        return add(element);
    }

    public E get(int index) {
        indexCheck(index);
        Node<E> node = getNode(index);
        return node.element;
    }

    public E getFirst() {
        return first.element;
    }

    public E getLast() {
        return last.element;
    }

    /**
     * @param index   索引
     * @param element 新元素
     * @return 返回旧的元素
     */
    public E set(int index, E element) {
        indexCheck(index);
        Node<E> node = getNode(index);
        E oldVal = node.element;
        node.element = element;
        return oldVal;
    }

    public boolean insert(int index, E element) {
        indexCheck(index);
        if (index == 0) {
            addFirst(element);
            size++;
        } else {
            Node<E> nextNode = getNode(index);
            Node<E> preNode = nextNode.pre;
            Node<E> node = new Node<>(preNode, element, nextNode);
            preNode.next = node;
            nextNode.pre = node;
            size++;
        }
        return true;
    }

    public boolean insertBefore(int index, E element) {
        return insert(index, element);
    }

    public boolean insertAfter(int index, E element) {
        indexCheck(index);
        if (index == size - 1) {
            addLast(element);
            size++;
        } else {
            Node<E> preNode = getNode(index);
            Node<E> nextNode = preNode.next;
            Node<E> node = new Node<>(preNode, element, nextNode);
            preNode.next = node;
            nextNode.pre = node;
            size++;
        }
        return true;
    }

    public E remove(int index) {
        indexCheck(index);
        Node<E> node = getNode(index);
        return unLink(node);
    }

    public boolean remove(E element) {
        if (element == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.element == null) {
                    unLink(node);
                    return true;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (element.equals(node.element)) {
                    unLink(node);
                    return true;
                }
            }
        }
        return false;
    }

    // 删除节点
    private E unLink(Node<E> node) {
        E oldVal = node.element;
        Node<E> preNode = node.pre;
        Node<E> nextNode = node.next;
        if (preNode == null) {
            first = nextNode;
        } else {
            preNode.next = nextNode;
            node.pre = null;
        }
        if (nextNode == null) {
            last = preNode;
        } else {
            nextNode.pre = preNode;
            node.next = null;
        }

        node.element = null;
        size--;

        return oldVal;
    }

    public E removeFirst() {
        E oldVal = null;
        if (first != null) {
            oldVal = first.element;
            if (first == last) {
                first = null;
                last = null;
            } else {
                first.element = null;
                first.next.pre = null;
                first = first.next;
            }
            size--;
        }
        return oldVal;
    }

    public E removeLast() {
        E oldVal = null;
        if (last != null) {
            oldVal = last.element;
            if (first == last) {
                first = null;
                last = null;
            } else {
                last.element = null;
                last.pre.next = null;
                last = last.pre;
            }
            size--;
        }
        return oldVal;
    }

    public int indexOf(E element) {
        int index = -1;
        if (element == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                index++;
                if (node.element == null) {
                    return index;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                index++;
                if (element.equals(node.element)) {
                    return index;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(E element) {
        int index = -1;
        int lastIndex = -1;
        if (element == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                index++;
                if (node.element == null) {
                    lastIndex = index;
                }
            }
            return lastIndex;
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                index++;
                if (element.equals(node.element)) {
                    lastIndex = index;
                }
            }
            return lastIndex;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "doubleLinkedList []";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("doubleLinkedList [");
        for (Node<E> node = first; node != null; node = node.next) {
            stringBuilder.append(node.element);
            if (node.next != null) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private Node<E> lastReturn;
        private Node<E> next;
        private int nextIndex;

        public Itr() {
            next = first;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            lastReturn = next;
            next = next.next;
            nextIndex++;
            return lastReturn.element;
        }
    }

    private class ListItr implements ListIterator<E> {
        private Node<E> lastReturn;
        private Node<E> next;
        private int nextIndex;

        public ListItr(int nextIndex) {
            indexCheck(nextIndex);
            next = getNode(nextIndex);
            this.nextIndex = nextIndex;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            lastReturn = next;
            next = next.next;
            nextIndex++;
            return lastReturn.element;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            lastReturn = next = (next == null) ? last : next.pre;
            nextIndex--;
            return lastReturn.element;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            Node<E> next1 = lastReturn.next;
            unLink(lastReturn);
            if (lastReturn == next) {
                next = next1;
            } else {
                nextIndex--;
            }
            lastReturn = null;
        }

        @Override
        public void set(E element) {
            lastReturn.element = element;
        }

        @Override
        public void add(E element) {
            lastReturn = null;
            if (next == null) {
                add(element);
            } else {
                insert(nextIndex, element);
            }
        }
    }


    /**
     * 返回索引处的节点，判断是在前半段还是后半段从前还是从后开始遍历
     *
     * @param index 索引
     * @return 返回索引处的节点
     */
    private Node<E> getNode(int index) {
        if (index < size >> 1) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.pre;
            }
            return node;
        }
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("outofbound DoublelinkedList size " + size + " index " + index);
        }
    }

    /**
     * 双链表中存储的节点结构
     *
     * @param <E>
     */
    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> pre;

        Node(Node<E> pre, E element, Node<E> next) {
            this.element = element;
            this.pre = pre;
            this.next = next;
        }
    }
}

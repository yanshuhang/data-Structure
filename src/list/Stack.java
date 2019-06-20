package list;

/**
 * 栈
 * @author 严书航
 */
public class Stack<E> {
    private int size;
    private Node<E> first;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Stack() {
    }

    /**
     * @param element 压入栈顶的元素
     */
    public E push(E element) {
        Node<E> node = new Node<>(element, first);
        first = node;
        size++;
        return element;
    }

    /**
     * @return 删除并返回栈顶元素
     */
    public E pop() {
        E oldVal = first.element;
        first.element = null;
        first = first.next;
        size--;
        return oldVal;
    }

    /**
     * @return 返回栈顶元素 不删除
     */
    public E peek() {
        return first.element;
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}

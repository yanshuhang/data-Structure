package list;

public class Queue<E> {
    private int size;
    private Node<E> head;
    private Node<E> tail;

    public Queue() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(E element) {
        Node<E> node = new Node<>(element, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public E dequeue() {
        E element = head.element;
        head.element = null;
        head = head.next;
        size--;
        return element;
    }

    public E peek() {
        return head.element;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "queue []";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("queue [");
        for (Node<E> node = head; node != null; node = node.next) {
            stringBuilder.append(node.element);
            if (node.next != null) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static class Node<E>{
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public void addNext(Node<E> node) {
            next = node;
        }
    }
}

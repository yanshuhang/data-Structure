package list;

/**
 * set 主要实现存储不重复的元素，使用单链表结构
 * @author 严书航
 */
public class Set<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;

    public Set() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        first = last = null;
        size = 0;
    }

    public boolean add(E element) {
        Node<E> node = new Node<>(element, null);
        if (node(element) == null) {
            if (first == null) {
                first = last = node;
            } else {
                last.next = node;
                last = node;
            }
            size++;
            return true;
        }
        return false;
    }

    public boolean remove(E element) {
        Node<E> node = node(element);
        if (node != null) {
            if (node == first) {
                first = node.next;
                node.next = null;
                node.element = null;
                size--;
                return true;
            } else {
                Node<E> preNode = preNode(element);
                preNode.next = node.next;
                node.next = null;
                node.element = null;
                size--;
                return true;
            }
        }
        return false;
    }

    private Node<E> node(E element) {
        Node<E> node = first;
        while (node != null) {
            if (element.equals(node.element)) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "set []";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("set [");
        Node<E> node = first;
        while (node != null) {
            stringBuilder.append(node.element);
            if (node.next != null) {
                stringBuilder.append(", ");
            }
            node = node.next;

        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private Node<E> preNode(E element) {
        Node<E> node = first;
        while (node.next != null) {
            if (element.equals(node.next.element)) {
                break;
            }
            node = node.next;
        }
        return node;
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

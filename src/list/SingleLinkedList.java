package list;

/**
 * 数据结构之单链表
 * @author 严书航
 */
public class SingleLinkedList<E> {
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
     * 链表中存储的节点
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
}

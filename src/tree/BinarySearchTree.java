package tree;

import java.util.Comparator;
import java.util.Iterator;

/**
 * 二叉查找树
 * 节点 x 的左子树中的节点都比 x 小 ，右子树中的节点都比 x 大
 * 不可以存储 null
 *
 * @author 严书航
 */
public class BinarySearchTree<E> implements Iterable<E> {

    // 大小
    private int size;
    // 根节点
    private Node<E> root;
    // 用于比较 super：可以使用父类的比较器
    private Comparator<? super E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public Node<E> getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public E getRootElement() {
        return root.element;
    }

    /**
     * 插入新的节点，如果comparator不为null使用compare比较元素，否则使用compareTo比较
     *
     * @param element
     */
    public E add(E element) {
        Comparator<? super E> cpt = comparator;
        Node<E> t = root;
        int i;

        if (t == null) {
            root = new Node<>(element, null, null, null);
            size = 1;
            return element;
        }
        //t在向下遍历时会到null 需要一个变量记录parent节点
        Node<E> parent;
        if (cpt != null) {
            do {
                parent = t;
                i = cpt.compare(element, t.element);
                if (i <= 0) {
                    t = t.left;
                } else {
                    t = t.right;
                }
            } while (t != null);

        } else {
            Comparable<? super E> cpa = (Comparable<? super E>) element;
            do {
                parent = t;
                i = cpa.compareTo(t.element);
                if (i <= 0) {
                    t = t.left;
                } else {
                    t = t.right;
                }
            } while (t != null);

        }
        //创建节点
        Node<E> node = new Node<>(element, parent, null, null);
        //根据比较值确定node是左还是右节点
        if (i <= 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        size++;
        return element;
    }

    public boolean remove(E element) {
        Node<E> node = getNode(element);
        if (node == null) {
            return false;
        }
        return removeNode(node);
    }

    public E removeFirst() {
        Node<E> firstNode = minNode(root);
        removeNode(firstNode);
        return firstNode.element;
    }

    public E removeLast() {
        Node<E> lastNode = maxNode(root);
        removeNode(lastNode);
        return lastNode.element;
    }

    /**
     * 删除节点
     *
     * @param node
     * @return
     */
    private boolean removeNode(Node<E> node) {
        if (node == null) {
            throw new NullPointerException("null node");
        }
        Node<E> parent = node.parent;
        // 情况1：没有子节点直接删除
        if (node.right == null && node.left == null) {
            if (node == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            clearNode(node);
            size--;
            return true;
        }
        // 情况2：只有一个节点直接用其替换
        if (node.right == null) {
            Node<E> left = node.left;
            transplant(node, left);
            clearNode(node);
            size--;
            return true;
        }
        if (node.left == null) {
            Node<E> right = node.right;
            transplant(node, right);
            clearNode(node);
            size--;
            return true;
        }
        // 情况3：有两个节点，找到其后继节点替换
        Node<E> succ = successor(node);
        // 如果后继是直接右节点 直接替换
        if (succ == node.right) {
            transplant(node, succ);
            node.left.parent = succ;
            succ.left = node.left;
            clearNode(node);
            size--;
            return true;
        }
        // 如果后继不是直接右节点，分两种情快：1.后继有右节点，2.后继是叶子节点；第1种情况将右子树连接到后继的父节点上转化为第2种情况
        if (succ.right != null) {
            succ.right.parent = succ.parent;
            succ.parent.left = succ.right;
        }
        // 后继是叶节点直接替换
        Node<E> parent1 = succ.parent;
        transplant(node, succ);
        parent1.left = null;
        succ.right = node.right;
        succ.left = node.left;
        node.left.parent = succ;
        node.right.parent = succ;
        clearNode(node);
        size--;
        return true;
    }

    private void clearNode(Node<E> node) {
        node.parent = null;
        node.left = null;
        node.right = null;
        node.element = null;
    }

    /**
     * 删除操作各种情况有大量重复的判断 提取为一个方法 替换中只处理的父节点的关系， 子节点关系由删除的各种情况自己处理
     *
     * @param oldNode 旧节点
     * @param newNode 新节点
     */
    private void transplant(Node<E> oldNode, Node<E> newNode) {
        Node<E> parent = oldNode.parent;
        if (parent == null) {
            root = newNode;
            newNode.parent = null;
        } else {
            newNode.parent = parent;
            if (oldNode == parent.right) {
                parent.right = newNode;
            } else {
                parent.left = newNode;
            }
        }

    }
    // 删除替换方法，方法错误，而且没有意义，可以先删除后插入
//    public boolean replace(E oldVal, E newVal) {
//        Node<E> node = getNode(oldVal);
//        if (node == null) {
//            throw new IllegalArgumentException("this is such element in BST " + oldVal);
//        }
//        node.element = newVal;
//        return true;
//    }

    public E min() {
        return minNode(root).element;
    }

    public E max() {
        return maxNode(root).element;
    }

    /**
     * 以 t 为根节点的树中的最小节点
     *
     * @return
     */
    private Node<E> minNode(Node<E> t) {
        if (t != null) {
            while (t.left != null) {
                t = t.left;
            }
        }
        return t;
    }

    /**
     * 以 t 为根节点的树中的最大节点
     *
     * @return
     */
    private Node<E> maxNode(Node<E> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }
        return t;
    }

    public boolean contain(E element) {
        return getNode(element) != null;
    }

    /**
     * @param element 元素可以不在树中
     * @return 返回树中比指定的元素大的最小元素 如果没有返回null，如果有相等的返回相等的元素
     */
    public E celling(E element) {
        Node<E> r = root;
        Comparator<? super E> comp = comparator;
        if (comp != null) {
            while (r != null) {
                int cpVal = comp.compare(element, r.element);
                if (cpVal < 0) {
                    if (r.left != null) {
                        r = r.left;
                    } else {
                        return r.element;
                    }
                } else if (cpVal > 0) {
                    if (r.right != null) {
                        r = r.right;
                    } else {
                        Node<E> parent = r.parent;
                        while (r.parent != null && r == r.parent.right) {
                            r = parent;
                            parent = r.parent;
                        }
                        return parent.element;
                    }
                } else {
                    return r.element;
                }
            }
        } else {
            Comparable<? super E> comparable = (Comparable<? super E>) element;
            while (r != null) {
                int cpVal = comparable.compareTo(r.element);
                if (cpVal < 0) {
                    if (r.left != null) {
                        r = r.left;
                    } else {
                        return r.element;
                    }
                } else if (cpVal > 0) {
                    if (r.right != null) {
                        r = r.right;
                    } else {
                        Node<E> parent = r.parent;
                        while (r.parent != null && r == r.parent.right) {
                            r = parent;
                            parent = r.parent;
                        }
                        return parent.element;
                    }
                } else {
                    return r.element;
                }
            }
        }
        return null;
    }

    /**
     * @param element 元素 可以不在树中
     * @return 返回树中比指定元素小的最大元素，如果没有返回null，如果有相等的返回相等的元素
     */
    public E floor(E element) {
        Node<E> r = root;
        Comparator<? super E> comp = comparator;
        if (comp != null) {
            while (r != null) {
                int cpVal = comp.compare(element, r.element);
                if (cpVal > 0) {
                    if (r.right != null) {
                        r = r.right;
                    } else {
                        return r.element;
                    }
                } else if (cpVal < 0) {
                    if (r.left != null) {
                        r = r.left;
                    } else {
                        Node<E> parent = r.parent;
                        while (r.parent != null && r == r.parent.left) {
                            r = parent;
                            parent = r.parent;
                        }
                        return parent.element;
                    }
                } else {
                    return r.element;
                }
            }
        } else {
            Comparable<? super E> comparable = (Comparable<? super E>) element;
            while (r != null) {
                int cpVal = comparable.compareTo(r.element);
                if (cpVal > 0) {
                    if (r.right != null) {
                        r = r.right;
                    } else {
                        return r.element;
                    }
                } else if (cpVal < 0) {
                    if (r.left != null) {
                        r = r.left;
                    } else {
                        Node<E> parent = r.parent;
                        while (r.parent != null && r == r.parent.left) {
                            r = parent;
                            parent = r.parent;
                        }
                        return parent.element;
                    }
                } else {
                    return r.element;
                }
            }
        }
        return null;
    }

    /**
     * 先序遍历：使用递归版本
     */
    public void preOrderTraversal() {
        preOrderTraversal(root);
    }

    /**
     * 中序遍历：使用递归版本
     */
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    /**
     * 后序遍历：使用递归版本
     */
    public void postOrderTraversal() {
        postOrderTraversal(root);
    }

    private void preOrderTraversal(Node<E> node) {
        System.out.println(node.element);
        if (node.left != null) {
            preOrderTraversal(node.left);
        }
        if (node.right != null) {
            preOrderTraversal(node.right);
        }
    }


    private void inOrderTraversal(Node<E> node) {
        if (node.left != null) {
            inOrderTraversal(node.left);
        }
        System.out.println(node.element);
        if (node.right != null) {
            inOrderTraversal(node.right);
        }

    }

    private void postOrderTraversal(Node<E> node) {
        if (node.left != null) {
            postOrderTraversal(node.left);
        }
        if (node.right != null) {
            postOrderTraversal(node.right);
        }
        System.out.println(node.element);
    }

    /**
     * 元素的节点
     *
     * @param element
     * @return
     */
    private Node<E> getNode(E element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
        Node<E> p = root;
        if (comparator != null) {
            while (p != null) {
                int cv = comparator.compare(element, p.element);
                if (cv < 0) {
                    p = p.left;
                } else if (cv > 0) {
                    p = p.right;
                } else {
                    return p;
                }
            }
        } else {
            Comparable<? super E> cmp = (Comparable<? super E>) element;
            while (p != null) {
                int cv = cmp.compareTo(p.element);
                if (cv < 0) {
                    p = p.left;
                } else if (cv > 0) {
                    p = p.right;
                } else {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * 返回节点的后继
     *
     * @param node
     * @return
     */
    private Node<E> successor(Node<E> node) {
        if (node == null) {
            return null;
        }
        //有右节点时，返回右节点里的最小节点
        if (node.right != null) {
            return minNode(node.right);
        } else {
            //无右节点，向上回溯，直到节点是父节点的左节点，父节点即后继节点
            //或者parent==null：到达了根节点说明没有后继 返回null
            Node<E> parent = node.parent;
            while (parent != null && node == parent.right) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    /**
     * 返回节点的前驱，跟后继逻辑一样
     *
     * @param node
     * @return
     */
    private Node<E> predecessor(Node<E> node) {
        if (node == null) {
            return null;
        }
        //有左节点时，返回左节点里的最大节点
        if (node.left != null) {
            return maxNode(node.left);
        } else {
            Node<E> parent = node.parent;
            //无左节点，向上回溯，直到节点是父节点的右节点，父节点即前驱节点
            //或者parent==null：到达了根节点说明没有前驱 返回null
            while (parent != null && node == parent.left) {
                node = parent;
                parent = parent.parent;
            }
            return parent;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        Node<E> next;
        Node<E> lastReturn;

        public Itr() {
            this.next = minNode(root);
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            lastReturn = next;
            next = successor(next);
            return lastReturn.element;
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "BST []";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BST [");
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * 树中存的节点
     *
     * @param <E>
     */
    private static class Node<E> {
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

}

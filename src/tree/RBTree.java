package tree;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * 红黑树：平衡的二叉查找树
 * 主要难点在插入、删除之后的调整：变色、左、右旋操作来调整性质3、4
 * 红黑树性质：
 * 1. 节点有颜色，红色或黑色
 * 2. 根节点是黑色
 * 3. 不能出现连续的红色节点
 * 4. 任何节点到其叶子节点，所有路径中的黑色节点个数相同
 * 5. 空节点是黑色的
 */
public class RBTree<E> {
    private Node<E> root;
    private int size;
    private Comparator<? super E> comparator;

    public RBTree() {
    }

    /**
     * 指定元素的比较器
     *
     * @param comparator 比较器
     */
    public RBTree(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("can not add null");
        }
        if (root == null) {
            root = new Node<>(element, null, null, null, false);
        } else {
            Node<E> t = this.root;
            Node<E> p;
            int val;
            do {
                p = t;
                val = compare(element, t.element);
                if (val <= 0) {
                    t = t.left;
                } else {
                    t = t.right;
                }
            } while (t != null);
            // 新插入的节点都设置为红色
            Node<E> node = new Node<>(element, p, null, null, true);
            if (val <= 0) {
                p.left = node;
            } else {
                p.right = node;
            }
            fixAfterAdd(node);
        }
        size++;
    }

    public E remove(E element) {
        Node<E> node = getNode(element);
        if (node == null) {
            return null;
        }
        E oldVal = node.element;
        deleteNode(node);
        return oldVal;
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

    private void deleteNode(Node<E> node) {
        // 记录实际删掉的节点的颜色，如果删除掉的是黑色需要调整
        boolean red = node.red;
        // 情况1：叶节点，如果是黑色先调整，然后直接删除
        if (node.left == null && node.right == null) {
            if (!red) {
                fixAfterRemove(node);
            }
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            size--;
        } else {
            // 记录替换到删掉节点位置的节点
            Node<E> r;
            // 情况2：只有一个子树，直接替换
            if (node.left == null) {
                r = node.right;
                transplant(node, node.right);
                size--;
            } else if (node.right == null) {
                r = node.left;
                transplant(node, node.left);
                size--;
            } else {
                // 情况3：有两个子树，使用后继替换
                Node<E> succ = minNode(node.right);
                red = succ.red;
                succ.red = node.red;
                r = succ.right;
                // 有点繁琐，主要是r为null时，先对succ调整再删除succ
                if (r != null) {
                    if (succ == node.right) {
                        transplant(node, succ);
                        succ.left = node.left;
                        node.left.parent = succ;
                        size--;
                    } else {
                        succ.right.parent = succ.parent;
                        succ.parent.left = succ.right;
                        Node<E> parent1 = succ.parent;
                        transplant(node, succ);
                        parent1.left = null;
                        succ.right = node.right;
                        succ.left = node.left;
                        node.left.parent = succ;
                        node.right.parent = succ;
                        size--;
                    }
                    if (!red) {
                        fixAfterRemove(r);
                    }
                } else {
                    if (!red) {
                        fixAfterRemove(succ);
                    }
                    if (succ == node.right) {
                        transplant(node, succ);
                        succ.left = node.left;
                        node.left.parent = succ;
                        size--;
                    } else {
                        Node<E> parent1 = succ.parent;
                        transplant(node, succ);
                        parent1.left = null;
                        succ.right = node.right;
                        succ.left = node.left;
                        node.left.parent = succ;
                        node.right.parent = succ;
                        size--;
                    }
                }
            }
        }
    }

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
    @SuppressWarnings("unchecked")
    //选择合适的比较方式：自定义的Comparator或者元素实现Comparable的compare方法
    private int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<? super E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    // 未找到时返回null
    private Node<E> getNode(E element) {
        if (element != null) {
            Node<E> t = this.root;
            while (t != null) {
                int val = compare(element, t.element);
                if (val < 0) {
                    t = t.left;
                } else if (val > 0) {
                    t = t.right;
                } else {
                    return t;
                }
            }
        }
        return null;
    }

    private void fixAfterAdd(Node<E> node) {
        // 如果父节点是黑色的无需调整
        while (node != null && node.parent != null && node.parent.red) {
            if (node.parent == node.parent.parent.left) {
                Node<E> uncle = node.parent.parent.right;
                // 情况1：叔节点是红色，父节点、叔节点、祖父节点变色然后向上遍历
                if (isRed(uncle)) {
                    node.parent.red = false;
                    uncle.red = false;
                    node.parent.parent.red = true;
                    node = node.parent.parent;
                } else {
                    // 情况2：叔节点是黑色，node是右节点 左旋转化为情况3
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    // 情况3：node是左节点 变化父节点、祖父节点颜色 右旋 此时循环条件不成立调整完毕
                    node.parent.red = false;
                    node.parent.parent.red = true;
                    rotateRight(node.parent.parent);
                }
                // 相反的处理
            } else {
                Node<E> uncle = node.parent.parent.left;
                if (isRed(uncle)) {
                    node.parent.red = false;
                    uncle.red = false;
                    node.parent.parent.red = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.red = false;
                    node.parent.parent.red = true;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        // 有可能一直变色到根节点，根节点被变为了红色
        root.red = false;
    }

    // 空节点都是黑色 处理空节点的问题
    private boolean isRed(Node<E> node) {
        return node != null && node.red;
    }

    // 处理空节点的问题
    private void setRed(Node<E> node, boolean red) {
        if (node != null) {
            node.red = red;
        }
    }

    private void fixAfterRemove(Node<E> node) {
        //由于删除了一个黑色节点，node替换了其位置，需要在经过node的路线上增加一个黑节点
        while (node != root && !isRed(node)) {
            if (node == node.parent.left) {
                Node<E> brother = node.parent.right;
                // 情况1：brother是红色，parent肯定是黑色，两个都变色然后parent左旋，brother现在是黑色，转为情况2、3、4
                if (isRed(brother)) {
//                    brother.red = false;
//                    node.parent.red = true;
                    setRed(brother,false);
                    setRed(node.parent, true);
                    rotateLeft(node.parent);
                    brother = node.parent.right;
                }
                // 情况2：brother是黑色，其两个子节点也是黑色，brother变红，node子树跟brother子树黑节点数一致，将node指向上移一层
                // 如果node为红色，循环结束，设置node为黑色完成平衡
                // 如果node是root，循环结束，因为两边子树黑高已一致，已达到平衡
                // 如果node为黑色，继续循环，相当于将问题上移了一层
                if (!isRed(brother.left) && !isRed(brother.right)) {
//                    brother.red = true;
                    setRed(brother,true);
                    node = node.parent;
                } else {
                    // 情况3：brother的右节点为黑色，左节点为红色，brother右旋，brother的左节点占据brother的位置，将brother的指向重新赋值
                    // 右旋前改变brother 和 其左节点的颜色，该旋转没有破环brother树的结构性质和每条路上的黑节点数量
                    // 此时转为情况4
                    if (!isRed(brother.right)) {
//                        brother.red = true;
//                        brother.left.red = false;
                        setRed(brother,true);
                        setRed(brother.left,false);
                        rotateRight(brother);
                        brother = node.parent.right;
                    }
                    // 情况4：brother的右节点为红色，左节点和parent颜色随意，parent左旋，brother占据parent的位置，设置为parent的颜色
                    // parent设置为黑色，则经过node的路径增加了一个黑节点，
                    // 左旋后brother的左子树成为了parent的右子树，其上的两个节点仍为黑色和parent的原颜色，不需调整
                    // 左旋后brother的右子树少一个黑节点，由于brother的右节点为红色，将其设置为黑色
                    // 完成平衡，使用node = root 结束循环
                    brother.red = isRed(node.parent);
//                    node.parent.red = false;
//                    brother.right.red = false;
                    setRed(node.parent,false);
                    setRed(brother.right, false);
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                //跟上面一样的做法只是左右反过来
                Node<E> brother = node.parent.left;
                if (isRed(brother)) {
//                    brother.red = false;
//                    node.parent.red = true;
                    setRed(brother,false);
                    setRed(node.parent,true);
                    rotateRight(node.parent);
                    brother = node.parent.left;
                }
                if (!isRed(brother.left) && !isRed(brother.right)) {
//                    brother.red = true;
                    setRed(brother,true);
                    node = node.parent;
                } else {
                    if (!isRed(brother.left)) {
//                        brother.red = true;
//                        brother.right.red = false;
                        setRed(brother,true);
                        setRed(brother.right,false);
                        rotateLeft(brother);
                        brother = node.parent.left;
                    }
                    brother.red = isRed(node.parent);
//                    node.parent.red = false;
//                    brother.left.red = false;
                    setRed(node.parent,false);
                    setRed(brother.left,false);
                    rotateRight(node.parent);
                    node = root;
                }
            }
        }
        node.red = false;
    }

    private Node<E> minNode(Node<E> node) {
        if (node != null) {
            while (node.left != null) {
                node = node.left;
            }
        }
        return node;
    }

    /**
     * 右旋
     */
    private void rotateRight(Node<E> n) {
        if (n != null) {
            Node<E> parent = n.parent;
            Node<E> left = n.left;
            // 处理父节点关系
            left.parent = parent;
            if (parent == null) {
                root = left;
            } else if (n == parent.left) {
                parent.left = left;
            } else {
                parent.right = left;
            }
            // 处理子节点关系
            n.left = left.right;
            if (left.right != null) {
                left.right.parent = n;
            }
            // 处理相对关系
            left.right = n;
            n.parent = left;
        }
    }

    /**
     * 左旋 操作跟右旋相反
     */
    private void rotateLeft(Node<E> n) {
        if (n != null) {
            Node<E> parent = n.parent;
            Node<E> right = n.right;
            right.parent = parent;
            if (parent == null) {
                root = right;
            } else if (n == parent.left) {
                parent.left = right;
            } else {
                parent.right = right;
            }
            n.right = right.left;
            if (right.left != null) {
                right.left.parent = n;
            }
            right.left = n;
            n.parent = right;
        }
    }

    public void printTree() {
        if (root != null) {
            LinkedList<LinkedList<Node<E>>> linkedLists = new LinkedList<>();
            LinkedList<Node<E>> nodes = new LinkedList<>();
            nodes.add(root);
            linkedLists.add(nodes);
            while (true) {
                LinkedList<Node<E>> nodes1 = new LinkedList<>();
                for (Node<E> node : nodes) {
                    if (node.left != null) {
                        nodes1.add(node.left);
                    }
                    if (node.right != null) {
                        nodes1.add(node.right);
                    }
                }
                if (!nodes1.isEmpty()) {
                    linkedLists.add(nodes1);
                    nodes = nodes1;
                } else {
                    break;
                }
            }
            for (LinkedList<Node<E>> linkedList : linkedLists) {
                for (Node<E> node : linkedList) {
                    printNode(node);
                    System.out.print("(");
                    printNode(node.left);
                    printNode(node.right);
                    System.out.print(")");
                    System.out.print("  |  ");
                }
                System.out.println();
            }
        }
    }

    private void printNode(Node<E> node) {
        if (node != null) {
            System.out.print(node.element + " color:" + (node.red ? "red  " : "black  "));
        }
    }

    private static class Node<E> {
        E element;
        Node<E> parent;
        Node<E> left;
        Node<E> right;
        boolean red;

        Node(E element, Node<E> parent, Node<E> left, Node<E> right, boolean red) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.red = red;
        }
    }
}

import edu.princeton.cs.algs4.Queue;

/**
 * Created by Luke on 1/22/18.
 * The simple table is implemented by BST data structure
 */
public class ST<Key extends Comparable<Key>, Value> {
    private Node root;
    private class Node {
        private Key key;
        private Value value;
        private int count;
        private Node left, right;
        public Node(Key key, Value value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    public void put (Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null) {
            return new Node(key, val, 1);
        }

        if (key.compareTo(node.key) > 0) {
            node.right = put(node.right, key, val);
        } else if (key.compareTo(node.key) < 0) {
            node.left = put(node.left, key, val);
        } else {
            node.value = val;
        }
        node.count = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Key ceiling(Key key) {
        Node cur = ceiling(root, key);
        if (cur == null) return null;
        return cur.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);

        if (cmp > 0) ceiling(node.right, key);

        Node tmp = ceiling(node.left, key);
        if (tmp != null) return tmp;
        return node;
    }

    public Key floor(Key key) {
        Node cur = floor(root, key);
        if (cur == null) return null;
        return cur.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return floor(node.left, key);

        Node tmp = floor(node.right, key);
        if (tmp != null) return tmp;
        return node;
    }

    public Value get(Key key) {
        if (key == null || root == null) {
            return null;
        }
        Node cur = root;
        while (cur != null) {
            if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                return cur.value;
            }
        }
        return null;
    }


    public boolean contains(Key key) {
        if (key == null) {
            return false;
        }

        Node cur = root;
        while (cur != null) {
            if (key.compareTo(cur.key) > 0) {
                cur = cur.right;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                return true;
            }
        }
        return false;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }


    private Node delete(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);

        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node t = node;
            node = min(t.right);
            node.right = deleteMin(node);
            node.left = t.left;
        }
        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);
    }


    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.count = size(node.left) + 1 + size(node.right);
        return node;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;

        return node.count;
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node root, Key key) {
        if (root == null) {
            return 0;
        }

        int cmp = key.compareTo(root.key);
        if (cmp > 0) return rank(root.right, key) + 1 + size(root.left);
        else if (cmp < 0) return rank(root.left, key);
        else return size(root);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        inOrder(root, queue);
        return queue;
    }

    public void inOrder(Node node, Queue<Key> queue) {
        if (node == null) return;

        inOrder(node.left, queue);
        queue.enqueue(node.key);
        inOrder(node.right, queue);

    }

    public static void main(String[] args) {
        ST st = new ST<Integer, Integer>();
        st.put(1, 1);
        st.get(1);

    }
}

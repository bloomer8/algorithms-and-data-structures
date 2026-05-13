public class BStarTree {

    private static class Node {

        int t;
        int n;
        int[] keys;
        Node[] children;
        boolean leaf;

        Node(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            this.keys = new int[2 * t - 1];
            this.children = new Node[2 * t];
            this.n = 0;
        }
    }

    private Node root;
    private final int t;
    private long operations;

    public BStarTree(int t) {
        this.t = t;
        root = new Node(t, true);
    }

    public long getOperations() {
        return operations;
    }

    private void resetOperations() {
        operations = 0;
    }

    public boolean search(int key) {
        resetOperations();
        return search(root, key) != null;
    }

    private Node search(Node node, int key) {

        int i = 0;
        while (i < node.n && key > node.keys[i]) {
            operations++;
            i++;
        }
        if (i < node.n && key == node.keys[i]) {
            operations++;
            return node;
        }
        if (node.leaf) {
            operations++;
            return null;
        }
        return search(node.children[i], key);
    }

    public void insert(int key) {
        resetOperations();
        Node r = root;
        if (r.n == 2 * t - 1) {
            Node s = new Node(t, false);
            root = s;
            s.children[0] = r;
            splitChild(s, 0, r);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(Node node, int key) {

        int i = node.n - 1;
        if (node.leaf) {
            while (i >= 0 && key < node.keys[i]) {
                operations++;
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                operations++;
                i--;
            }
            i++;
            if (node.children[i].n == 2 * t - 1) {
                splitChild(node, i, node.children[i]);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(Node parent, int index, Node fullChild) {

        Node newNode = new Node(t, fullChild.leaf);
        newNode.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            operations++;
            newNode.keys[j] = fullChild.keys[j + t];
        }
        if (!fullChild.leaf) {
            for (int j = 0; j < t; j++) {
                operations++;
                newNode.children[j] = fullChild.children[j + t];
            }
        }

        fullChild.n = t - 1;

        for (int j = parent.n; j >= index + 1; j--) {
            operations++;
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[index + 1] = newNode;
        for (int j = parent.n - 1; j >= index; j--) {
            operations++;
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[index] = fullChild.keys[t - 1];
        parent.n++;
    }

    public void remove(int key) {
        resetOperations();
        remove(root, key);
        if (root.n == 0) {
            if (!root.leaf) {
                root = root.children[0];
            }
        }
    }

    private void remove(Node node, int key) {

        int idx = findKey(node, key);
        if (idx < node.n && node.keys[idx] == key) {
            if (node.leaf) {
                removeFromLeaf(node, idx);
            } else {
                removeFromNonLeaf(node, idx);
            }
        } else {

            if (node.leaf) {
                return;
            }
            boolean flag = (idx == node.n);
            if (node.children[idx].n < t) {
                fill(node, idx);
            }
            if (flag && idx > node.n) {
                remove(node.children[idx - 1], key);
            } else {
                remove(node.children[idx], key);
            }
        }
    }

    private int findKey(Node node, int key) {
        int idx = 0;
        while (idx < node.n && node.keys[idx] < key) {
            operations++;
            idx++;
        }
        return idx;
    }

    private void removeFromLeaf(Node node, int idx) {

        for (int i = idx + 1; i < node.n; i++) {
            operations++;
            node.keys[i - 1] = node.keys[i];
        }
        node.n--;
    }

    private void removeFromNonLeaf(Node node, int idx) {

        int key = node.keys[idx];
        if (node.children[idx].n >= t) {
            int pred = getPredecessor(node, idx);
            node.keys[idx] = pred;
            remove(node.children[idx], pred);
        } else if (node.children[idx + 1].n >= t) {
            int succ = getSuccessor(node, idx);
            node.keys[idx] = succ;
            remove(node.children[idx + 1], succ);
        } else {
            merge(node, idx);
            remove(node.children[idx], key);
        }
    }

    private int getPredecessor(Node node, int idx) {

        Node current = node.children[idx];
        while (!current.leaf) {
            operations++;
            current = current.children[current.n];
        }
        return current.keys[current.n - 1];
    }

    private int getSuccessor(Node node, int idx) {

        Node current = node.children[idx + 1];
        while (!current.leaf) {
            operations++;
            current = current.children[0];
        }
        return current.keys[0];
    }

    private void fill(Node node, int idx) {

        if (idx != 0 && node.children[idx - 1].n >= t) {
            borrowFromPrevious(node, idx);

        } else if (idx != node.n && node.children[idx + 1].n >= t) {
            borrowFromNext(node, idx);
        } else {
            if (idx != node.n) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }

    private void borrowFromPrevious(Node node, int idx) {

        Node child = node.children[idx];
        Node sibling = node.children[idx - 1];
        for (int i = child.n - 1; i >= 0; i--) {
            operations++;
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                operations++;
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = node.keys[idx - 1];
        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }
        node.keys[idx - 1] = sibling.keys[sibling.n - 1];
        child.n++;
        sibling.n--;
    }

    private void borrowFromNext(Node node, int idx) {

        Node child = node.children[idx];
        Node sibling = node.children[idx + 1];
        child.keys[child.n] = node.keys[idx];
        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }
        node.keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.n; i++) {
            operations++;
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                operations++;
                sibling.children[i - 1] = sibling.children[i];
            }
        }
        child.n++;
        sibling.n--;
    }

    private void merge(Node node, int idx) {
        Node child = node.children[idx];
        Node sibling = node.children[idx + 1];
        child.keys[t - 1] = node.keys[idx];

        for (int i = 0; i < sibling.n; i++) {
            operations++;
            child.keys[i + t] = sibling.keys[i];
        }
        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; i++) {
                operations++;
                child.children[i + t] = sibling.children[i];
            }
        }
        for (int i = idx + 1; i < node.n; i++) {
            operations++;
            node.keys[i - 1] = node.keys[i];
        }
        for (int i = idx + 2; i <= node.n; i++) {
            operations++;
            node.children[i - 1] = node.children[i];
        }
        child.n += sibling.n + 1;
        node.n--;
    }
}
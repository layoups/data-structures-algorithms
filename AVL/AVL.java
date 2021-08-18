import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Karim Layoun
 * @userid klayoun3
 * @GTID 903210227
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        this();
        if (data == null) {
            throw new IllegalArgumentException("The collection is null");
        }
        for (T el: data) {
            add(el);
        }
    }

    /**
     * height method
     *
     * @param node fow which I want the height
     * @return the height of the node
     */
    private int height(AVLNode<T> node) {
        return node != null ? node.getHeight() : -1;
    }

    /**
     * updates the height and bf of a node
     *
     * @param node to be updated
     */
    private void updateHBF(AVLNode<T> node) {
        node.setHeight(Math.max(height(node.getLeft()),
                height(node.getRight())) + 1);
        node.setBalanceFactor(height(node.getLeft())
                - height(node.getRight()));
    }

    /**
     * rotates left
     *
     * @param node to be rotated
     * @return new root of subtree
     */
    private AVLNode<T> left(AVLNode<T> node) {
        AVLNode<T> ret = node.getRight();
        node.setRight(ret.getLeft());
        ret.setLeft(node);
        updateHBF(node);
        updateHBF(ret);
        return ret;
    }

    /**
     * rotates right
     *
     * @param node to be rotated
     * @return new root of subtree
     */
    private AVLNode<T> right(AVLNode<T> node) {
        AVLNode<T> ret = node.getLeft();
        node.setLeft(ret.getRight());
        ret.setRight(node);
        updateHBF(node);
        updateHBF(ret);
        return ret;
    }

    /**
     * balances node
     *
     * @param node to be balanced
     * @return new root of subtree
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        updateHBF(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(left(node.getLeft()));
                return right(node);
            }
            return right(node);
        }
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(right(node.getRight()));
                return left(node);
            }
            return left(node);
        }
        return node;
    }

    /**
     * helper method for add
     *
     * @param data to be added
     * @param node current node
     * @return updated avl
     */
    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            node = new AVLNode<>(data);
        }
        if (node.getData().compareTo(data) == 0) {
            return node;
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(add(data, node.getLeft()));
        } else {
            node.setRight(add(data, node.getRight()));
        }
        return balance(node);
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null to AVL");
        }
        root = add(data, root);

    }

    /**
     * checks if node has right child
     *
     * @param node to be checked
     * @return true if does not have a right child
     */
    private boolean isRightLeaf(AVLNode<T> node) {
        return node.getRight() == null && node.getLeft() != null;
    }

    /**
     * checks if node has left child
     *
     * @param node to be checked
     * @return true if does not have a left child
     */
    private boolean isLeftLeaf(AVLNode<T> node) {
        return node.getLeft() == null && node.getRight() != null;
    }

    /**
     * checks if node is a leaf
     *
     * @param node to be checked
     * @return true if node is a leaf
     */
    private boolean isLeaf(AVLNode<T> node) {
        return node.getLeft() == null && node.getRight() == null;
    }

    /**
     * finds and removes successor of node
     *
     * @param node to find successor to
     * @param dummy dummy node to store successor data
     * @return the right subtree of node without successor
     */
    private AVLNode<T> goLeft(AVLNode<T> node, AVLNode<T> dummy) {
        System.out.println("hi");
        if (!(isLeaf(node) || isLeftLeaf(node))) {
            node.setLeft(goLeft(node.getLeft(), dummy));
            return balance(node);
        }
        dummy.setData(node.getData());
        return node.getRight();
    }

    /**
     * helper method for remove
     *
     * @param data to be removed
     * @param node current node
     * @param dum the data to be returned
     * @return updated avl
     */
    private AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> dum) {
        if (node == null) {
            throw new NoSuchElementException("The given data"
                    + " is not in the AVL");
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(remove(data, node.getLeft(), dum));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(remove(data, node.getRight(), dum));
        } else {
            dum.setData(node.getData());
            if (isLeaf(node)) {
                return null;
            }
            if (isLeftLeaf(node)) {
                return node.getRight();
            }
            if (isRightLeaf(node)) {
                return node.getLeft();
            }
            AVLNode<T> succ = new AVLNode<>(null);
            node.setRight(goLeft(node.getRight(), succ));
            node.setData(succ.getData());
        }
        return balance(node);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There is no null element in"
                    + " the AVL");
        }
        AVLNode<T> dum = new AVLNode<>(null);
        this.root = remove(data, this.root, dum);
        size--;
        return dum.getData();
    }

    /**
     * searches avl to find data
     *
     * @param data to be found
     * @param node current node
     * @return true if found
     */
    private AVLNode<T> search(T data, AVLNode<T> node) {
        if (node == null) {
            return node;
        }
        if (node.getData().compareTo(data) > 0) {
            return search(data, node.getLeft());
        }
        if (node.getData().compareTo(data) < 0) {
            return (search(data, node.getRight()));
        }
        return node;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There are no null elements "
                    + "in the AVL");
        }
        AVLNode<T> ret = search(data, this.root);
        if (ret == null) {
            throw new NoSuchElementException("The given data"
                    + " is not in the AVL");
        }
        return ret.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("There are no null elements "
                    + "in the AVL");
        }
        return search(data, this.root) != null;
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * method that goes along the right children of a subtree
     *
     * @param node the current node
     * @return the rightmost node
     */
    private AVLNode<T> goRight(AVLNode<T> node) {
        if (isLeaf(node.getRight())) {
            return node;
        } else if (isRightLeaf(node.getRight())) {
            return node.getRight().getLeft();
        }
        return goRight(node.getRight());
    }

    @Override
    public T getSecondLargest() {
        if (this.size < 2) {
            throw new NoSuchElementException("The AVl doesn't have 2 elements");
        }
        if (this.size == 2) {
            if (isRightLeaf(this.root)) {
                return this.root.getLeft().getData();
            }
            return this.root.getData();
        }
        AVLNode<T> sc = goRight(this.root);
        return sc.getData();
    }

    /**
     * helper method for equals
     *
     * @param node the current node
     * @param other the other current node
     * @return true if both trees are equal
     */
    private boolean equal(AVLNode<T> node, AVLNode<T> other) {
        if (node == null && other == null) {
            return true;
        }
        if (node == null || other == null) {
            return false;
        }
        if (!(node.getData().equals(other.getData()))) {
            return false;
        }
        return equal(node.getLeft(), other.getLeft())
                && equal(node.getRight(), other.getRight());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof AVL) {
            AVL<T> that = (AVL<T>) obj;
            if (this.size != that.size) {
                return false;
            }
            return equal(this.root, that.root);
        }
        return false;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public int height() {
        return height(root);
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}

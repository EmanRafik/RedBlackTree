package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private INode<T, V> nullNode;
	private INode<T, V> root;

	private class Node<K extends Comparable<K>, S> implements INode<T, V> {

		private V value;
		private T key;
		private INode<T, V> parent, leftChild, rightChild;
		private boolean color = RED;
		private boolean nullFlag;

		public Node(boolean nullFlag) {
			this.nullFlag = nullFlag;
		}

		@Override
		public void setParent(INode<T, V> parent) {
			// TODO Auto-generated method stub
			this.parent = parent;
		}

		@Override
		public INode<T, V> getParent() {
			// TODO Auto-generated method stub
			return parent;
		}

		@Override
		public void setLeftChild(INode<T, V> leftChild) {
			// TODO Auto-generated method stub
			this.leftChild = leftChild;
		}

		@Override
		public INode<T, V> getLeftChild() {
			// TODO Auto-generated method stub
			return leftChild;
		}

		@Override
		public void setRightChild(INode<T, V> rightChild) {
			// TODO Auto-generated method stub
			this.rightChild = rightChild;
		}

		@Override
		public INode<T, V> getRightChild() {
			// TODO Auto-generated method stub
			return rightChild;
		}

		@Override
		public T getKey() {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public void setKey(T key) {
			// TODO Auto-generated method stub
			this.key = key;
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return value;
		}

		@Override
		public void setValue(V value) {
			// TODO Auto-generated method stub
			this.value = value;
		}

		@Override
		public boolean getColor() {
			// TODO Auto-generated method stub
			return color;
		}

		@Override
		public void setColor(boolean color) {
			// TODO Auto-generated method stub
			this.color = color;
		}

		@Override
		public boolean isNull() {
			// TODO Auto-generated method stub
			return nullFlag;
		}

	}

	public RedBlackTree() {
		nullNode = new Node<T, V>(true);
		nullNode.setColor(INode.BLACK);
		root = nullNode;
	}

	@Override
	public INode<T, V> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if (root.isNull()) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		root = nullNode;
	}

	@Override
	public V search(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		INode<T, V> t = root;
		while (!t.isNull() && key.compareTo(t.getKey()) != 0) {
			if (key.compareTo(t.getKey()) > 0) {
				t = t.getRightChild();
			} else {
				t = t.getLeftChild();
			}
		}
		if (!t.isNull()) {
			return t.getValue();
		}
		return null;
	}

	@Override
	public boolean contains(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		INode<T, V> t = root;
		while (!t.isNull() && key.compareTo(t.getKey()) != 0) {
			if (key.compareTo(t.getKey()) > 0) {
				t = t.getRightChild();
			} else {
				t = t.getLeftChild();
			}
		}
		if (!t.isNull()) {
			return true;
		}
		return false;
	}

	@Override
	public void insert(T key, V value) {
		// TODO Auto-generated method stub
		if (key == null || value == null) {
			throw new RuntimeErrorException(new Error());
		}
		INode<T, V> t = root;
		INode<T, V> p = nullNode;
		while (!t.isNull()) {
			p = t;
			if (key.compareTo(t.getKey()) == 0) {
				t.setValue(value);
				return;
			} else if (key.compareTo(t.getKey()) < 0) {
				t = t.getLeftChild();
			} else {
				t = t.getRightChild();
			}
		}
		INode<T, V> z = new Node<T, V>(false);
		z.setParent(p);
		z.setKey(key);
		z.setValue(value);
		if (p == nullNode)
			root = z;
		else if (key.compareTo(p.getKey()) < 0)
			p.setLeftChild(z);
		else
			p.setRightChild(z);
		z.setRightChild(nullNode);
		z.setLeftChild(nullNode);
		z.setColor(INode.RED);
		insertFix(z);
	}

	private void insertFix(INode<T, V> z) {
		while (z.getParent().getColor()) {
			/** inserted node is left child **/
			if (z.getParent() == z.getParent().getParent().getLeftChild()) {
				z = insertFix_left(z);
				/** inserted node is right child **/
			} else if (z.getParent() == z.getParent().getParent().getRightChild()) {
				z = insertFix_right(z);
			}
		}
		root.setColor(INode.BLACK);
	}

	private INode<T, V> insertFix_left(INode<T, V> z) {
		INode<T, V> y = z.getParent().getParent().getRightChild();
		/** case 1: uncle is red **/
		if (y.getColor()) {
			y.setColor(INode.BLACK);
			z.getParent().setColor(INode.BLACK);
			z.getParent().getParent().setColor(INode.RED);
			z = z.getParent().getParent();
		} else {
			/** case 2: uncle is black, inserted node is right child **/
			if (z == z.getParent().getRightChild()) {
				z = z.getParent();
				rotateLeft(z);
			}
			/** case 3: uncle is black, inserted node is left child **/
			z.getParent().setColor(INode.BLACK);
			z.getParent().getParent().setColor(INode.RED);
			rotateRight(z.getParent().getParent());
		}
		return z;
	}

	private INode<T, V> insertFix_right(INode<T, V> z) {
		INode<T, V> y = z.getParent().getParent().getLeftChild();
		/** case 1: uncle is red **/
		if (y.getColor()) {
			y.setColor(INode.BLACK);
			z.getParent().setColor(INode.BLACK);
			z.getParent().getParent().setColor(INode.RED);
			z = z.getParent().getParent();
		} else {
			/** case 2: uncle is black, inserted node is left child **/
			if (z == z.getParent().getLeftChild()) {
				z = z.getParent();
				rotateRight(z);
			}
			/** case 3: uncle is black, inserted node is right child **/
			z.getParent().setColor(INode.BLACK);
			z.getParent().getParent().setColor(INode.RED);
			rotateLeft(z.getParent().getParent());
		}
		return z;
	}

	@Override
	public boolean delete(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (!contains(key)) {
			return false;
		}
		INode<T, V> t = this.root;
		while (key.compareTo(t.getKey()) != 0) {
			if (key.compareTo(t.getKey()) > 0) {
				t = t.getRightChild();
			} else {
				t = t.getLeftChild();
			}
		}
		boolean color = t.getColor();
		INode<T, V> x;
		if (t.getLeftChild().isNull()) {
			x = t.getRightChild();
			transplant(t.getRightChild(), t);
			if (x.isNull()) {
				x.setParent(t.getParent());
			}
		} else if (t.getRightChild().isNull()) {
			x = t.getLeftChild();
			transplant(t.getLeftChild(), t);
			if (x.isNull()) {
				x.setParent(t.getParent());
			}
		} else {
			INode<T, V> min = minimum(t.getRightChild());
			x = min.getRightChild();
			color = min.getColor();
			if (min.getParent() != t) {
				transplant(x, min);
				min.setRightChild(t.getRightChild());
				min.getRightChild().setParent(min);
			} else {
				x.setParent(min);
			}
			transplant(min, t);
			min.setLeftChild(t.getLeftChild());
			t.getLeftChild().setParent(min);
			min.setColor(t.getColor());
			t = min;
		}
		if (!color) {
			deleteFix(x);
		}
		return true;
	}

	private void deleteFix(INode<T, V> x) {
		while (x != this.root && !x.getColor()) {
			if (x == x.getParent().getLeftChild()) {
				x = deleteFix_Left(x);
			} else {
				x = deleteFix_Right(x);
			}
		}
		x.setColor(INode.BLACK);
	}

	private INode<T, V> deleteFix_Left(INode<T, V> x) {
		INode<T, V> z = x.getParent().getRightChild();
		if (z.getColor()) {
			z.setColor(INode.BLACK);
			x.getParent().setColor(INode.RED);
			rotateLeft(x.getParent());
			z = x.getParent().getRightChild();
		}
		if (!z.getLeftChild().getColor() && !z.getRightChild().getColor()) {
			z.setColor(INode.RED);
			x = x.getParent();
		} else {
			if (z.getLeftChild().getColor() && !z.getRightChild().getColor()) {
				z.getLeftChild().setColor(INode.BLACK);
				z.setColor(INode.RED);
				rotateRight(z);
				z = x.getParent().getRightChild();
			}
			z.setColor(x.getParent().getColor());
			x.getParent().setColor(INode.BLACK);
			z.getRightChild().setColor(INode.BLACK);
			rotateLeft(x.getParent());
			x = this.root;
		}
		return x;
	}

	private INode<T, V> deleteFix_Right(INode<T, V> x) {
		INode<T, V> z = x.getParent().getLeftChild();
		if (z.getColor()) {
			z.setColor(INode.BLACK);
			x.getParent().setColor(INode.RED);
			rotateRight(x.getParent());
			z = x.getParent().getLeftChild();
		}
		if (!z.getLeftChild().getColor() && !z.getRightChild().getColor()) {
			z.setColor(INode.RED);
			x = x.getParent();
		} else {
			if (z.getRightChild().getColor() && !z.getLeftChild().getColor()) {
				z.getRightChild().setColor(INode.BLACK);
				z.setColor(INode.RED);
				rotateLeft(z);
				z = x.getParent().getLeftChild();
			}
			z.setColor(x.getParent().getColor());
			x.getParent().setColor(INode.BLACK);
			z.getLeftChild().setColor(INode.BLACK);
			rotateRight(x.getParent());
			x = this.root;
		}
		return x;
	}

	private void rotateLeft(INode<T, V> z) {
		INode<T, V> y = z.getRightChild();
		z.setRightChild(y.getLeftChild());
		if (!y.getLeftChild().isNull()) {
			y.getLeftChild().setParent(z);
		}
		y.setParent(z.getParent());
		if (z.getParent().isNull()) {
			this.root = y;
		} else if (z == z.getParent().getLeftChild()) {
			z.getParent().setLeftChild(y);
		} else {
			z.getParent().setRightChild(y);
		}
		y.setLeftChild(z);
		z.setParent(y);
	}

	private void rotateRight(INode<T, V> z) {
		INode<T, V> x = z.getLeftChild();
		z.setLeftChild(x.getRightChild());
		if (!x.getRightChild().isNull()) {
			x.getRightChild().setParent(z);
		}
		x.setParent(z.getParent());
		if (z.getParent().isNull()) {
			this.root = x;
		} else if (z == z.getParent().getRightChild()) {
			z.getParent().setRightChild(x);
		} else {
			z.getParent().setLeftChild(x);
		}
		x.setRightChild(z);
		z.setParent(x);
	}

	/** get minimum node in a tree rooted at t **/
	private INode<T, V> minimum(INode<T, V> t) {
		while (!t.getLeftChild().isNull()) {
			t = t.getLeftChild();
		}
		return t;
	}

	/** transplant node x at node y **/
	private void transplant(INode<T, V> x, INode<T, V> y) {
		x.setParent(y.getParent());
		if (y.getParent().isNull()) {
			this.root = x;
		} else if (y == y.getParent().getRightChild()) {
			y.getParent().setRightChild(x);
		} else {
			y.getParent().setLeftChild(x);
		}
	}
}

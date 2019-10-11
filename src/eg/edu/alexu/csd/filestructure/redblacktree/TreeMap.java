package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

	private IRedBlackTree<T, V> rb = new RedBlackTree<T, V>();
	private int size = 0;

	@Override
	public Entry<T, V> ceilingEntry(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (rb.contains(key)) {
			return new AbstractMap.SimpleEntry<T, V>(key, rb.search(key));
		}
		return new AbstractMap.SimpleEntry<T, V>(predecessor(key).getKey(), predecessor(key).getValue());
	}

	@Override
	public T ceilingKey(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (rb.contains(key)) {
			return key;
		}
		return predecessor(key).getKey();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		rb.clear();
		size = 0;
	}

	@Override
	public boolean containsKey(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (rb.contains(key)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		// TODO Auto-generated method stub
		if (value == null) {
			throw new RuntimeErrorException(new Error());
		}
		return inorderValues(rb.getRoot(), new ArrayList<V>()).contains(value);
	}

	@Override
	public Set<Entry<T, V>> entrySet() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		Set<Map.Entry<T, V>> set = new LinkedHashSet<Entry<T, V>>();
		return inorderEntry(rb.getRoot(), set);
	}

	@Override
	public Entry<T, V> firstEntry() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		INode<T, V> min = minimum(rb.getRoot());
		return new AbstractMap.SimpleEntry<T, V>(min.getKey(), min.getValue());
	}

	@Override
	public T firstKey() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		return minimum(rb.getRoot()).getKey();
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (size == 0) {
			return null;
		}
		if (rb.contains(key)) {
			return new AbstractMap.SimpleEntry<T, V>(key, rb.search(key));
		}
		INode<T, V> successor = successor(key);
		return new AbstractMap.SimpleEntry<T, V>(successor.getKey(), successor.getValue());

	}

	@Override
	public T floorKey(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (size == 0) {
			return null;
		}
		if (rb.contains(key)) {
			return key;
		}
		return successor(key).getKey();
	}

	@Override
	public V get(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		return rb.search(key);
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey) {
		// TODO Auto-generated method stub
		if (toKey == null) {
			throw new RuntimeErrorException(new Error());
		}
		return inorderHeapMap(rb.getRoot(), toKey, new ArrayList<Entry<T, V>>(), false);
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		// TODO Auto-generated method stub
		if (toKey == null) {
			throw new RuntimeErrorException(new Error());
		}
		return inorderHeapMap(rb.getRoot(), toKey, new ArrayList<Entry<T, V>>(), inclusive);
	}

	@Override
	public Set<T> keySet() {
		// TODO Auto-generated method stub
		return inorderKey(rb.getRoot(), new TreeSet<T>());
	}

	@Override
	public Entry<T, V> lastEntry() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		INode<T, V> max = maximum(rb.getRoot());
		return new AbstractMap.SimpleEntry<T, V>(max.getKey(), max.getValue());
	}

	@Override
	public T lastKey() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		return maximum(rb.getRoot()).getKey();
	}

	@Override
	public Entry<T, V> pollFirstEntry() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		INode<T, V> min = minimum(rb.getRoot());
		remove(min.getKey());
		return new AbstractMap.SimpleEntry<T, V>(min.getKey(), min.getValue());
	}

	@Override
	public Entry<T, V> pollLastEntry() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		INode<T, V> max = maximum(rb.getRoot());
		remove(max.getKey());
		return new AbstractMap.SimpleEntry<T, V>(max.getKey(), max.getValue());
	}

	@Override
	public void put(T key, V value) {
		// TODO Auto-generated method stub
		if (key == null || value == null) {
			throw new RuntimeErrorException(new Error());
		}
		if (!rb.contains(key)) {
			size++;
		}
		rb.insert(key, value);	
	}

	@Override
	public void putAll(Map<T, V> map) {
		// TODO Auto-generated method stub
		if (map == null) {
			throw new RuntimeErrorException(new Error());
		}
		for (Map.Entry<T, V> m : map.entrySet()) {
			rb.insert(m.getKey(), m.getValue());
			size++;
		}
	}

	@Override
	public boolean remove(T key) {
		// TODO Auto-generated method stub
		if (key == null) {
			throw new RuntimeErrorException(new Error());
		}
		boolean d = rb.delete(key);
		if (d) {
			size--;
		}
		return d;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return inorderValues(rb.getRoot(), new ArrayList<V>());
	}

	private Collection<V> inorderValues(INode<T,V> t,Collection<V> v) {
		if (!t.isNull()) {
			v = inorderValues(t.getLeftChild(), v);
			v.add(t.getValue());
			v = inorderValues(t.getRightChild(), v);
		}
		return v;
	}

	/** get reference to node with key key **/
	private INode<T, V> getNode(T key) {
		if (!rb.contains(key)) {
			return null;
		}
		INode<T, V> t = rb.getRoot();
		while (key.compareTo(t.getKey()) != 0) {
			if (key.compareTo(t.getKey()) < 0)
				t = t.getLeftChild();
			else
				t = t.getRightChild();
		}
		return t;
	}

	/** get predecessor of given key **/
	private INode<T, V> predecessor(T key) {
		INode<T, V> t = getNode(key);
		if (!t.getRightChild().isNull()) {
			return minimum(t);
		}
		INode<T, V> p = t.getParent();
		while (!p.isNull() && t == t.getParent().getRightChild()) {
			t = p;
			p = p.getParent();
		}
		return p;
	}

	/** get successor of given key **/
	private INode<T, V> successor(T key) {
		INode<T, V> t = getNode(key);
		if (!t.getRightChild().isNull()) {
			return maximum(t);
		}
		INode<T, V> p = t.getParent();
		while (!p.isNull() && t == t.getParent().getRightChild()) {
			t = p;
			p = p.getParent();
		}
		return p;
	}

	/** get minimum node in a tree rooted at t **/
	private INode<T, V> minimum(INode<T, V> t) {
		while (!t.getLeftChild().isNull()) {
			t = t.getLeftChild();
		}
		return t;
	}

	/** get maximum node in a tree rooted at t **/
	private INode<T, V> maximum(INode<T, V> t) {
		while (!t.getRightChild().isNull()) {
			t = t.getRightChild();
		}
		return t;
	}

	private Set<Entry<T, V>> inorderEntry(INode<T, V> t, Set<Entry<T, V>> set) {
		if (!t.isNull()) {
			inorderEntry(t.getLeftChild(), set);
			//Entry<T, V> entry = new AbstractMap.SimpleEntry<T, V>(t.getKey(), t.getValue());
			set.add(new AbstractMap.SimpleEntry<T, V>(t.getKey(), t.getValue()));
			inorderEntry(t.getRightChild(), set);
		}
		return set;
	}

	private Set<T> inorderKey(INode<T, V> t, Set<T> set) {
		if (!t.isNull()) {
			inorderKey(t.getLeftChild(), set);
			set.add(t.getKey());
			inorderKey(t.getRightChild(), set);
		}
		return set;
	}

	private ArrayList<Entry<T, V>> inorderHeapMap(INode<T, V> t, T toKey, ArrayList<Entry<T, V>> m, boolean inclusive) {
		if (!t.isNull()) {
			inorderHeapMap(t.getLeftChild(), toKey, m, inclusive);
			if (t.getKey().compareTo(toKey) < 0) {
				Entry<T, V> entry = new AbstractMap.SimpleEntry<T, V>(t.getKey(), t.getValue());
				m.add(entry);
			}
			if (inclusive && t.getKey().compareTo(toKey) == 0) {
				Entry<T, V> entry = new AbstractMap.SimpleEntry<T, V>(t.getKey(), t.getValue());
				m.add(entry);
			}
			inorderHeapMap(t.getRightChild(), toKey, m, inclusive);
		}
		return m;
	}
}

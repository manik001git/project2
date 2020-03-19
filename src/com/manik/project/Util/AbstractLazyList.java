//$Id$
package com.manik.project.Util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class AbstractLazyList<E> implements List<E>{

	protected List<E> delegates = null;
	protected abstract void hydrate();
	
	@Override
	public int size() {
		hydrate();
		return delegates.size();
	}

	@Override
	public boolean isEmpty() {
		hydrate();
		return delegates.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		hydrate();
		return delegates.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		hydrate();
		return delegates.iterator();
	}

	@Override
	public Object[] toArray() {
		hydrate();
		return delegates.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		hydrate();
		return delegates.toArray(a);
	}

	@Override
	public boolean add(E e) {
		hydrate();
		return delegates.add(e);
	}

	@Override
	public boolean remove(Object o) {
		hydrate();
		return delegates.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		hydrate();
		return delegates.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		hydrate();
		return delegates.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		hydrate();
		return delegates.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		hydrate();
		return delegates.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		hydrate();
		return delegates.retainAll(c);
	}

	@Override
	public void clear() {
		hydrate();
		delegates.clear();
	}

	@Override
	public E get(int index) {
		hydrate();
		return delegates.get(index);
	}

	@Override
	public E set(int index, E element) {
		hydrate();
		return delegates.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		hydrate();
		delegates.add(index, element);
	}

	@Override
	public E remove(int index) {
		hydrate();
		return delegates.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		hydrate();
		return delegates.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		hydrate();
		return delegates.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		hydrate();
		return delegates.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		hydrate();
		return delegates.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		hydrate();
		return delegates.subList(fromIndex, toIndex);
	}

}

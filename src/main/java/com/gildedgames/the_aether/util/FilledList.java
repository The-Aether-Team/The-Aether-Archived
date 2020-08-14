package com.gildedgames.the_aether.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

public class FilledList<E> extends AbstractList<E> {

	private final List<E> list;

	private E fillElement;

	@SuppressWarnings("unchecked")
	public FilledList(int size, E fillIn) {
		this.fillElement = fillIn;

		Object[] objectList = new Object[size];
		Arrays.fill(objectList, this.fillElement);

		this.list = (List<E>) Arrays.asList(objectList);
	}

	@Override
	public E set(int index, E element) {
		return this.list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		this.list.add(element);
	}

	@Override
	public E get(int index) {
		return this.list.get(index);
	}

	@Override
	public E remove(int index) {
		return this.list.remove(index);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.size(); ++i) {
			this.set(i, this.fillElement);
		}
	}

}
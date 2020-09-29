package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class RowIterator implements Iterator<Row> {

	private List<Row> rows;
	private int currentIdx;

	public RowIterator(List<Row> rows) {
		this.rows = rows;
		this.currentIdx = 0;
	}

	public Row next() {
		if (this.hasNext()) {
			Row element = rows.get(currentIdx);
			currentIdx++;
			return element;
		} else {
			throw new NoSuchElementException();
		}
	}

	public boolean hasNext() {
		return currentIdx < rows.size();
	}
}

package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An iterator class for iterating over a row's spaces (mainly for spark)
 */
public class RowIterator implements Iterator<Row> {

	private List<Row> rows;
	private int currentIdx;

	/**
	 * Creates a new row iterator instance
	 * @param rows
	 * 		The rows to make an iterator for
	 */
	public RowIterator(List<Row> rows) {
		this.rows = rows;
		this.currentIdx = 0;
	}

	/**
	 * Gets the next row and increments the iterator
	 */
	public Row next() {
		if (this.hasNext()) {
			Row element = rows.get(currentIdx);
			currentIdx++;
			return element;
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Returns if this iterator has another row to iterate to
	 */
	public boolean hasNext() {
		return currentIdx < rows.size();
	}
}

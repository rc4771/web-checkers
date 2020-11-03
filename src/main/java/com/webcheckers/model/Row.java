package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A row on a checkers board
 *
 * @author Mike White
 */
public class Row implements Iterable<Space> {
	private int index;
	private ArrayList<Space> spaces;

	/**
	 * Creates a new board row
	 *
	 * @param index The row index
	 * @param spaces The spaces in the row
	 */
	public Row(int index, ArrayList<Space> spaces) {
		this.spaces = spaces;
		this.index = index;
	}

	/**
	 * Creates a new board row
	 * @param row The row to copy
	 */
	public Row(Row row) {
		this.index = row.index;
		this.spaces = new ArrayList<>(8);

		for (int i = 0; i < 8; i++) {
			spaces.add(row.spaces.get(i).copy());
		}
	}

	/**
	 * Returns the list of spaces for this row
	 */
	public ArrayList<Space> getSpaces() {
		return spaces;
	}

	/**
	 * Gets this row's index
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Returns an iterator (mainly for spark) for this row's spaces
	 */
	public Iterator<Space> iterator() {
		return new SpaceIterator(spaces);
	}
}

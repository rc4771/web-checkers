package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A row on a checkers board
 */
public class Row implements Iterable<Space> {
	private int index;
	private ArrayList<Space> spaces;

	/**
	 * Creates a new board row
	 */
	public Row(int index, ArrayList<Space> spaces) {
		this.spaces = spaces;
		this.index = index;
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

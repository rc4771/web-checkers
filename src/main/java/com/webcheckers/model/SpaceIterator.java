package com.webcheckers.model;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An iterator that iterates of the spaces in a row on a board
 *
 * @author Mike White
 */
public class SpaceIterator implements Iterator<Space> {

	/** A list of spaces */
	private List<Space> spaces;

	/** The current index into the list */
	private int currentIdx;

	/**
	 * Creates a new iterator for a list of spaces
	 *
	 * @param spaces The spaces that should be in the iterator
	 */
	public SpaceIterator(List<Space> spaces) {
		this.spaces = spaces;
		this.currentIdx = 0;
	}

	/**
	 * Gets the next space in this iterator and increments the iterator
	 *
	 * @return The next space
	 */
	public Space next() {
		if (this.hasNext()) {
			Space element = spaces.get(currentIdx);
			this.currentIdx++;
			return element;
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Returns a boolean for if this iterator has a next element or not
	 *
	 * @return True if there's a next value
	 */
	public boolean hasNext() {
		return this.currentIdx < spaces.size();
	}
}

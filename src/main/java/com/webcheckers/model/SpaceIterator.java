package com.webcheckers.model;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SpaceIterator implements Iterator<Space> {
	private List<Space> spaces;
	private int currentIdx;

	public SpaceIterator(List<Space> spaces) {
		this.spaces = spaces;
		this.currentIdx = 0;
	}

	public Space next() {
		if (this.hasNext()) {
			Space element = spaces.get(currentIdx);
			this.currentIdx++;
			return element;
		} else {
			throw new NoSuchElementException();
		}
	}

	public boolean hasNext() {
		return this.currentIdx < spaces.size();
	}
}

package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Row implements Iterable<Space> {
	private int index;
	private ArrayList<Space> spaces;

	public Row(int index, ArrayList<Space> spaces) {
		this.spaces = spaces;
		this.index = index;
	}

	public ArrayList<Space> getSpaces() {
		return spaces;
	}

	public int getIndex() {
		return this.index;
	}

	public Iterator<Space> iterator() {
		return new SpaceIterator(spaces);
	}
}

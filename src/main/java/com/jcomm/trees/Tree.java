package com.jcomm.trees;

public interface Tree<T extends Comparable<T>> {
	
	int getCount();
	void insertValue(T val);
	void removeValue(T val);
	

}

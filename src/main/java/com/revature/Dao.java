package com.revature;

import java.util.List;

public interface Dao<E> {
	void insert(E e);
	
	List<E> getAll();
	
	void update();
	
	void delete();
}

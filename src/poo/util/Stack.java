package poo.util;

import java.util.Iterator;

public interface Stack<T> extends Iterable<T>{
	int size();
	default void clear() {
		Iterator<T> it=iterator();
		while( it.hasNext() ) {
			it.next();
			it.remove();
		}
	}//clear
	void push( T x );
	default T pop() {
		Iterator<T> it=iterator();
		T x=it.next();
		it.remove();
		return x;
	}//pop
	default T peek() {
		Iterator<T> it=iterator();
		T x=it.next();
		return x;		
	}//peek
	default boolean isEmpty() {
		return size()==0;
	}//isEmpty
	default boolean isFull() {
		return false;
	}//isFull
}//Stack

package poo.util;

import java.util.Iterator;

public interface Vector<T> extends Iterable<T>{//Esempio di Abstract Data Type o ADT
	//è possibile utilizzare l'iteratore anche se qui è astratto
	
	default int size() { //indici utili sono da 0 a size-1
		int c=0;
		for( T x: this ) c++;
		return c;
	}//size
	
	default void clear() {
		Iterator<T> it=iterator();
		while( it.hasNext() ) {
			it.next();
			it.remove();
		}
	}//clear
	
	default boolean contains( T x ) {
		for( T e: this )
			if( e.equals(x) ) return true;
		return false;
	}//contains
	
	default T get( int indice ) {
		if( indice<0 || indice >= size() )
			throw new IndexOutOfBoundsException();
		int i=-1;
		for( T e: this ) {
			i++;
			if( i==indice ) return e;
		}
	    return null; //mai eseguita
	}//get
	
	T set( int indice, T x ); //rimane astratto
	
	default void add( T x ) { //aggiunge/append x alla fine, incrementando size
		add( size(), x );
	}//add
	
	void add( int indice, T x ); //indice può valere da 0 a size
	
	default void remove( T x ) {
		Iterator<T> it=iterator();
		while( it.hasNext() ) {
			T e=it.next();
			if( e.equals(x) ) { it.remove(); break; }
		}
	}//remove
	
	default T remove( int indice ) {
		if( indice<0 || indice>=size() )
			throw new IndexOutOfBoundsException();
		int i=-1;
		Iterator<T> it=iterator();
		T e=null;
		while( it.hasNext() ) {
			i++;
			e=it.next();
			if( i==indice ) { it.remove(); return e; }
		}
		return null; //mai eseguita
	}//remove
	
	default int indexOf( T x ) {
		int i=-1;
		for( T e: this ) {
			i++;
			if( e.equals(x) ) return i;
		}
		return -1;	
	}//indexOf
	
	default boolean isEmpty() {
		return size()==0;
	}//isEmpty
	
	Vector<T> subVect( int da, int a ); //da<=a
	
}//Vector


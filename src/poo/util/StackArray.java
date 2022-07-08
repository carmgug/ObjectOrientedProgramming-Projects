package poo.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackArray<T> extends StackAstratto<T> {
	private T[] pila;
	private int size=0; //size è anche la cima/top dello stack
	
	@SuppressWarnings("unchecked")
	public StackArray( int n ) {
		if( n<1 ) throw new IllegalArgumentException();
		pila=(T[])new Object[n];
	}
	public StackArray() { this(17); }
	public int size() { return size; }
	public void push( T e ) {
		if( size==pila.length ) pila=java.util.Arrays.copyOf(pila,size*2);
		pila[size]=e;
		size++;
	}//push
	public T pop() {
		if( size==0 ) throw new NoSuchElementException();
		T x=pila[size-1];
		pila[size-1]=null;
		size--;
		return x;
	}//pop
	public T peek() {
		if( size==0 ) throw new NoSuchElementException();
		return pila[size-1];
	}//peek	
	
	public Iterator<T> iterator(){ return new StackArrayIterator(); }
	
	private class StackArrayIterator implements Iterator<T>{
		private int cor=size;
		private boolean rimuovibile=false;
		public boolean hasNext() {
			if( cor==size ) return size>0;
			return cor>0;
		}
		public T next() {
			if( !hasNext() ) throw new NoSuchElementException();
			cor--;
			rimuovibile=true;
			return pila[cor];
		}//next
		public void remove() {
			if( !rimuovibile ) throw new IllegalStateException();
			rimuovibile=false;
			//left shift
			for( int i=cor+1; i<size; ++i )
				pila[i-1]=pila[i];
			pila[size-1]=null;
			size--;
		}//remove
	}//StackArrayIterator
}//StackArray

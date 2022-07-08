package poo.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackConcatenato<T> extends StackAstratto<T>{
	private static class Nodo<E>{
		E info;
		Nodo<E> next;
	}
	private Nodo<T> first=null;
	private int size=0;
	public int size() { return size; }
	public void push( T x ) {
		Nodo<T> nuovo=new Nodo<>();
		nuovo.info=x;
		//inserimento in testa
		nuovo.next=first;
		first=nuovo;
		size++;
	}//push
	public T pop() {
		if( first==null ) throw new NoSuchElementException();
		T x=first.info;
		first=first.next;
		size--;
		return x;
	}//pop
	public T peek() {
		if( first==null ) throw new NoSuchElementException();
		T x=first.info;
		return x;
	}//pop	
	public Iterator<T> iterator(){ return new StackIterator(); }
	private class StackIterator implements Iterator<T>{
		private Nodo<T> pre=null, cor=null;
		public boolean hasNext() {
			if( cor==null ) return first!=null;
			return cor.next!=null;
		}
		public T next() {
			if( !hasNext() ) throw new NoSuchElementException();
			if( cor==null ) cor=first;
			else { pre=cor; cor=cor.next; }
			return cor.info;
		}//next
		public void remove() {
			if( pre==cor ) throw new IllegalStateException();
			if( cor==first ) first=first.next;
			else {pre.next=cor.next;}
			size--;
			cor=pre; //arretra cor
		}//remove
	}//StackIterator
	
}//StackConcatenato

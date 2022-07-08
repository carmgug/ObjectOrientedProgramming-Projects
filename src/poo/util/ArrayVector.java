package poo.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayVector<T> extends AbstractVector<T>{//ArrayVector esso stesso è iterabile
	
	private T[] array;
	private int size;
	
	@SuppressWarnings("unchecked")
	public ArrayVector( int capacita ) {
		if( capacita<=0 ) throw new IllegalArgumentException();
		array=(T[])new Object[capacita];
		size=0;
	}
	public ArrayVector() {
		this(50);
	}
	
	public ArrayVector( Vector<T> v ) {
		array=(T[]) new Object[v.size()];
		for( int i=0; i<v.size(); ++i ) 
			this.add( v.get(i) );
	}
	@Override
	public int size() { //ridefinito per ragioni di efficienza
		return size;
	}//size

/*
	public void clear() {
		for( int i=0; i<size; ++i ) array[i]=null;
		size=0;
	}//clear

	
	public boolean contains( T o ) {
		for( int i=0; i<size; ++i )
			if( array[i].equals(o) ) return true;
		return false;
	}//contains
	
	public T get( int indice ) {//array[indice]
		if( indice<0 || indice>=size ) throw new IndexOutOfBoundsException();
		return array[indice];
	}//get
*/
	
	public T set( int indice, T x ) {//array[indice]=x;
		if( indice<0 || indice>=size ) throw new IndexOutOfBoundsException();
		T y=array[indice];
		array[indice]=x;
		return y;
	}//set

/*
	public void add( T x ) {
		if( size==array.length )
			array=java.util.Arrays.copyOf( array, size*2 );
		array[size]=x;
		size++;
	}//add
*/
	
	public void add( int indice, T x ) {
		if( indice<0 || indice>size ) throw new IndexOutOfBoundsException();
		if( size==array.length )
			array=java.util.Arrays.copyOf( array, size*2 );
		//scorrimento a destra di un posto di tutti gli elementi da indice a size-1
		for( int i=size-1; i>=indice; --i )
			array[i+1]=array[i];
		array[indice]=x;
		size++;
	}//add
	
/*	
	public int indexOf( T x ) {
		for( int i=0; i<size; ++i )
			if( array[i].equals(x) ) return i;
		return -1;
	}//indexOf
	
	public void remove( T x ) {
		int i=indexOf(x);
		if( i==-1 ) return;
		remove(i);
	}//remove
	
	public T remove( int indice ) {
		if( indice<0 || indice>=size ) throw new IndexOutOfBoundsException();
		//scorrimento a sinistra di un posto di tutti gli elementi da indice+1 a size-1
		T y=array[indice];
		for( int i=indice+1; i<size; ++i )
			array[i-1]=array[i];
		array[size-1]=null;
		size--;
		if( size<array.length/2 ) //contrazione
			array=java.util.Arrays.copyOf(array,array.length/2);
		return y;
	}//remove
	
	public boolean isEmpty() {
		return size==0;
	}//isEmpty
*/
	
	public Vector<T> subVect( int da, int a ) {
		if( da<0||da>=size||a<0||a>=size||da>a ) throw new IndexOutOfBoundsException();
		ArrayVector<T> sub=new ArrayVector<>(a-da);
		for( int i=da; i<a; ++i ) sub.add( this.array[i] );
		return sub;
	}//subVect
	
	public Iterator<T> iterator(){
		return new VIterator();
	}//VIterator
	
	private class VIterator implements Iterator<T>{//inner class di ArrayVector
		private int cor=-1; //indice o freccia dell'iteratore
		//se cor non è -1 allora deve puntare ad un elemento già consumato
		private boolean rimuovibile=false;
		public boolean hasNext() {
			if( cor==-1 ) return size>0;
			return cor<size-1;
		}//hasNext
		public T next() {
			if( !hasNext() ) throw new NoSuchElementException();
			cor++;
			rimuovibile=true;
			return array[cor]; //si consuma l'elemento puntato da cor
		}//next
		public void remove() {
			if( !rimuovibile ) throw new IllegalStateException();
			rimuovibile=false;
			//togliere l'elemento puntato da cor
			for( int i=cor+1; i<size; ++i )
				array[i-1]=array[i];
			size--; array[size]=null;
			cor--;
		}
	}//VIterator
	
	
}//ArrayVector

package poo.util;

import java.util.Iterator;

public abstract class AbstractList<T> implements List<T>{
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		Iterator<T> it=iterator();
		while( it.hasNext() ) {
			sb.append( it.next() );
			if( it.hasNext() ) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}//toString
	
	@SuppressWarnings("unchecked")
	public boolean equals( Object o ) {
		if( !(o instanceof Vector) ) return false;
		if( o==this ) return true;
		List<T> v=(List<T>)o;
		if( this.size()!=v.size() ) return false;
		Iterator<T> i1=iterator(), i2=v.iterator();
		while( i1.hasNext() ) {
			T e1=i1.next();
			T e2=i2.next();
			if( !e1.equals(e2) ) return false;
		}
		return true;
	}//equals
	
	public int hashCode() {
		final int M=83;
		int h=0;
		for( T x: this )
			h=h*M+x.hashCode();
		return h;
	}//hashCode
	
}//AbstractList

package poo.util;

import java.util.Iterator;

public abstract class StackAstratto<T> implements Stack<T>{
	//mettere i metodi equals(), hashCode()
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
}//StackAstratto

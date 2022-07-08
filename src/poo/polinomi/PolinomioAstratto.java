package poo.polinomi;
import java.util.Iterator;

public abstract class PolinomioAstratto implements Polinomio{
	public String toString() {
		StringBuilder sb=new StringBuilder(300);
		Iterator<Monomio> it=this.iterator();
		boolean flag=true; //siamo sul primo monomio
		while( it.hasNext() ) {
			Monomio m=it.next();
			if( m.getCoeff()>0 && !flag ) sb.append('+');
			sb.append(m);
			if( flag ) flag=!flag;
		}
		return sb.toString();
	}//toString
	public boolean equals( Object x ) {
		if( !(x instanceof Polinomio) ) return false;
		if( x==this ) return true;
		Polinomio p=(Polinomio)x;
		if( this.size()!=p.size() ) return false;
		Iterator<Monomio> i1=this.iterator(), i2=p.iterator();
		while( i1.hasNext() ) {
			Monomio m1=i1.next();
			Monomio m2=i2.next();
			if( m1.getCoeff()!=m2.getCoeff() || m1.getGrado()!=m2.getGrado() ) 
				return false;
		}
		return true;
	}//equals
	public int hashCode() {
		final int M=83;
		int h=0;
		for( Monomio m: this )
			h=h*M+(String.valueOf(m.getCoeff())+String.valueOf(m.getGrado())).hashCode();
		return h;
	}//hashCode
}//Polinomio

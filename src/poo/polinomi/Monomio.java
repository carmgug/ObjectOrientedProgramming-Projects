package poo.polinomi;

public class Monomio implements Comparable<Monomio>{
	private final int COEFF, GRADO;
	public Monomio( final int coeff, final int grado ) {
		if( grado<0 ) throw new IllegalArgumentException();
		this.COEFF=coeff; this.GRADO=grado;
	}
	public Monomio( Monomio m ) {
		this.COEFF=m.COEFF;
		this.GRADO=m.GRADO;
	}
	public int getCoeff() { return COEFF; }
	public int getGrado() { return GRADO; }
	
	public Monomio add( Monomio m ) {
		if( !this.equals(m) ) throw new RuntimeException("Monomi non simili!");
		return new Monomio( COEFF+m.COEFF, GRADO );
	}//add
	
	public Monomio mul( int s ) {
		return new Monomio( COEFF*s, GRADO );
	}//mul
	
	public Monomio mul( Monomio m ) {
		return new Monomio( COEFF*m.COEFF, GRADO+m.GRADO );
	}//mul
	
	@Override
	public boolean equals( Object x ) {
		if( !(x instanceof Monomio) ) return false;
		if( x==this ) return true;
		Monomio m=(Monomio)x;
		return this.GRADO==m.GRADO;
	}//equals
	
	@Override
	public int hashCode() {
		return GRADO;
	}//hashCode
	
	public int compareTo( Monomio m ) {
		if( this.GRADO>m.GRADO ) return -1;
		if( this.equals(m) ) return 0;
		return 1;
	}//compareTo
	
	public String toString() {
		StringBuilder sb=new StringBuilder(30);
		if( COEFF==0 ) sb.append(0);
		else {
			if( COEFF<0 ) sb.append('-');
			if( Math.abs(COEFF)!=1 || GRADO==0 ) sb.append( Math.abs(COEFF) );
			if( COEFF!=0 && GRADO>0 ) sb.append('x');
			if( COEFF!=0 && GRADO>1 ) { sb.append('^'); sb.append(GRADO); }
		}
		return sb.toString();
	}//toString
	
	public static void main( String []args ) {
		Monomio m=new Monomio(3,0);
		Monomio m1=new Monomio(-4,2);
		Monomio m3=m.mul(m1);
		m3=m3.add(m1);
		m3=m3.mul(-1);
		Monomio m2=new Monomio(0,2);
		System.out.println("m="+m+" m1="+m1+" m2="+m2+" m3="+m3);
	}
	
}//Monomio

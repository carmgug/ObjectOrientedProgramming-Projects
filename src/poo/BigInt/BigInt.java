package poo.BigInt;


/**
 * Classe che implementa la possibilità di manipolare Interi Immutabili a precisione arbitraria. 
 * @author Carmelo Gugliotta
 *  
 */
public interface BigInt extends Comparable<BigInt>,Iterable<Integer> {
	default String value() {
		return this.toString();
	}//value
		
	default BigInt zero() {
		return factory(0);
	}//zero
	
	default BigInt uno() {
		return factory(1);
	}//uno
	
	
	
	
	default int lenght() {
		int c=0;
		for(Integer x:this) {c++;}
		return c;
	}//lenght
	
	default BigInt incr() {
		return this.add(uno());
	}//incr
	
	default BigInt decr() {
		if(this.compareTo(zero())<=0) {throw new  IllegalStateException();}
		return this.sub(uno());
	}//decr
	
	
	//mul basato su ADD -->Molto dispendioso.
	default BigInt mul(BigInt m) {
		BigInt c=factory(0);
		if(m.compareTo(c)<0 ){throw new IllegalArgumentException();}
		if(m.compareTo(c)==0) {return c;}
		BigInt ris=this;
		c=c.incr();
		while(c.compareTo(m)<0) {
			ris=this.add(ris);
			c=c.incr();
		}
		return ris;
	}//mul
	
	
	default BigInt pow(int exp) { 
		if(exp<0) {throw new IllegalArgumentException();}
		if(exp==0) {BigInt x=factory(1);return x;}
		if(exp==1) {return this;}
		int c=1;
		
		BigInt ris=this;
		do {
			ris=ris.mul(this);
			c++;
		}while(c<exp);
		
		return ris;
	}//pow
	
	default public BigInt rem(BigInt d) {
		BigInt Q=this.div(d);
		Q=Q.mul(d);
		BigInt R=this.sub(Q);
		
		return R;
	}//rem
	
	default BigInt div(BigInt d) {
		if(d.compareTo(zero())<=0) throw new IllegalArgumentException();
		if(this.compareTo(d)<0) throw new IllegalArgumentException();
		if(this.compareTo(d)==0) return uno();
		BigInt Q= factory(0);//quoziente
		BigInt R=this;//resto iniziale
		while(R.compareTo(d)>=0) {
			Q=Q.incr();
			R=R.sub(d);
		}
		return Q;
	}
	BigInt add(BigInt a);
	BigInt sub(BigInt b);
	BigInt factory(int x); //metodo factory di appoggio
}//BigInt

package poo.BigInt;

import java.util.Iterator;

public abstract class AbstractBigInt implements BigInt {

	@Override
	public String toString() {
		StringBuilder Sb=new StringBuilder(500);
		for(Integer x:this) {
			Sb.append(Integer.toString(x));
		}
		return Sb.toString();
	}//toString
	
	@Override
	public boolean equals(Object x) {
		if(!(x instanceof BigInt)) return false;
		if(x==this) return true;
		BigInt B=(BigInt) x;
		if(this.lenght()!=B.lenght()) return false;
		Iterator<Integer> i1=this.iterator(),i2=B.iterator();
		while(i1.hasNext()) {
			Integer k_1=i1.next();
			Integer k_2=i2.next();
			if(k_1!=k_2)return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int M=43;
		int h=0;
		for(Integer x:this) {
			h+=h*M+((String.valueOf(x)).hashCode());
		}
		return h;
	}
	
	
	
	@Override
	
	public int compareTo(BigInt x) {
		if(this.lenght()>x.lenght()) return 1;
		if(this.lenght()<x.lenght()) return -1;
		Iterator<Integer> i1=this.iterator(),i2=x.iterator();
		while(i1.hasNext()) {
			int k_1=i1.next();
			int k_2=i2.next();
			if(k_1>k_2) return 1;
			if(k_1<k_2) return -1;
		}
		return 0;
	}
	
	

}

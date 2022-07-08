package poo.polinomi;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class PolinomioSet extends PolinomioAstratto{
	private Set<Monomio> monomi=new TreeSet<>();
	
	public int size() { return monomi.size(); }
	public Iterator<Monomio> iterator(){ return monomi.iterator(); }
	public PolinomioSet crea() { return new PolinomioSet(); }
	
	public void add( Monomio m ) {
		if( m.getCoeff()==0 ) return;
		Iterator<Monomio> it=monomi.iterator();
		Monomio ms=null;
		while( it.hasNext() ) {
			Monomio mc=it.next();
			if( mc.equals(m) ) {
				it.remove();
				ms=mc;
				break;
			}
		}
		if( ms!=null ) {
			ms=ms.add(m);
			if( ms.getCoeff()!=0 ) monomi.add(ms);
		}
		else monomi.add(m);
	}//add
	
}//Polinomio

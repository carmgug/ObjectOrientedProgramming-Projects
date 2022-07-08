package poo.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;



public class ABR_Bilanciato<T extends Comparable<? super T>> implements CollezioneOrdinata<T>{
	private static class Nodo<E>{
		E info;
		Nodo<E> fS, fD;
		int cfs=0,cfd=0;
	}
	private Nodo<T> radice=null;
	private int ModCounter=0;
	
	
	public int size() {
		return size(radice);
	}//size
	private int size( Nodo<T> radice ) {
		if( radice==null ) return 0;
		return radice.cfd+radice.cfs+1;
	}//size
	
	public void clear() {
		radice=null;
	}//clear
	public boolean contains( T x ) {
		return contains(radice,x);
	}//contains
	private boolean contains( Nodo<T> radice, T e ) {
		if( radice==null ) return false;
		if( radice.info.equals(e) ) return true;
		if( radice.info.compareTo(e)>0 ) return contains( radice.fS, e );
		return contains( radice.fD, e );
	}//contains
	
	
	
	//---Metodi di ADD/REMOVE---//
	
	
	
	
	public void add( T x ) {
		if(contains(x)) throw new IllegalStateException();
		
		Stack<Nodo<T>> StackNodi_NB=new StackArray<Nodo<T>>();
		radice=add(radice,x,StackNodi_NB);
		
		if(!StackNodi_NB.isEmpty()) {reBuild(StackNodi_NB);}
		
		ModCounter++;
		
	}//add
	private Nodo<T> add( Nodo<T> radice, T x ,Stack<Nodo<T>> StackNodi_NB){
		if( radice==null ) {
			Nodo<T> n=new Nodo<>();
			n.info=x; n.fS=null; n.fD=null;
			n.cfd=0;n.cfs=0;
			return n;
		}
		if( radice.info.compareTo(x)>0 ) {
			radice.fS=add( radice.fS, x ,StackNodi_NB);
			radice.cfs+=1;
			if(isSbilanciato(radice)) StackNodi_NB.push(radice);
			return radice;
		}
		radice.fD=add( radice.fD, x ,StackNodi_NB);
		radice.cfd+=1;
		if(isSbilanciato(radice)) StackNodi_NB.push(radice);
		return radice;
	}//add
	
	public void remove( T x ) {
		if(!contains(x)) return;
		
		Stack<Nodo<T>> StackNodi_NB=new StackArray<Nodo<T>>();
		radice=remove( radice, x,StackNodi_NB );
		if(!StackNodi_NB.isEmpty()) reBuild(StackNodi_NB);
		
		ModCounter++;
		
	}//remove
	private Nodo<T> remove( Nodo<T> radice, T x,Stack<Nodo<T>> StackNodi_NB ){
		if( radice==null ) return null;
		if( radice.info.compareTo(x)>0 ) {
			radice.fS=remove( radice.fS,x ,StackNodi_NB);
			radice.cfs--;
			if(isSbilanciato(radice)) StackNodi_NB.push(radice);
			return radice;
		}
		if( radice.info.compareTo(x)<0 ) {
			radice.fD=remove( radice.fD, x ,StackNodi_NB);
			radice.cfd--;
			if(isSbilanciato(radice)) StackNodi_NB.push(radice);
			return radice;
		}
		//l'elemento è trovato in radice.info
		
		if( radice.fS==null && radice.fD==null ) {//nodo foglia
			return null;
		}
		if( radice.fS==null ) {//nodo col solo figlio destro
			return radice.fD;
		}
		if( radice.fD==null ) { //nodo col solo figlio sinistro
			return radice.fS;
		}
		//nodo puntato da radice, ha entrambi i figli
		if( radice.fD.fS==null ) {//1 sotto caso: la radice destra e' il minimo
			//promozione
			radice.info=radice.fD.info;
			
			radice.cfd--; //la cardinalità è pari al nodo promosso O ANCHE DIMINUITA DI UNO N.B FD.CFS=0
			//rimozione "vittima"
			
			radice.fD=radice.fD.fD; //fatto il bypass
			
			if(isSbilanciato(radice)) StackNodi_NB.push(radice);
			
			return radice;
		}
		//2 sotto caso più generale
		Nodo<T> padre=radice.fD; //radice del sotto albero destro
		Nodo<T> figlio=padre.fS;
		padre.cfs--;		
		while( figlio.fS!=null ) {
			padre=figlio; figlio=figlio.fS;
			padre.cfs--; //l'elemento da togliore è ancora più in fondo
		}
		//figlio punta alla vittima
		//promozione
		radice.info=figlio.info;
		radice.cfd--;
		//eliminare il nodo figlio, che al più ha un solo figlio
		padre.fS=figlio.fD; //bypass
		
		if(!isBilanciato(radice)) StackNodi_NB.push(radice);
		return radice;
	}//remove
	
	//---Metodi di supporto per Mantenere Bilanciato l'albero---//
	private void reBuild(Stack<Nodo<T>> StackNodi_NB) {
		Nodo<T> nc=StackNodi_NB.peek();
		ArrayVector<T> a=new ArrayVector<>(10);
		
		inOrder(nc,a);
		
		nc.fD=null;nc.fS=null;
		nc.cfd=0;nc.cfs=0;
		
		nc=reBuild(a,0,a.size()-1,nc);
		
	}//reBuild
	private Nodo<T> reBuild(Vector<T> v,int inizio,int fine,Nodo<T> nc) {
		int med;
		if(inizio>fine) {
			return null;
		}
		else {
			
			//assegnazione valore al nc
			med=(inizio+fine)/2;
			nc.info=v.get(med);
			
			
			//gestione nodo destro
			nc.fD=new Nodo<T>();
			nc.fD=reBuild(v,med+1,fine,nc.fD);
			//gestione nodo sinistro
			nc.fS=new Nodo<T>();
			nc.fS=reBuild(v,inizio,med-1,nc.fS);
			
			nc.cfd=fine-med;
			nc.cfs=med-inizio;
			
			return nc;
		}
	}//reBuild
	
	
	
	
	//---Metodi di visita tramite Vector---//
	public void inOrder(Vector<T> v) {
		inOrder(radice,v);
	}
	private void inOrder(Nodo<T> radice,Vector<T> v) {
		if( radice!=null ) {
			inOrder( radice.fS, v );
			v.add( radice.info ); //visita la radice
			inOrder( radice.fD, v );
		}
		
	}//inOrder
	
	//---METODI DI VISITA TRAMITE LISTE---//
	public void inOrder( List<T> lis ) {
		inOrder( radice, lis );
	}//inOrder
	private void inOrder( Nodo<T> radice, List<T> lis ) {
		if( radice!=null ) {
			inOrder( radice.fS, lis );
			lis.addLast( radice.info ); //visita la radice
			inOrder( radice.fD, lis );
		}
	}//inOrder
	
	public void preOrder( List<T> lis ) {//visita in ordine anticipato
		preOrder(radice,lis);
	}
	private void preOrder( Nodo<T> radice, List<T> lis ) {
		if( radice!=null ) {
			lis.addLast(radice.info); //prima la radice
			preOrder( radice.fS, lis );
			preOrder( radice.fD, lis );
		}
	}
	
	public void postOrder( List<T> lis ) {//visita in ordine posticipato
		postOrder( radice, lis );
	}
	private void postOrder( Nodo<T> radice, List<T> lis ) {
		if( radice!=null ) {
			postOrder( radice.fS, lis );
			postOrder( radice.fD, lis );
			lis.addLast( radice.info );
		}
	}
	
	
	public T get( T x ) {
		if(x==null) throw new IllegalArgumentException();
		return get( radice,x );
	}//get
	private T get( Nodo<T> radice, T e ) {
		if( radice==null ) return null;
		if( radice.info.equals(e) ) return radice.info;
		if( radice.info.compareTo(e)>0 ) return get( radice.fS, e );
		return get( radice.fD, e );
	}//get
	
	public boolean isEmpty() { return radice==null; }
	public boolean isFull() { return false; } //Gestire in caso si voglia implementare una size massima
	
	
	
	//---ITERATORE SU STACK---//
	public Iterator<T> iterator(){ return new ABRIterator_Stack(); }
	
	private class ABRIterator_Stack implements Iterator<T>{
		private Stack<Nodo<T>> Stack_ABR=new StackArray<>();
		
		private Nodo<T> nc;
		private int ModCounterMirror=ModCounter;
	
		
		
		public ABRIterator_Stack() { 
			BuildStack(radice);
		}
		private void BuildStack(Nodo<T> radice) {
			if(radice==null) return;
			Stack_ABR.push(radice);
			BuildStack(radice.fS);
			
		}
		
		public boolean hasNext() { return !Stack_ABR.isEmpty(); }
		
		public T next() {
			nc=Stack_ABR.pop(); //Potrebbe lanciare un eccezzione
			
			if(nc.fD!=null) {
				BuildStack(nc.fD);
			}
			
			return nc.info; 
		}
		public void remove() {
			if(ModCounterMirror!=ModCounter) throw new ConcurrentModificationException();
			if(nc==null) throw new IllegalStateException();
			
			T valore_nc=nc.info;
			//il nodo durante la remove può subire promozioni e quindi il
			//suo valore interno può mutare, per evitare ciò conserviamo il suo valore,all'interno
			//di una variabile locale.
			
			ABR_Bilanciato.this.remove(valore_nc);//la remove va fatta anche sull'albero...
			
			//Puliamo lo stack e lo ricostruiamo
			Stack_ABR.clear();
			reBuildStack(radice,valore_nc);
			
			ModCounter++;
			ModCounterMirror++;
			nc=null;
		}//remove
		
		private void reBuildStack(Nodo<T> radice,T valore_nc) {
			if(radice==null) return;
			if(radice.info.compareTo(valore_nc)>=0) {
				Stack_ABR.push(radice);
				reBuildStack(radice.fS,valore_nc);
			}
			else
				reBuildStack(radice.fD,valore_nc);
		}//reBuildStack
		
		
	}//ABRIterator_Stack
	
	
	
	
	
	
	public String toString() {
		StringBuilder sb=new StringBuilder(100);
		sb.append('[');
		Iterator<T> it=this.iterator();
		while( it.hasNext() ) {
			
			sb.append( it.next()+" ");
			if( it.hasNext() ) sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}//toString
	
	public boolean equals( Object x ) {
		if( !(x instanceof ABR_Bilanciato) ) return false;
		if( x==this ) return true;
		ABR_Bilanciato<T> abr=(ABR_Bilanciato<T>)x;
		return equals( this.radice, abr.radice );
	}//equals
	
	private boolean equals( Nodo<T> r1, Nodo<T> r2 ) {
		if( r1==null && r2==null ) return true;
		if( r1==null || r2==null ) return false;
		if( !r1.info.equals(r2.info) ) return false;
		return equals( r1.fS, r2.fS ) && equals( r1.fD, r2.fD );
	}//equals
	
	
	//---Shallow-Copy---//
	public ABR_Bilanciato<T> copy(){
		ABR_Bilanciato<T> ABR_copia=new ABR_Bilanciato<T>();
		ABR_copia.radice=copy(radice,ABR_copia.radice);
		return ABR_copia;
	}//copy
	private Nodo<T> copy(Nodo<T> radice,Nodo<T> radice_copia){
		if (radice==null) return null;
		radice_copia=new Nodo<T>();
		radice_copia.info=radice.info;
		radice_copia.cfd=radice.cfd;
		radice_copia.cfs=radice.cfs;
		radice_copia.fD=copy(radice.fD,radice_copia.fD);
		radice_copia.fS=copy(radice.fS,radice_copia.fS);
		return radice_copia;
	}
	
	
	
	
	
	
	
	
	public boolean isBilanciato() {
		return isBilanciato( radice );
	}//bilanciato
	private boolean isBilanciato( Nodo<T> radice ) {
		if( radice==null ) return true;
		int s1=radice.cfs;
		int s2=radice.cfd;
		if( Math.abs(s1-s2)>1 ) return false;
		return isBilanciato(radice.fS) && isBilanciato(radice.fD);
	}//bilanciato
	
	private boolean isSbilanciato(Nodo<T> radice) {   //Nodo sbilanciato? TRUE SI;FALSE NO
		if(Math.abs(radice.cfd-radice.cfs)>1) return true;
		return false;
		
	}
	
	public static void main( String[] args ) {
		
		
		
		
		
		
		
		
		
		
		
		
		
		ABR_Bilanciato<Integer> abr=new ABR_Bilanciato<>();
		abr.add(12); abr.add(-4); abr.add(5);
		abr.add(1);
		abr.add(7);
		abr.add(38); abr.add(-6); abr.add(8);
		System.out.println(abr);
		System.out.println("RADICE:"+abr.radice.info);
		System.out.println("cfs,cfd : "+abr.radice.cfs+","+abr.radice.cfd);
		
		System.out.println("DEBUG OPERAZIONI:");
		
		Iterator<Integer> it=abr.iterator();
		int i=0;
		while(it.hasNext() && i<5) {
			System.out.println(it.next());
			i++;
		}
		System.out.println(it.next()+"Elemento rimosso");
		it.remove();
		while(it.hasNext()) {
			System.out.println(it.next());
			
		}
		
		System.out.println(abr);
		System.out.println("RADICE:"+abr.radice.info);
		System.out.println("cfs,cfd : "+abr.radice.cfs+","+abr.radice.cfd);
		
		
		
	}
	
	
}//ABR

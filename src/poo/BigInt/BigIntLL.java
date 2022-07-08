package poo.BigInt;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.math.BigInteger;

public class BigIntLL extends AbstractBigInt{
	private LinkedList<Integer> lista=new LinkedList<>();
	
	
	
	//Costruttore
	
	public BigIntLL(int x) {
		if(x<0) throw new IllegalArgumentException();
		String S=String.valueOf(x);
		for(int i=0;i<S.length();++i) {
			Integer k=Integer.parseInt(String.valueOf(S
					.charAt(i)));
			lista.add(k);
		}
	}//Costruttore BigIntLL
	
	private BigIntLL() {} // metodo che serve di appoggio per creare un bigIntLL con lista vuota;
	
	public BigIntLL(String x) {
		if(x.charAt(0)=='+' || x.charAt(0)=='-') throw new IllegalArgumentException();
		try {
			boolean flag=false;
			for(int i=0;i<x.length();++i) {
				Integer k=Integer.parseInt(String.valueOf(x.charAt(i)));
				if(k==0 && flag==false) {continue;} //non aggiunge gli 0 prima di un possibile numero !=0 000014-->14
				if(k!=0 && flag==false) flag=true; //dopo aver incontrato un numero tieni conto dei prossimi 0
				lista.add(k);
			}
		}catch(NumberFormatException exp){ //se parseInt non riesce a convertire la stringa di un solo carattere in intero lancia l'errore NumberFormatException noi lo catturiamo e lo mandiamo all'utente;
			System.out.println(exp);
			System.out.println("Ricorda che la stringa deve essere formata solo da caratteri compresi tra 0 e 9");
			System.exit(-1); //chiudiamo il programma
		}
	}//Costruttore BigIntLL
	
	
	
	//Corretto
	@Override
	public BigInt div(BigInt d)  {
		if(d.compareTo(zero())<=0) throw new IllegalArgumentException();
		if(this.compareTo(d)<0) throw new IllegalArgumentException();
		if(this.compareTo(d)==0) return uno();
		
		BigIntLL Q= new BigIntLL(0);//quoziente
		BigIntLL Dividendo=this;
		BigIntLL Divisore=(BigIntLL)d;
		BigIntLL tempA, tempB, counter;
		
		//Ora calcoliamo il Quoziente sempre sulla base delle differenze, ma ottimizziamo il tutto
		//trovando un numero abbastanza grande tenendo il conto del quoziente
		
		while(Dividendo.compareTo(Divisore)>=0) {
			tempA=(BigIntLL)Dividendo.div2();
			tempB=Divisore;
			counter=(BigIntLL)uno();
			while(tempA.compareTo(tempB)>=0) {
				tempB=(BigIntLL)tempB.mul(factory(2));
				counter=(BigIntLL)counter.mul(factory(2));
			}//while
			
			Dividendo=(BigIntLL)Dividendo.sub(tempB);
			
			Q=(BigIntLL)Q.add(counter);
		}//while
		return Q;
		
	}//div
	
	//Corretto
	@Override
	public BigInt add(BigInt a) {
		BigIntLL Addendo=(BigIntLL)a;
		BigIntLL ris=new BigIntLL();
		int resto=0;
		
		ListIterator<Integer> i1=this.listIterator(this.lenght());
		ListIterator<Integer> i2=Addendo.listIterator(a.lenght());
		
		while(i1.hasPrevious() && i2.hasPrevious()) {
			int k_1=i1.previous();
			int k_2=i2.previous();
			int somma=k_1+k_2+resto;
			ris.lista.addFirst((somma)%10);
			if(somma>=10) {resto=1;}
			else {resto=0;}
		}//while
		
		if(i1.hasPrevious() || i2.hasPrevious()) {
			while(i1.hasPrevious()) {
				int k_1=i1.previous();
				int somma=k_1+resto;
				ris.lista.addFirst(somma%10);
				if(somma>=10) {resto=1;}
				else {resto=0;}
			}//while i1
			while(i2.hasPrevious()) {
				int k_1=i2.previous();
				int somma=k_1+resto;
				ris.lista.addFirst(somma%10);
				if(somma>=10) {resto=1;}
				else {resto=0;}
			}//while i2
			
		}//if
		
		if (resto==1) {ris.lista.addFirst(1);}
		return ris;
		
	}//Add
	
	
	@Override
	public BigInt sub(BigInt b) {
		if(this.compareTo(b)<0) {throw new IllegalArgumentException();}
		if(this.compareTo(b)==0) {return factory(0);}
		BigIntLL Sottraendo=(BigIntLL)b;
		int p=0; //Prestito dell'unità
		BigIntLL Differenza=new BigIntLL();
		
		ListIterator<Integer> i1=this.listIterator(this.lenght());
		ListIterator<Integer> i2=Sottraendo.listIterator(b.lenght());
		
		//il controllo di i1.hasPrevious è superfluo poichè this.lenght>=b.lenght
		while(i2.hasPrevious()) {
			int k_1=i1.previous()-p;
			int k_2=i2.previous();
			if(k_1==-1) {k_1=9;p=1;Differenza.lista.addFirst(k_1-k_2);continue;}//passa all'unità successivo e il resto viene addebbitato a quest'ultima
			if(k_1<k_2) {Differenza.lista.addFirst((k_1+10)-k_2);p=1;}
			else {Differenza.lista.addFirst((k_1-k_2));p=0;}
		}
		
		//il sottraendo non può avere in alcun caso più cifre del minunendo quindi controlliamo solo quest'ultimo ulteriormente
		while(i1.hasPrevious()) {
			int k_1=i1.previous();
			if(k_1==0 && p==1) {k_1=9;p=1;Differenza.lista.addFirst(k_1);}
			else{Differenza.lista.addFirst(k_1-p);if(p==1){p=0;}}
		}
		
		rimuovi_0(Differenza.lista); //eliminiamo eventuali 0 superflui che si sono andati a sviluppare successivamante alla differenza all'inizio del BIGINT
		return Differenza;
	}//sub
	
	//Corretto
	
	@Override
	public BigInt mul(BigInt b){
		if(this.compareTo(factory(0))==0 || b.compareTo(factory(0))==0) return zero();
		BigIntLL F_1,F_2;
		//scegliamo come secondo fattore sempre quello con meno cifre così da effettuare meno add durante l'esecuzione della moltiplicazione
		if(b.lenght()<=this.lenght()) {F_2=(BigIntLL) b;F_1= this;} 
		else { F_1=(BigIntLL) b;F_2= this;}
		
		BigIntLL Prod=new BigIntLL(0); //Prodotto
		ListIterator<Integer> i2=F_2.listIterator(F_2.lista.size());
		int cont=0;
		
		while(i2.hasPrevious()) {
			int p_2=i2.previous();
			if(p_2==0) {cont++;continue;} //se è 0 saltiamo la riga direttamente perchè dovremo aggiungere 0 a Prod;
			BigIntLL temp=new BigIntLL();
			int R=0;//riporto
			
			ListIterator<Integer> i1=F_1.listIterator(F_1.lista.size());
			while (i1.hasPrevious()) {
				int p_1=i1.previous();
				int x=(p_1*p_2)+R;
				temp.lista.addFirst(x%10);
				if(x>=10) {R=(x/10);}
				else{R=0;}
			
			}//while i1
			if(R!=0) {temp.lista.addFirst(R);}
			aggiungi_0(cont,temp); //aggiungiamo tanti 0 quanto è il valore del contatore {BigInt * 10^cont}
			Prod=(BigIntLL)Prod.add(temp);
			cont++;
		}//while i2
		
		return Prod;
	}//mul

	@Override
	public BigInt factory(int x) {
		BigIntLL N_BigLL=new BigIntLL(x);
		return N_BigLL;
	}//factory
	
	@Override
	public Iterator<Integer> iterator(){
		return lista.iterator();
	}//iterator
	
	//METODI DI APPOGGIO
	
	/*	un caso semplice di divisione in colonna è la divisione per 2
	 *	questo algoritmo ci permettera di dividere con poco costo temporale qualsiasi numero per N
	 * 	La divisione per due da implementare è molto semplice poichè il resto può essere o 0 o 1;
	 * 	da 0 a 9 se pari 0 se dispari resto 1
	 */
	private BigInt div2() {
		BigIntLL Risultato= new BigIntLL();
		int riporto=0;
		for(int x:this){
			x=x+riporto;
			if(x%2==0) {riporto=0;}
			else {riporto=10;}
			x=x/2;
			Risultato.lista.add(x);
		}
		Risultato.rimuovi_0(Risultato.lista);
		return Risultato;
	}//div2

	
	private void rimuovi_0(LinkedList<Integer> x) {
		boolean flag=false;
		Iterator<Integer> it=x.iterator();
		while(it.hasNext() && !flag) {
			int k=it.next();
			if(k==0 && flag==false) {it.remove();}
			if(k!=0 && flag==false) flag=true;
		}
	}//rimuovi_0
	private void aggiungi_0(int cont,BigIntLL L) {
		while (cont>0) {
			L.lista.addLast(0);
			cont--;
		}
	}//aggiungi_0
	private ListIterator<Integer> listIterator(int indice) {
		return lista.listIterator(indice);
	}//listIterator
	
	//Main Di prova
	public static void main(String...args) {
		
		
		BigInteger b=new BigInteger("2");
		long inizio_j = System.currentTimeMillis();
		BigInteger p=b.pow(128);
		long fine_j = System.currentTimeMillis();
		BigIntLL a=new BigIntLL("2");
		long inizio_m = System.currentTimeMillis();
		BigInt p_2=a.pow(128);
		long fine_m = System.currentTimeMillis();
		
		System.out.println("Risultati Potenza: ");
		System.out.println("BigInteger 2^128: "+p);
		System.out.println("BigIntLL   2^128: "+p_2);
		System.out.print("Sono Identici? : ");
		System.out.println(String.valueOf(p_2).compareTo(String.valueOf(p)));
		System.out.println("1 O -1: NO "+"\n"+ "0: SI" );
		System.out.println("Tempo di esecuzione BigInteger in millisecondi: "+(fine_j-inizio_j));
		System.out.println("Tempo di esecuzione BigIntLL   in millisecondi: "+(fine_m-inizio_m));
		
	}//main
	
}//BigIntLL

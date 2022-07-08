package poo.agendina;

import java.util.Iterator;
import poo.util.ABR_Bilanciato;

public class AgendinaABR_Bilanciato extends AgendinaAstratta {

	ABR_Bilanciato<Nominativo> rubrica=new ABR_Bilanciato<Nominativo>();
	
	public int size() {return rubrica.size();}
	
	public void svuota() {rubrica.clear();}
	
	
	@Override
	public void aggiungi(Nominativo n) {
		rubrica.remove(n);
		rubrica.add(n);
	}

	@Override
	public Iterator<Nominativo> iterator() {
		return rubrica.iterator();
	}
	
	
	
	public static void main( String[] args ) {
		AgendinaABR_Bilanciato rubrica=new AgendinaABR_Bilanciato();
		
		Nominativo n=new Nominativo("GUGLIOTTA","CARMELO","+39","1321");
		Nominativo n_2=new Nominativo("TASSONE","ALESSANDRO","+39","321412");
		Nominativo n_3=new Nominativo("GUGLIOTTA","FLAVIA","+39","34353454");
		Nominativo n_4=new Nominativo("GUGLIOTTA","ROBERTA","+39","453453");
		Nominativo n_5=new Nominativo("PEZZETTA","EMANUELE","+39","553453453");
		Nominativo n_6=new Nominativo("PEZZETTA","ALESSANDRO","+39","6453534");
		Nominativo n_7=new Nominativo("PEZZETTA","SARA","+39","73454353");
		Nominativo n_8=new Nominativo("VILLIRILLO","VITTORINA","+39","8534534");
		
		rubrica.aggiungi(n);rubrica.aggiungi(n_2);rubrica.aggiungi(n_3);
		rubrica.aggiungi(n_4);rubrica.aggiungi(n_5);rubrica.aggiungi(n_6);
		rubrica.aggiungi(n_7);rubrica.aggiungi(n_8);
		System.out.println("Stato rubrica prima di effettuare modifiche: ");
		System.out.println(rubrica);
		
		Iterator<Nominativo> it =rubrica.iterator();
		int i=0;
		while(it.hasNext() && i<5) {
			System.out.println(it.next()+" --> Elemento consumato dall'iteratore");
			i++;
		}
		System.out.println(it.next()+ " --> Elemento che verrà rimosso");
		it.remove();
		while(it.hasNext()) {
			System.out.println(it.next()+" --> Elemento consumato dall'iteratore");
		}
		
		
		
		
		System.out.println();
		System.out.println("Stato rubrica dopo aver effettuato le modifiche: ");
		System.out.println(rubrica);
		
	}


}

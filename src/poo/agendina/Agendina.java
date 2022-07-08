package poo.agendina;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public interface Agendina extends Iterable<Nominativo>{
	default int size() {
		int c=0;
		for( Nominativo x: this ) c++;
		return c;
	}
	default void svuota() {
		Iterator<Nominativo> it=this.iterator();
		while( it.hasNext() ) {
			it.next();
			it.remove();
		}
	}
	void aggiungi( Nominativo n );
	default void rimuovi( Nominativo n ) {
		Iterator<Nominativo> it=this.iterator();
		while( it.hasNext() ) {
			Nominativo q=it.next();
			if( q.equals(n) ){ it.remove(); break; }
			if( q.compareTo(n)>0 ) return;
		}		
	}
	default Nominativo cerca( Nominativo n ) {
		for( Nominativo x: this ) {
			if( x.equals(n) ) return x;
			if( x.compareTo(n)>0 ) return null;
		}
		return null;
	}
	default Nominativo cerca( String prefisso, String telefono ) {
		for( Nominativo x: this )
			if( x.getPrefisso().equals(prefisso) && x.getTelefono().equals(telefono) ) return x;
		return null;
	}
	default void salva( String nomeFile ) throws IOException{
		PrintWriter pw=new PrintWriter( new FileWriter(nomeFile) );
		for( Nominativo n: this ) pw.println(n);
		pw.close();
	}
	default void ripristina( String nomeFile ) throws IOException{
		BufferedReader br=new BufferedReader( new FileReader(nomeFile) );
		this.svuota();
		for(;;) {
			String linea=br.readLine();
			if( linea==null ) break;
			StringTokenizer st=new StringTokenizer(linea," -");
			String cog=st.nextToken(), nom=st.nextToken(), pre=st.nextToken(), tel=st.nextToken();
			this.aggiungi( new Nominativo(cog,nom,pre,tel) );
		}
		br.close();
	}

}//Agendina

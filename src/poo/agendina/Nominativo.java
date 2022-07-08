package poo.agendina;

public class Nominativo implements Comparable<Nominativo>{
	private String cognome, nome, prefisso, telefono;
	public Nominativo( String cognome, String nome, String prefisso, String telefono ) {
		this.cognome=cognome; this.nome=nome; this.prefisso=prefisso; this.telefono=telefono;
	}
	public String getCognome() { return cognome; }
	public String getNome() { return nome; }
	public String getPrefisso() { return prefisso; }
	public String getTelefono() { return telefono; }
	public int compareTo( Nominativo n ) {
		if( this.cognome.compareTo(n.cognome)<0 ||
		    this.cognome.equals(n.cognome) && this.nome.compareTo(n.nome)<0 ) return -1;
		if( this.cognome.equals(n.cognome) && this.nome.equals(n.nome) ) return 0;
		return 1;
	}//compareTo
	public boolean equals( Object o ) {
		if( !(o instanceof Nominativo) ) return false;
		if( o==this ) return true;
		Nominativo n=(Nominativo)o;
		return this.cognome.equals(n.cognome) && this.nome.equals(n.nome);
	}//equals
	public int hashCode() {
		final int M=83;
		int h=cognome.hashCode()*M+nome.hashCode();
		return h;
	}//hashCode
	public String toString() {
		return cognome+" "+nome+" "+prefisso+"-"+telefono;
	}//toString
}//Nominativo

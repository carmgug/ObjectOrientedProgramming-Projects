package poo.EspressioneAritmetica;

import java.util.Scanner;
import java.util.StringTokenizer;

import poo.util.*;


//Operandi sono interi senza segno.Gli operatori sono
//+-*%^ E VALGONO LE USUALI PRECEDENZA DELLA MATEMATICA


//Si usano due stack: 
/* il primo è uno stack di operandi
 * il secondo è uno stack di caratteri operatori
 * 
 * Qunado arriva un operando lo si inserisce in cima
 * allo stack di operandi.
 * 
 * Qunado arriva un operatore,sia esso opc si procede come segue:
 * 
 * A): se opc è più prioritario dell'operatore affiorante dello stack di operatori
 * o tale stack è vuoto, si inserisci opc in cima allo stack degli operatori.
 * 
 * B)se opc non è prioritario rispetto alla cima dello stack operatori, si preleva op al top dello stack operatori, quindi si preleva l'operatore op
 * 	al top dello stack operatori, quindi si prelevano due operandi o2(top) e o1(top-1) dello stack operandi(se vi sono eccezzioni l'espressione è malformata.SI ESEGUE O1 OP O2
 *  il risultato è inserito in cima allo stack operandi 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


public final class EspressioneAritmetica {
	
	
	private final static String Segno="[\\+\\-\\%\\*\\/\\^]";
	
	
	private final static String Regex="(\\d+|([\\+\\-\\%\\*\\/\\^\\(\\)]))+";
	
	
	
	
	
	private EspressioneAritmetica() {};
	
	public static int Risolvi(String Espressione) {
		if(!Espressione.matches(Regex)) throw new IllegalArgumentException();
		StringTokenizer st=new StringTokenizer(Espressione,"+-*%/^()",true);
		return valutaEspressione(st,true);
	}
	
	private static int  valutaEspressione(StringTokenizer st,boolean flag) {
		StackConcatenato<String> Operatori=new StackConcatenato<>();
		StackConcatenato<Integer> Operandi=new StackConcatenato<>();
		
		
		while(st.hasMoreTokens()) {
			String x=st.nextToken();
			if(x.matches("[\\d]+")) {Operandi.push(Integer.parseInt(x));}
			else if(x.matches(Segno)) {valutaOperando(x,Operatori,Operandi);}
			else if(x.matches("[\\(]")){Operandi.push(valutaEspressione(st,false));}
			else if(x.matches("[\\)]")) {if(flag!=false) throw new IllegalStateException();// se la flag è già true allora non corrisponde a una parentesi aperta;
										flag=true;break;}//Abbiamo trovato una parentesi chiusa che corrisponde a una aperta,
														//nessun problema di malformazione,usciamo dal ciclo poichè 
														//la seguente sessione di calcolo è terminata
			
		}
		while(!Operatori.isEmpty()) {
			String op=Operatori.pop();
			int o2=Operandi.pop(),o1=Operandi.pop();
			EseguiOperazione(o1,o2,op,Operandi);
		}
		if((Operatori.isEmpty() && Operandi.size()>1) || flag==false ) throw new IllegalStateException(); /*Se la flag==false a una "(" non corrisponde una ")" --> Espressione malformata*/		
		return Operandi.pop();
		
		
	}
	
	
	private static void valutaOperando(String opc,StackConcatenato<String> Operatori, StackConcatenato<Integer> Operandi) {
		if(Operatori.isEmpty()) {Operatori.push(opc); return;}
		while(!Operatori.isEmpty() && Compara(opc,Operatori.peek())<=0) {
			String op=Operatori.pop();
			int o2=Operandi.pop(),o1=Operandi.pop();
			EseguiOperazione(o1,o2,op,Operandi);
			}
		Operatori.push(opc); 
	}
	
	private static void EseguiOperazione(int o1,int o2,String op, StackConcatenato<Integer> Operandi) {
		switch(op){
		case "+" :Operandi.push(o1+o2);break;
		case "-" :Operandi.push(o1-o2);break;
		case "%" :Operandi.push(o1%o2);break;
		case "/" :Operandi.push(o1/o2);break;
		case "*" :Operandi.push(o1*o2);break;
		default: Operandi.push((int) Math.pow(o1, o2));
		}
		
	}
	
	
	private static int Compara(String x,String x1) {
		if(x.equals(x1)) return 0;
		if(x.matches("[\\+\\-]") && !(x1.matches("[\\-]"))) return -1;
		if(x.matches("[\\/\\%]") && !(x1.matches("[\\%\\+\\-]"))) return -1;
		if(x.matches("[\\*]") && !(x1.matches("[\\/\\%\\+\\-]")))return -1;
		return 1;
	}
	
	
	
	
	
	
	//-----MAIN DI PROVA-----//
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("EspressioneAritmetica: PREMI INVIO" );
		sc.nextLine();
		for(;;) {
			System.out.print(">> ");
			String espressione=sc.nextLine();
			if(espressione.equals(".")){break;}
			System.out.println(Risolvi(espressione));
		}
		sc.close();
		
		
	}
	
	
	

}

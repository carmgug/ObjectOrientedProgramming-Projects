package poo.util;


import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poo.polinomi.Polinomio;
import poo.polinomi.*;
public final class RegexPolinomi {
	
	final static String Numero="[0-9]+";
	final static String Grado="(x(\\^[0-9]+)?)";
	
	final static String Monomio="((\\-?"+Numero+Grado+"?)|(\\-?"+Grado+"))";
	final static String Regex="("+Monomio+"([\\-\\+]"+Numero+"("+Grado+")?|([\\+\\-]"+Grado+"))*)";
	
	final static Pattern p_P=Pattern.compile(Monomio);
	
	private RegexPolinomi(){}
	
	public static Polinomio ConvertToPoli(String polinomio_inserito,String Type){
		if(!polinomio_inserito.matches(Regex)) throw new IllegalArgumentException();
		Matcher m=p_P.matcher(polinomio_inserito);
		Polinomio P;
		switch (Type) {
		case "Set": P=new PolinomioSet(); break;
		case "LL": P=new PolinomioLL(); break;
		default: System.out.println("Tipo non riconoscito!"); throw new IllegalArgumentException(); 
		}
		return Convertitore(m,P);
	}
	
	
	private static Polinomio Convertitore(Matcher m,Polinomio P) {
		while(m.find())  {
			String MonomioTrovato=m.group();
			String COEFF="1",GRADO="0";
			if(MonomioTrovato.contains("x")) {GRADO="1";}
			
			StringTokenizer st=new StringTokenizer(MonomioTrovato,"x");
			while(st.hasMoreTokens()) {
				String curr=st.nextToken();
				if(curr.contains("^")) {GRADO=curr.substring(1);}//la stringa meno il cappuccio
				else if (curr.contains("-") && curr.length()==1) {COEFF="-1";}
				else COEFF=curr;
			}
			P.add(new Monomio(Integer.parseInt(COEFF),Integer.parseInt(GRADO)));
		}
		return P;
	}
}


/*
 * StringTokenizer st=new StringTokenizer(MonomioTrovato,"x^");
				String COEFF="0",GRADO_="0";
				if(MonomioTrovato.contains("x")) GRADO_="1";
				while(st.hasMoreElements()) {
					COEFF=st.nextToken();
					if(st.hasMoreElements()) GRADO_=st.nextToken();
				}
				Poli.add(new Monomio(Integer.parseInt(COEFF),Integer.parseInt(GRADO_)));
				System.out.println(Poli);
 * 
 * 
 * 
 */





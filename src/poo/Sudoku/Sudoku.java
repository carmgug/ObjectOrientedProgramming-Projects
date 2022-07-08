package poo.Sudoku;

import java.util.ArrayList;
import poo.backtracking.Backtracking;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



public class Sudoku extends Backtracking<int[],Integer>{
	
	private int[][] Matrice;
	private int N_Sol;
	
	
	private LinkedList<int[][]> LinkedList_Soluzioni;
	private boolean Limite_Sup;
	

	public Sudoku() {
		Matrice=new int[9][9];//La matrice viene inizializzata a tutti 0
		N_Sol=0;
		LinkedList_Soluzioni=new LinkedList<>();
		Limite_Sup=false;
		
	}
	
	
	
	
	public void imposta(int i,int j,int v) {
		if(v>9 || v <1 || i<0 || i>8 || j<0 || j>8) throw new IllegalArgumentException();
		int[] p= {i,j};
		if(assegnabile(p,v)) {
			Matrice[i][j]=v;
		}
		else throw new IllegalStateException();
		
	}
	
	
	@Override
	protected boolean assegnabile(int[] p, Integer s) {
		int i=p[0],j=p[1];
		if(Matrice[i][j]!=0) return false;
		boolean Check_1=colonna(j,s);
		boolean Check_2=riga(i,s);
		boolean Check_3=SottoMatrice(i,j,s);
		
		return Check_1 && Check_2 && Check_3;
	}
	
	

	@Override
	protected void assegna(int[] ps, Integer s) {
		Matrice[ps[0]][ps[1]]=s;
		
	}

	@Override
	protected void deassegna(int[] ps, Integer s) {
		Matrice[ps[0]][ps[1]]=0;
	}
	
	@Override
	protected boolean ultimaSoluzione(int[] p ) {
		if(N_Sol>=40) {Limite_Sup=true; return true;}
		
		return false; 
	}//ultimaSoluzione
	
	@Override
	protected boolean esisteSoluzione(int[] p ) {
		List<int[]> lista=puntiDiScelta();
		return lista.size()==0;
	}//esisteSoluzione
	

	@Override
	protected void scriviSoluzione(int[] p) {
		N_Sol++;
		LinkedList_Soluzioni.add(CopiaMatriceCorrente());
		
		System.out.println();
		for(int i=0;i<Matrice.length;i++) {
			System.out.print("[ ");
			for(int j=0;j<Matrice.length;j++)
				if(j==8) System.out.println(Matrice[i][j]+" ]");
				else System.out.print(Matrice[i][j]+", ");
		}
		
	}

	@Override
	protected List<int[]> puntiDiScelta() {
		List<int[]> lista=new LinkedList<int[]>();
		for(int i=0;i<Matrice.length;++i)
			for(int j=0;j<Matrice[0].length;++j)
				if(Matrice[i][j]==0) {
					int[] v= {i,j};

					lista.add(v);
				}
		return lista;
	}

	@Override
	protected Collection<Integer> scelte(int[] p) {
		ArrayList<Integer> sceltep=new ArrayList<>();
		for(int i=1;i<=9;++i)
			if(assegnabile(p,i))
				sceltep.add(i);
		
		return sceltep;
	}//scelte

	@Override
	protected void risolvi() {
		List<int[]> lista=puntiDiScelta();
		tentativo(lista,lista.get(0));
		
	}
	
	//---MetodiImplementati---//
	private int[][] CopiaMatriceCorrente() {
		int[][] soluzionecurr=new int[9][9];
		for(int i=0;i<Matrice.length;++i) {
			System.arraycopy(Matrice[i], 0, soluzionecurr[i], 0, Matrice[0].length);
		}
		return soluzionecurr;
	}//copiaMatriceCorrente
	
	private boolean colonna(int j,int v) {
		for(int i=0;i<Matrice.length;++i) {
			if(Matrice[i][j]==v) return false;
		}
		return true;
	}//colonna
	
	private boolean riga(int i,int v) {
		for(int j=0;j<Matrice[0].length;++j) {
			if(Matrice[i][j]==v) return false;
		}
		return true;
	}//riga

	private boolean SottoMatrice(int r,int c,int v) {
		int sup_r=(r-r%3)+3;
		int sup_c=(c-c%3)+3;
		for(int i=r-r%3;i<sup_r;++i) {
			for(int j=c-c%3;j<sup_c;++j) {
				if(Matrice[i][j]==v) return false;
			}
		}
		return true;
		
	}//SottoMatrice
	
	public LinkedList<int[][]> getSoluzioni(){
		return new LinkedList<int[][]>(LinkedList_Soluzioni);
	}//getSoluzioni
	
	
	
	public int[][] MatriceGiocoCurr(){
		int [][] DeepCopyOfGame=new int[9][9];
		for(int i=0;i<Matrice.length;i++) {
			System.arraycopy(Matrice[i], 0, DeepCopyOfGame[i], 0, Matrice[0].length);
		}
		return DeepCopyOfGame;
	}//MatriceGiocoCurr
	
	
	public boolean TroppeSoluzioni() {
		return Limite_Sup;
	}
	
	
	
public static void main(String[] args) {
		
		Sudoku partita=new Sudoku();
		
		
		int [][] Ciao=partita.Matrice;
		for(int i=0;i<Ciao.length;i++) {
			System.out.println(); System.out.print("[ ");
			for(int j=0;j<Ciao.length;j++)
				if(j==8) System.out.print(Ciao[i][j]+" ]");
				else System.out.print(Ciao[i][j]+", ");
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		partita.risolvi();
		
		}
		
	}

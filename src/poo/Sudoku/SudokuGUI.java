package poo.Sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.*;





class FinestraGUI extends JFrame{
	
	private File fileDiSalvataggio=null;
	private boolean Partita_Finita;
	private String titolo="SudokuGui";
	private JMenuItem nuova,apri,salva,salvaConNome, esci, risolvi, about;
	
	//Pannello Sudoku
	private JPanel Sudoku_Panel;
	private JTextField[][] GridDiGioco=new JTextField[9][9];
	//Pannello Operazioni Next Previous
	private JPanel Operation_Panel;
	//Button
	private JButton Next,Previous;
		
	//Sudoku
	private Sudoku Game;
	//Soluzioni Possibili
	private LinkedList<int[][]> SoluzioniTotali; //Usare Una LinkedList
	private ListIterator<int[][]> iterator;
	
	
	public FinestraGUI() {
		
		
		setTitle(titolo);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if( consensoUscita() ) System.exit(0);
			}
		});
		
		
		AscoltatoreEventiAzione listener=new AscoltatoreEventiAzione();
		
		
		//Menù
		JMenuBar menuBar=new JMenuBar();
		this.setJMenuBar(menuBar);
		
		//Caratterizzazzione Menù
		
		//Partita
		JMenu partitaMenu=new JMenu("Partita");
		menuBar.add(partitaMenu);
		//1
		nuova=new JMenuItem("Crea nuova partita");
		partitaMenu.add(nuova);
		nuova.addActionListener(listener);
		partitaMenu.addSeparator();
		apri=new JMenuItem("Ripristina partita");
		partitaMenu.add(apri);
		apri.addActionListener(listener);
		salva=new JMenuItem("Salva");
        salva.addActionListener(listener);
        partitaMenu.add(salva);
        salvaConNome=new JMenuItem("Salva con nome");
        salvaConNome.addActionListener(listener);
        partitaMenu.add(salvaConNome);
        partitaMenu.addSeparator();
        //2
        esci=new JMenuItem("Esci");
        esci.addActionListener(listener);
        partitaMenu.add(esci);
        
        //Comandi
        JMenu Comandi=new JMenu("Comandi");
        menuBar.add(Comandi);
        //1
        risolvi=new JMenuItem("Trova soluzione/i");
        risolvi.addActionListener(listener);
        Comandi.add(risolvi);
        
        //Help Menu
		JMenu helpMenu=new JMenu("Help");
		about=new JMenuItem("About");
		about.addActionListener(listener);
		helpMenu.add(about);
		menuBar.add(helpMenu);
      	
		//Pannello Sudoku
		Sudoku_Panel=new JPanel(new GridLayout(9,9));
		InizializzaCelle();
		
		
		//Pannello Operazioni
		Operation_Panel=new JPanel();
		this.add(Operation_Panel,BorderLayout.SOUTH);
		//Buttons
		Next=new JButton("Prossima Soluzione");
		Next.addActionListener(listener);
		Previous=new JButton("Soluzione Precedente");
		Previous.addActionListener(listener);
		Operation_Panel.add(Previous);Operation_Panel.add(Next);
		
		//Pannello con JLabel 
		JPanel Game_Name=new JPanel(new BorderLayout());
		this.add(Game_Name,BorderLayout.NORTH);
		JLabel Intestazione=new JLabel("Sudoku 9X9",JLabel.CENTER);
		Game_Name.add(Intestazione,BorderLayout.CENTER);
		
		PreMenu();
		setLocation(300,300);
	    setSize(350,450);
	    setResizable(false);
	    
	}
	
	//Metodi-Di-Appoggio//
	private void PreMenu() {
		salvaConNome.setEnabled(false);
		salva.setEnabled(false);
		Next.setEnabled(false);
		Previous.setEnabled(false);
		Sudoku_Panel.setVisible(false);
		risolvi.setEnabled(false);
	}//PreMenu
	
	private void Start() {
		salvaConNome.setEnabled(true);
		salva.setEnabled(true);
		Sudoku_Panel.setVisible(true);
		risolvi.setEnabled(true);
	}//Start
	
	private void NewGame() {
		Game=new Sudoku();Partita_Finita=false;
		iterator=null; Next.setEnabled(false);Previous.setEnabled(false);
		for(int i=0;i<9;++i)
			for(int j=0;j<9;++j) {
				JTextField Casella=GridDiGioco[i][j];
				Casella.setText("");Casella.setBackground(Color.white);
				Casella.setEditable(true);
			}
	}
	
	private boolean consensoUscita() {
			if(fileDiSalvataggio==null) {
			   int option=JOptionPane.showConfirmDialog(
					   null, "Continuare ?", "Uscita in corso",
					   JOptionPane.YES_NO_OPTION);
			   return option==JOptionPane.YES_OPTION;
			 }
			else {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				
				int option=JOptionPane.showConfirmDialog( null,
						"Ultimo Salvataggio effettuato:"
							+sdf.format(fileDiSalvataggio.lastModified())+
							"\nContinuare ?","Uscendo si perderanno tutti i dati!",
									JOptionPane.YES_NO_OPTION);
	
				return option==JOptionPane.YES_OPTION;
			}
		}//consensoUscita
	
	private void InizializzaCelle() {
		
		this.add(Sudoku_Panel,BorderLayout.CENTER);
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				JTextField N_Box=new JTextField("",1);
				N_Box.setHorizontalAlignment(JTextField.CENTER);
				
				//Personalizzazzione Grafica
				if(i%3==2 && i%3==j%3 && (i!=8 && j!=8)) N_Box.setBorder(BorderFactory.createMatteBorder(1,1,4,4,Color.DARK_GRAY));
				else if(i==2 || i==5) N_Box.setBorder(BorderFactory.createMatteBorder(1,1,4,1,Color.DARK_GRAY));
				else if(j==2 || j==5) N_Box.setBorder(BorderFactory.createMatteBorder(1,1,1,4,Color.DARK_GRAY));
				else N_Box.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.DARK_GRAY));
				
				
				GridDiGioco[i][j]=N_Box;
				Sudoku_Panel.add(N_Box);
				
				
				N_Box.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(e.getSource()==N_Box) {
							String valore=N_Box.getText();
							//PosizioneCellaSuSchermo.ConvertPosizioneMatriceBack-END
							
							int c=(int) Math.round(N_Box.getLocation().getX() /(Sudoku_Panel.getSize().getWidth()/9));
							int r=(int) Math.round(N_Box.getLocation().getY()/(Sudoku_Panel.getSize().getHeight()/9));
							
							try {
								Game.imposta(r, c, Integer.parseInt(valore));
								N_Box.setEditable(false);N_Box.setBackground(Color.GREEN);
								
								
							}catch(Exception exp) {
								JOptionPane.showMessageDialog(null,
										"Valore non valido:\n"
										+"Inserisci un valore tra 1 e 9,\n"+"attenendoti alle regole del gioco.","Errore",JOptionPane.ERROR_MESSAGE);
								N_Box.setText("");
							}
								
							
						}
						
					}//actionPerformed
					
				});// ActionListener LambdaExpression
			} //for columns 
		}//for row
		
	}

	private void DisabilitaCelle() {
		for(int i=0;i<9;++i) 
			for(int j=0;j<9;++j) 
				GridDiGioco[i][j].setEditable(false);
	}

	private void VisualizzaMatriceSoluzione(int[][] Matrice) {
		for(int i=0;i<9;++i) {
			for(int j=0;j<9;++j) {
				JTextField CellaCor=GridDiGioco[i][j];
				CellaCor.setText(String.valueOf(Matrice[i][j]));
				CellaCor.setEditable(false);
			}
		}
		
	}
	
	private void refresh() {
		this.revalidate();
		Sudoku_Panel.repaint();
	}
	
	
	private void ripristina(String nomeFile) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(nomeFile));
		Start();NewGame();
		
		for(int i=0;i<GridDiGioco.length;++i)
			for(int j=0;j<GridDiGioco[0].length;++j) {
				String linea=br.readLine();
				if(!linea.equals("0")) {
					GridDiGioco[i][j].setText(linea);GridDiGioco[i][j].setBackground(Color.GREEN);
					GridDiGioco[i][j].setEditable(false);
					Game.imposta(i,j,Integer.parseInt(linea));
				}
				
			}
		br.close();
	}
	
	public void salva(String nomeFile) throws IOException{
		PrintWriter pw=new PrintWriter(new FileWriter(nomeFile));
		int[][] Partita=Game.MatriceGiocoCurr();
		for(int i=0;i<Partita.length;++i)
			for(int j=0;j<Partita[0].length;++j) {
				pw.println(Partita[i][j]);
			}
		pw.close();
		
	}//salva
	
	
	//-----LISTENER IMPLEMENTATO-----//
	private class AscoltatoreEventiAzione implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==esci) {
				if(consensoUscita())System.exit(0);
			}
			
			else if(e.getSource()==nuova) {
				Start(); NewGame();
				
			}
			
			else if(e.getSource()==apri) {
				
				//FILE chooser
				JFileChooser chooser=new JFileChooser();
				try {
					if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
						 if( !chooser.getSelectedFile().exists() ){
	  						   JOptionPane.showMessageDialog(null,"File inesistente!"); 
	  					   }
						 else{	
	  						   fileDiSalvataggio=chooser.getSelectedFile();
	  						   FinestraGUI.this.setTitle(titolo+" - "+fileDiSalvataggio.getName());
	  						   try{
	  							   ripristina( fileDiSalvataggio.getAbsolutePath() );
	  						   }catch(IOException ioe){
	  							   JOptionPane.showMessageDialog(null,"Fallimento apertura. File malformato!");
	  						   }
	  					   }
						
					}
				}catch(Exception exc) {
					exc.printStackTrace();
				}
				
			}
				
			else if (e.getSource()==salva) {
				//file chooser
  			   	JFileChooser chooser=new JFileChooser();
  			   	try{
  			   		
  			   		if( fileDiSalvataggio!=null ){
  			   			
  					   int ans=JOptionPane.showConfirmDialog(null,"Sovrascrivere "+fileDiSalvataggio.getAbsolutePath()+" ?");
					   if( ans==0 /*Risposta==Si*/)
						   salva( fileDiSalvataggio.getAbsolutePath() );
					   else
						   JOptionPane.showMessageDialog(null,"Nessun salvataggio!");
					   return;
				   }
  			   		if( chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
  			   			fileDiSalvataggio=chooser.getSelectedFile();
  			   			FinestraGUI.this.setTitle(titolo+" - "+fileDiSalvataggio.getName());
  				   }
  			   		if( fileDiSalvataggio!=null ){
  					   salva( fileDiSalvataggio.getAbsolutePath() );
  				   }
  			   		else
  					   JOptionPane.showMessageDialog(null,"Nessun Salvataggio!");
  			   }catch( Exception exc ){
  				   exc.printStackTrace();
  			   }
  			   	
			}
			
			else if(e.getSource()==salvaConNome) {
			
				//file chooser
				JFileChooser chooser=new JFileChooser();
				try{
					if( chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
						fileDiSalvataggio=chooser.getSelectedFile();	  						
  						FinestraGUI.this.setTitle(titolo+" - "+fileDiSalvataggio.getName());
  					   }
					
  				   if( fileDiSalvataggio!=null ){
  					   salva( fileDiSalvataggio.getAbsolutePath() );
  				   }
  				   else
  					   JOptionPane.showMessageDialog(null,"Nessun Salvataggio!");
  				   
  			   }catch( Exception exc ){
  				   exc.printStackTrace();
  			   }
			}
			
			else if(e.getSource()==risolvi) {
				
				Game.risolvi();
				SoluzioniTotali=Game.getSoluzioni();
				
				DisabilitaCelle();risolvi.setEnabled(false);
				
				
				
				if(SoluzioniTotali.size()==0) { 
					JOptionPane.showMessageDialog(null, "Nessuna Soluzione!!",
							"Sistema singolare",JOptionPane.INFORMATION_MESSAGE);
					Next.setEnabled(false);Previous.setEnabled(false);
				}
				else {
					if(Game.TroppeSoluzioni()) {
						JOptionPane.showMessageDialog(null, "TroppeSoluzioni!!\n"+
								"Saranno disponibili solo le prime "+SoluzioniTotali.size(),
								"Attenzione",JOptionPane.INFORMATION_MESSAGE);
					}
					iterator= SoluzioniTotali.listIterator();
					VisualizzaMatriceSoluzione(iterator.next()); //se la size !=0 allora sicuramente una soluzione
					Next.setEnabled(iterator.hasNext());Previous.setEnabled(iterator.hasPrevious());
				}
				
				
				
			}
			
			else if(e.getSource()==Next) {
				VisualizzaMatriceSoluzione(iterator.next());
				Next.setEnabled(iterator.hasNext());Previous.setEnabled(iterator.hasPrevious());
				
				
			}
			
			else if(e.getSource()==Previous) {
				VisualizzaMatriceSoluzione(iterator.previous());
				Next.setEnabled(iterator.hasNext());Previous.setEnabled(iterator.hasPrevious());
			}
			else if(e.getSource()==about){
				JOptionPane.showMessageDialog(null, "Gioco Sudoku con 81 celle.\n"
						+"Premi nuova partita per inziare o carica una partita precedente", 
						"About", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}//action Performed
	}//AscoltatoreEventiAzione
	
	
}//FinestraGUI





public class SudokuGUI {
	public static void main( String []args ){
		EventQueue.invokeLater( new Runnable(){
		  public void run(){
		      JFrame f=new FinestraGUI();
		      f.setVisible(true);	  
		  }
	  });
	}//main
	

}

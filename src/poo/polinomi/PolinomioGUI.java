package poo.polinomi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


import poo.util.RegexPolinomi;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
//per leggetere gli attributi di uni file
import java.text.SimpleDateFormat;


@SuppressWarnings("serial")
class FinestraGUI extends JFrame{
	
	private File fileDiSalvataggio=null;
	private String titolo="PolinomioGui ";
	private String tipo="";
	private JMenuItem tipoLL, tipoSet,
	   apri, salva, salvaConNome, esci, about,
	   aggiungiPolinomio, rimuoviPolinomio,modificaPolinomio,svuota;
	
	//Pannello Polinomi
	JPanel Pannello_Polinomi;
	private LinkedList<JCheckBox> Box_P_Totali=new LinkedList<>();
	//PannelloOperazioni
	JPanel Pannello_Operazioni;
	private JButton Box_Val,Box_add,Box_mul,Box_D;
	
	
	private boolean PolinomioOK;
	private FrameAggiungiPolinomio fAP;
	
	
	
	
	//Regex Di Controllo
	final static String Numero="[\\d]+";
	final static String Grado="(x(\\^[0-9]+)?)";//x(^0)
	final static String Monomio="((\\-?"+Numero+Grado+"?)|(\\-?"+Grado+"))";
	final static String Regex="("+Monomio+"([\\-\\+]"+Numero+"("+Grado+")?|([\\+\\-]"+Grado+"))*)"; //(4 4x x^321 x -4)  //4x+4-4+4
	final static String INVALIDO="[0]*"+Grado+"?";
	
	public FinestraGUI() {
		setTitle(titolo+tipo);
		
		
		
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
		
		//Caraterizzazione Menù
		
		//File
        JMenu fileMenu=new JMenu("File");
        menuBar.add(fileMenu);
        //voci File
        //1
        JMenu tipoImp=new JMenu("Nuovo");
        fileMenu.add(tipoImp);
        
        tipoLL=new JMenuItem("LinkedList");
        tipoLL.addActionListener(listener);
        tipoImp.add(tipoLL);
        tipoSet=new JMenuItem("Set");
        tipoSet.addActionListener(listener);
        tipoImp.add(tipoSet);
        fileMenu.addSeparator();
        //2
        apri=new JMenuItem("Apri");
        apri.addActionListener(listener);
        fileMenu.add(apri);
        salva=new JMenuItem("Salva");
        salva.addActionListener(listener);
        fileMenu.add(salva);
        salvaConNome=new JMenuItem("Salva con nome");
        salvaConNome.addActionListener(listener);
        fileMenu.add(salvaConNome);
        fileMenu.addSeparator();
        //3
        esci=new JMenuItem("Esci");
        esci.addActionListener(listener);
        fileMenu.add(esci);
        
        //Comandi
		JMenu commandMenu=new JMenu("Comandi");
		menuBar.add(commandMenu);
        //Voci Comandi
		//1
		aggiungiPolinomio=new JMenuItem("Aggiungi Polinomio");
		aggiungiPolinomio.addActionListener(listener);
		commandMenu.add(aggiungiPolinomio);
		rimuoviPolinomio=new JMenuItem("Rimuovi Polinomio/i");
		rimuoviPolinomio.addActionListener(listener);
		commandMenu.add(rimuoviPolinomio);
		modificaPolinomio=new JMenuItem("Modifica Polinomio");
		modificaPolinomio.addActionListener(listener);
		commandMenu.add(modificaPolinomio);
		commandMenu.addSeparator();
		//2
		svuota=new JMenuItem("Svuota WorkSpace");
		svuota.addActionListener(listener);
		commandMenu.add(svuota);
		
		
		
		//Help Menu
		JMenu helpMenu=new JMenu("Help");
		about=new JMenuItem("About");
		about.addActionListener(listener);
		helpMenu.add(about);
		menuBar.add(helpMenu);
		
		
        //Pannello Polinomi
		Pannello_Polinomi=new JPanel(new FlowLayout());
		this.add(Pannello_Polinomi,BorderLayout.CENTER);
		
		
		//Pannello Operazioni
		Pannello_Operazioni=new JPanel();
		//JCheckBox
		Box_Val=new JButton("Value in X");
		Box_Val.addActionListener(listener);
		Box_add=new JButton("Add");
		Box_add.addActionListener(listener);
		Box_mul=new JButton("Mul");
		Box_mul.addActionListener(listener);
		Box_D=new JButton("Derivative");
		Box_D.addActionListener(listener);
		Pannello_Operazioni.add(Box_Val);Pannello_Operazioni.add(Box_add);Pannello_Operazioni.add(Box_mul);Pannello_Operazioni.add(Box_D);
        this.add(Pannello_Operazioni,BorderLayout.SOUTH);
		PreMenu();
		
        setLocation(300,300);
        setSize(350,450);
		
        
        
	
	
	
	
	}//costruttore
	
	
	//----METODI DI APPOGGIO----//
	
	private void AggiungiPolinomio(String Polinomio) {
		Polinomio P=RegexPolinomi.ConvertToPoli(Polinomio,tipo);
		JCheckBox temp=new JCheckBox(P.toString(),false);
		if(Box_P_Totali.size()==0) {AttivaComandi();}
		Box_P_Totali.add(temp);
		Pannello_Polinomi.add(temp);
	}
	
	private void AggiornaFrame() {
		validate();
        repaint();
	}//AggiornaPannelloPolinomi
	
	private boolean consensoUscita() {
		
		if(fileDiSalvataggio==null) {
		   int option=JOptionPane.showConfirmDialog(
				   null, "Continuare ?", "Uscendo si perderanno tutti i dati!",
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
	private void DisattivaComandi() {
		Box_Val.setEnabled(false);Box_add.setEnabled(false);
		Box_mul.setEnabled(false);Box_D.setEnabled(false);
		salva.setEnabled(false);salvaConNome.setEnabled(false);
		rimuoviPolinomio.setEnabled(false); modificaPolinomio.setEnabled(false);
		svuota.setEnabled(false);
	}//DisattivaComandi
	private void AttivaComandi() {
		Box_Val.setEnabled(true);Box_add.setEnabled(true);
		Box_mul.setEnabled(true);Box_D.setEnabled(true);
		salva.setEnabled(true);salvaConNome.setEnabled(true);
		rimuoviPolinomio.setEnabled(true);modificaPolinomio.setEnabled(true);
		svuota.setEnabled(true);
	}//AttivaComandi
	
	private void PreMenu() {
		apri.setEnabled(false);
		salva.setEnabled(false);
		salvaConNome.setEnabled(false);
		Pannello_Operazioni.setVisible(false);
		aggiungiPolinomio.setEnabled(false);
		rimuoviPolinomio.setEnabled(false);
		modificaPolinomio.setEnabled(false);
		svuota.setEnabled(false);
	}//PreMenu
	private void menuAvviato() {
		tipoLL.setEnabled(false);
		tipoSet.setEnabled(false);
		apri.setEnabled(true);
		salva.setEnabled(true);
		salvaConNome.setEnabled(true);
		Pannello_Operazioni.setVisible(true);
		aggiungiPolinomio.setEnabled(true);
		
		if(Box_P_Totali.size()==0) {DisattivaComandi();}
	}//menuAvviato
	
	private void Svuota() {
		
		Iterator<JCheckBox> it= Box_P_Totali.iterator();
		while(it.hasNext()) {
			Pannello_Polinomi.remove(it.next());it.remove();
		}
		DisattivaComandi();
		AggiornaFrame();
		
	}
	private void ripristina(String nomeFile) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(nomeFile));
		if(Box_P_Totali.size()!=0) {Svuota();}
		
		for(;;) {
			String linea=br.readLine();
			if(linea==null) {br.close();break;}
			if(!linea.matches(INVALIDO) && linea.matches(Regex)) {AggiungiPolinomio(linea);}
		}
		AggiornaFrame();
		
	}
	private void salva(String nomeFile) throws IOException{
		
		PrintWriter pw=new PrintWriter(new FileWriter(nomeFile));
		for(JCheckBox Poly:Box_P_Totali) pw.println(Poly.getText());
		pw.close();
		
	}
	//----FINE METODI DI APPOGGIO----//
	
	//-----FRAME AGGIUNTIVI-----//
	
	//Classi Private Frame per la gestione delle checkBox
	private class FrameAggiungiPolinomio extends JFrame implements ActionListener{
		private JTextField Polinomio_Ins;
		private JButton ok;
		public FrameAggiungiPolinomio() {
			setTitle("Aggiungi Polinomio");
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					if(PolinomioOK) {
						AggiungiPolinomio(Polinomio_Ins.getText());
						AggiornaFrame();
					}
					Polinomio_Ins.setText("");
					PolinomioOK=false;
					FrameAggiungiPolinomio.this.setVisible(false);
				}//WindowClosing
			});//definizioneWindowsListener
			PolinomioOK=false;
			JPanel p=new JPanel();
			p.add(new JLabel("Inserisci Polinomio",JLabel.RIGHT));
			p.add(Polinomio_Ins=new JTextField("",22));
			Polinomio_Ins.addActionListener(this);
			p.add(ok=new JButton("OK"));
			ok.addActionListener(this);
			
			this.add(p);
			
			setLocation(300,340);
			setSize(500,100);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==Polinomio_Ins) {
				if (!Polinomio_Ins.getText().matches(INVALIDO) && Polinomio_Ins.getText().matches(Regex)) {PolinomioOK=true;}
				else JOptionPane.showMessageDialog(null,"Polinomio Errato");
				
			}
			if(e.getSource()==ok) {
				if(PolinomioOK) {
					AggiungiPolinomio(Polinomio_Ins.getText());
					AggiornaFrame();
					
					this.setVisible(false);
					PolinomioOK=false;
					Polinomio_Ins.setText("");
					
				}
				
			
			}
			
		}//ActionPerformed
	}
	
	
		
	//----LISTENER IMPLEMENTATO----//
	
	private class AscoltatoreEventiAzione implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==esci) {
				if(consensoUscita())System.exit(0);
			}
			else if(e.getSource()==tipoLL) {
				tipo="LL";
				FinestraGUI.this.setTitle(titolo+" "+tipo);
				
				menuAvviato();
			}
			else if(e.getSource()==tipoSet) {
				tipo="Set";
				FinestraGUI.this.setTitle(titolo+" "+tipo);
				
				menuAvviato();
			}
			else if(e.getSource()==apri) {
				//FILE chooser
				JFileChooser chooser=new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt","TXT");
	  			chooser.setFileFilter(filter);
				try {
					if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
						 if( !chooser.getSelectedFile().exists() ){
	  						   JOptionPane.showMessageDialog(null,"File inesistente!"); 
	  					   }
						 else{	
	  						   fileDiSalvataggio=chooser.getSelectedFile();
	  						   FinestraGUI.this.setTitle(titolo+tipo+" - "+fileDiSalvataggio.getName());
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
			else if(e.getSource()==salva) {
				//file chooser
  			   	JFileChooser chooser=new JFileChooser();
  			   	FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt","TXT");
	  			chooser.setFileFilter(filter);
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
	  					   FinestraGUI.this.setTitle(titolo+tipo+" - "+fileDiSalvataggio.getName());
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
	  			   	FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt","TXT");
		  			chooser.setFileFilter(filter);
	  			   try{
	  				   if( chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
	  						   fileDiSalvataggio=chooser.getSelectedFile();	  						
	  						   FinestraGUI.this.setTitle(titolo+tipo+" - "+fileDiSalvataggio.getName());
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
			
			
			
			else if(e.getSource()==aggiungiPolinomio) {
				if(fAP==null) fAP=new FrameAggiungiPolinomio();
				fAP.setVisible(true);
			}
			else if(e.getSource()==rimuoviPolinomio) {
				
				Iterator<JCheckBox> it= Box_P_Totali.iterator();
				boolean Polinomi_Trovati=false;
				while(it.hasNext()) {
					JCheckBox x=it.next();
					if(x.isSelected()) {
						Pannello_Polinomi.remove(x);it.remove();
						if(!Polinomi_Trovati)Polinomi_Trovati=true;
					}
				}
				if(!Polinomi_Trovati) {JOptionPane.showMessageDialog(null,"Nessun Polinomio Selezionato.\n"
							+ "Operazione Annulata","Informazione",JOptionPane.INFORMATION_MESSAGE);return;
				}
				if(Box_P_Totali.size()==0) {DisattivaComandi();}
				AggiornaFrame();
				
				
			}
			else if(e.getSource()==modificaPolinomio) {
				Iterator<JCheckBox> it= Box_P_Totali.iterator();
				boolean Polinomio_Trovato=false;
				JCheckBox selezionato=null;
				while(it.hasNext()) {
					JCheckBox x=it.next();
					if(x.isSelected() && !Polinomio_Trovato) {selezionato=x;Polinomio_Trovato=true;}
					
					else if(x.isSelected() && Polinomio_Trovato) {
						JOptionPane.showMessageDialog(null,"Hai selezionato più di un Polinomio.\n"
								+ "Operazione Annulata","Informazione",JOptionPane.INFORMATION_MESSAGE);return;
						}
						
				}//while
				if(Polinomio_Trovato) {
					String Nuovo_Polinomio=JOptionPane.showInputDialog(null,"Inserisci nuovo Polinomio:","Modificatore",JOptionPane.QUESTION_MESSAGE);
					if(Nuovo_Polinomio==null || Nuovo_Polinomio.matches(INVALIDO) || !Nuovo_Polinomio.matches(Regex)) {
						JOptionPane.showMessageDialog(null,"Polinomio inserito non valido.\n"
									+ "Operazione Annulata","Errore",JOptionPane.ERROR_MESSAGE);return;
					}
					selezionato.setText(Nuovo_Polinomio);
				}
				
				else {JOptionPane.showMessageDialog(null,"Nessun Polinomio Selezionato.\n"
						+ "Operazione Annulata","Informazione",JOptionPane.INFORMATION_MESSAGE);return;
				}
			}
			else if(e.getSource()==svuota) {
				Svuota();
			}
			//Box_Val,Box_add,Box_mul,Box_D;
			else if(e.getSource()==Box_Val) {
				
				String Numero_D=JOptionPane.showInputDialog(null,"Inserisci un numero:","Selezionatore",JOptionPane.QUESTION_MESSAGE);
				if(Numero_D==null) {JOptionPane.showMessageDialog(null,"Nessun Valore Inserito.\n Operazione Annulata","Errore",JOptionPane.ERROR_MESSAGE);return;}
				
				double x;
				
				try {
					x=Double.parseDouble(Numero_D);
				}catch(NumberFormatException exc) {
					JOptionPane.showMessageDialog(null,exc.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//JCheckBox Box_P_Totali
				Polinomio P=null;
				boolean Polinomio_Trovato=false;
				for(JCheckBox J1:Box_P_Totali) {
					if(J1.isSelected() && !Polinomio_Trovato) {
						String poly=J1.getText();
						P=RegexPolinomi.ConvertToPoli(poly, tipo);
						Polinomio_Trovato=true;
					}
					else if(J1.isSelected() && Polinomio_Trovato) {
						JOptionPane.showMessageDialog(null,"Hai selezionato più di un Polinomio.\n"
								+ "Operazione Annulata","Errore",JOptionPane.ERROR_MESSAGE); 
						return;
					}
					
				}
				
				if(!Polinomio_Trovato) {
					JOptionPane.showMessageDialog(null,"Nessun Polinomio Selezionato","Errore",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(null, "Risultato Ottenuto:"+P.valore(x),"Operazione Value in x",JOptionPane.INFORMATION_MESSAGE);
				
				
			}
			else if(e.getSource()==Box_add) {
				
				Polinomio P_1=null,P_2=null;
				boolean Primo_trovato=false;
				boolean Secondo_trovato=false;
				for(JCheckBox J1:Box_P_Totali) {
					if(J1.isSelected() && !Primo_trovato) {
						String poly=J1.getText();
						P_1=RegexPolinomi.ConvertToPoli(poly, tipo);
						Primo_trovato=true;
					}
					else if(J1.isSelected() && !Secondo_trovato) {
						String poly=J1.getText();
						P_2=RegexPolinomi.ConvertToPoli(poly, tipo);
						Secondo_trovato=true;
						
					}
					else if(J1.isSelected() && Primo_trovato && Secondo_trovato) {
						JOptionPane.showMessageDialog(null,"Operazione Binaria!!\nMax: 2 Polinomi.","Errore",JOptionPane.ERROR_MESSAGE);
						return;
						
					}
					
				}//for
				if(!Primo_trovato || !Secondo_trovato) {
					JOptionPane.showMessageDialog(null,"Devi selezionare 2 polinomi!!","Errore",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Polinomio P_add=P_1.add(P_2);
				
				JOptionPane.showMessageDialog(null, "Risultato Ottenuto:\n"+P_add.toString(),"Operazione Addizzione",JOptionPane.INFORMATION_MESSAGE);
				AggiungiPolinomio(P_add.toString());
				AggiornaFrame();
			
			
			}//controllo Box_mul
			else if(e.getSource()==Box_mul) {
				
					
					
					Polinomio P_1=null,P_2=null;
					boolean Primo_trovato=false;
					boolean Secondo_trovato=false;
					for(JCheckBox J1:Box_P_Totali) {
						if(J1.isSelected() && !Primo_trovato) {
							String poly=J1.getText();
							P_1=RegexPolinomi.ConvertToPoli(poly, tipo);
							Primo_trovato=true;
							
						}
						else if(J1.isSelected() && !Secondo_trovato) {
							String poly=J1.getText();
							P_2=RegexPolinomi.ConvertToPoli(poly, tipo);
							Secondo_trovato=true;
							
						}
						else if(J1.isSelected() && Primo_trovato && Secondo_trovato) {
							JOptionPane.showMessageDialog(null,"Operazione Binaria!!\nMax: 2 Polinomi.","Errore",JOptionPane.ERROR_MESSAGE);
							return;
						}
						
					}//for
					if(!Primo_trovato || !Secondo_trovato) {
						JOptionPane.showMessageDialog(null,"Devi selezionare 2 polinomi!!","Errore",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					Polinomio P_mul=P_1.mul(P_2);
					
					JOptionPane.showMessageDialog(null, "Risultato Ottenuto:\n"+P_mul.toString(),"Operazione Moltiplicazione",JOptionPane.INFORMATION_MESSAGE);
					
					AggiungiPolinomio(P_mul.toString());
					AggiornaFrame();
				
			}//Controllo Box_mul
			else if(e.getSource()==Box_D) {
				
					
					
					
					Polinomio P=null;
					boolean Trovato=false;
					for(JCheckBox J1:Box_P_Totali) {
						if(J1.isSelected() && !Trovato) {
							String poly=J1.getText();
							P=RegexPolinomi.ConvertToPoli(poly, tipo);
							Trovato=true;
						}
						else if(J1.isSelected() && Trovato) {
							JOptionPane.showMessageDialog(null,"Devi selezionare un polinomio!!","Errore",JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					if(!Trovato) {
						JOptionPane.showMessageDialog(null,"Nessun Polinomio Selezionato","Errore",JOptionPane.ERROR_MESSAGE);
						return;
					}
					Polinomio PD;
					
					try {
							PD=P.derivata();
					}catch(IllegalArgumentException exc) {
						JOptionPane.showMessageDialog(null,exc.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
						return;
					}
					//Se la derivata è 0 per costruzione restituisce una Stringa nulla
					if(PD.toString().length()==0) {JOptionPane.showMessageDialog(null, 
							"Risultato Ottenuto: 0\n"+"Il risultato non verrà aggiunto",
							"Operazione Derivata",JOptionPane.INFORMATION_MESSAGE);return;}
					else {
						JOptionPane.showMessageDialog(null, "Risultato Ottenuto:\n"+PD.toString(),"Operazione Derivata",JOptionPane.INFORMATION_MESSAGE);
						
						AggiungiPolinomio(PD.toString());
						AggiornaFrame();
					}
				
				
			}//Controllo Box_D
			else if(e.getSource()==about) {
				//DialogBox  NULL=frame di default
				JOptionPane.showMessageDialog( null,
						"Programma di Gestione di Polinomi  \n",
						"About", JOptionPane.INFORMATION_MESSAGE );
			}
			
		}//actionPerformed
		
	}//AscoltatoreEventiAzione


}//FinestraGUI


public class PolinomioGUI {
	public static void main( String []args ){
		EventQueue.invokeLater( new Runnable(){
			  public void run(){
			      JFrame f=new FinestraGUI();
			      f.setVisible(true);			  
			  }
		  });
	}//main
	

}//PolinomioGUI

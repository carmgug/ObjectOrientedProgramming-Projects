package poo.EspressioneAritmetica;



import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



class FinestraExp extends JFrame implements ActionListener{
	private JTextField expression;
	private JLabel ris;
	public FinestraExp() {
		setTitle("Risolvi Espressione");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel();
		p.add(new JLabel("Espressione",JLabel.RIGHT));
		p.add(expression=new JTextField("",20));
		p.add(new JLabel("Risultato :",JLabel.RIGHT));
		p.add(ris=new JLabel("",JLabel.LEFT));
		add(p);
		expression.addActionListener( this );
		setLocation(400,400);
		setSize(500,200);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==expression ){
			String Espressione=expression.getText();
			Integer risultato;
			try {
				risultato=EspressioneAritmetica.Risolvi(Espressione);
				ris.setText(risultato.toString());
			}catch(Exception exc) {
				exc.printStackTrace();
				JOptionPane.showMessageDialog(null,
							"Espressione Malformata.\n"
							+"Operazione Annulata","Errore",JOptionPane.ERROR_MESSAGE);
				expression.setText("");ris.setText("");return;
			}
	         
	      }
	     
	   }//actionPerfomrmed
		
	}//Finestra




public class EspressioneAritmeticaGUI {
	public static void main(String []args){
		EventQueue.invokeLater( new Runnable(){
			  public void run(){
			      JFrame f=new FinestraExp();
			      f.setVisible(true);			  
			  }
		  });
	}

}

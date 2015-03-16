import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Tworzy graficzny interfejs gry
 * 
 * @author Piotr Wrona
 * @version 1.2
 */

public class Plansza extends Gra{

	public final static int szerokoscPlanszy = 700;
	public final static int wysokoscPlanszy = 500;
	
	private Image zdjecieObrazu;
	private Graphics grafikaGry;
	
	/**
	 * Konstruuje ekran gry
	 */
	public Plansza(){
		JFrame okno = new JFrame("Pingiel");
		setBounds(0,0,szerokoscPlanszy,wysokoscPlanszy);
		addKeyListener(this);
		
		JPanel panel = (JPanel) okno.getContentPane();
		panel.setPreferredSize(new Dimension(szerokoscPlanszy,wysokoscPlanszy));
		panel.setLayout(null);
		panel.add(this);
				
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setResizable(false);
		okno.setBounds(100,50,szerokoscPlanszy+6,wysokoscPlanszy+25);
		okno.setVisible(true);	
		
		zdjecieObrazu = createImage (szerokoscPlanszy,wysokoscPlanszy);
		grafikaGry = zdjecieObrazu.getGraphics();		
	}	

	
	boolean ekranPowitalny = true;
	/**
	 * Rysyje elementy na ekranie
	 */
	public void paint(Graphics grafika){		
		
		grafikaGry.setColor(Color.black);
		grafikaGry.fillRect(0,0,szerokoscPlanszy,wysokoscPlanszy);
		
		grafikaGry.setColor(Color.white);
		grafikaGry.fillOval(xPileczka,yPileczka,20,20);
		
		grafikaGry.fillRect(Player1PaletkaX,Player1PaletkaY,20,100);
		grafikaGry.fillRect(Player2PaletkaX,Player2PaletkaY,20,100);
			
		grafikaGry.setFont(new Font("Tahoma", Font.BOLD, wysokoscPlanszy/10)); 
		if (Gra.pokazWynik){
			grafikaGry.drawString(String.valueOf(Gra.punktyPlayer1), (szerokoscPlanszy/2)-(szerokoscPlanszy/10), wysokoscPlanszy/4);
			grafikaGry.drawString(String.valueOf(Gra.punktyPlayer2), (szerokoscPlanszy/2)+(szerokoscPlanszy/10)-10, wysokoscPlanszy/4);
		}
		

		if (ekranPowitalny){
			String napis1 = "Player1 uzyj klawiszy \"Q\" oraz \"A\", aby sie poruszac";
			String napis2 = "Player2 uzyj klawiszy \"P\" oraz \"L\", aby sie poruszac";
			String napis3 = "Nacisnij SPACJE, aby kontynuowac";
			grafikaGry.setColor(Color.DARK_GRAY);
			grafikaGry.fillRect(0,0,szerokoscPlanszy,wysokoscPlanszy);
			grafikaGry.setFont(new Font("Tahoma", Font.BOLD, wysokoscPlanszy/30));
			grafikaGry.setColor(Color.white);
			grafikaGry.drawString(napis1, (szerokoscPlanszy/5), wysokoscPlanszy/4);
			grafikaGry.drawString(napis2, (szerokoscPlanszy/5), wysokoscPlanszy/4+50);
			grafikaGry.drawString(napis3, (szerokoscPlanszy/5), wysokoscPlanszy/4+100);
			ekranPowitalny=false;
		}
		
		grafika.drawImage(zdjecieObrazu,0,0,this);
	}
	
	public void update(Graphics grafika){
		paint(grafika);
	}
	
	
	public static void main(String[] args) {
		Plansza plansza = new Plansza();
		plansza.run();
	}

		
}

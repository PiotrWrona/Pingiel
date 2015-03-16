import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa odpowiedzialna za mechanik� gry
 * 
 * @author Piotr Wrona
 * @version 1.2
 *
 */

public class Gra extends Canvas implements Runnable, KeyListener{

	/**
	 * @param korekta korekta wielko�ci planszy
	 */
	private final static int korekta = 16;
	
	/**
	 * @param xPileczka warto�� na osi OX, gdzie znajduje si� pi�eczka
	 * @param yPileczka warto�� na osi OY, gdzie znajduje si� pi�eczka
	 * @param katPileczkiX ruch (liczba px) na osi OX na jeden cykl gry
	 * @param katPileczkiY ruch (liczba px) na osi OY na jeden cykl gry
	 * @param pileczkaPionowo kierunek ruchu pi�eczki (true=g�ra; false=d�)
	 * @param pileczkaPoziomo kierunek ruchu pi�eczki (true=prawo; false=lewo)
	 */
	public int xPileczka, yPileczka;
	public int katPileczkiX, katPileczkiY;
	private boolean pileczkaPionowo, pileczkaPoziomo;
	/**
	 * @param Player1PaletkaX okresla warto�� na osi OX, gdzie znajduje si� paletka gracza 1 (lewy brzeg planszy)
	 * @param Player1PaletkaY okresla warto�� na osi OY, gdzie znajduje si� paletka gracza 1
	 * @param Player2PaletkaX okresla warto�� na osi OY, gdzie znajduje si� paletka gracza 2 (prawy brzeg planszy))
	 * @param Player2PaletkaY okresla warto�� na osi OY, gdzie znajduje si� paletka gracza 2
	 * 
	 */
	public final int Player1PaletkaX = 0;
	public int Player1PaletkaY;
	public final int Player2PaletkaX = Plansza.szerokoscPlanszy-20;
	public int Player2PaletkaY;	
	/**
	 * @param start czy gra wystartowa�a (mo�liwy jest ruch paletek oraz pi�eczki)
	 * @param szybkoscGryNaStarcie szybko�� gry na starcie (warto�c pocz�tkowa)
	 * @param szybkoscGry szybko�� gry na starcie, kt�ra ro�nie z biegiem gry
	 * @param licznikPredkosci warto�� okreslaj�ca cykle gry i pomagaj�ca przelicza� szybko�c gry
	 * @param punktyPlayer1 liczba punkt�w gracza 1
	 * @param punktyPlayer2 liczba punkt�w gracza 2
	 * @param pokazWynik pokazuje wyniki po ka�dej ich zmianie
	 * @param koniecGry ko�czy gr� w przypadku osi�gni�cia warto�ci koniecGryIle
	 * @param koniecGryIle liczba punkt�w, jak� musi zdoby� gracz, aby wygra� gr� (zako�czy� gr�)
	 */
	private boolean start;
	private final int szybkoscGryNaStarcie = 15;
	private int szybkoscGry;
	private int licznikPredkosci;
	public static int punktyPlayer1, punktyPlayer2;
	public static boolean pokazWynik;
	private boolean koniecGry;
	private final int koniecGryIle = 5;
	
	
	public void run(){
			
		try {
			click(110,80);
		} catch (AWTException e1) {
			System.out.println("Autoklik nie zadzialal");
			e1.printStackTrace();
		}	
		
		szybkoscGry = szybkoscGryNaStarcie;
		
		xPileczka = Plansza.szerokoscPlanszy / 2;
		yPileczka = Plansza.wysokoscPlanszy / 2;
		
		katPileczkiX = 2;
		katPileczkiY = 2;
		
		pileczkaPionowo = true;
		pileczkaPoziomo = true;
				
		Player1PaletkaY = (Plansza.wysokoscPlanszy / 2) - (Plansza.wysokoscPlanszy / 6);
		Player2PaletkaY = (Plansza.wysokoscPlanszy / 2) - (Plansza.wysokoscPlanszy / 6);
		
		while(!start){}
		repaint();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(!start){}
		
		while(!koniecGry){
		
			odbicieOdPaletki();
			
			zmianaWyniku();
				
			ruchPileczki();
		
			czasiSzybkoscGry();

			repaint();
		}
		
	}

	
	/**
	 * Mechanika odbicia pi�eczki od paletek graczy
	 */
	private void odbicieOdPaletki() {
		if ( (xPileczka <= 20) && (yPileczka >= Player1PaletkaY && yPileczka <= Player1PaletkaY+100) ){
			pileczkaPoziomo = true;
		}
		if ( (xPileczka >= (Plansza.szerokoscPlanszy-20-korekta)) && (yPileczka >= Player2PaletkaY && yPileczka <= Player2PaletkaY+100) ){
			pileczkaPoziomo = false;
		}	
	}
	
	/**
	 * Mechanika zliczania punkt�w
	 */
	private void zmianaWyniku() {
		if (xPileczka >= Plansza.szerokoscPlanszy-korekta){
			punktyPlayer1++;
			restartPileczki();
			pileczkaPoziomo = false;
		}
		if (xPileczka <= 0){
			punktyPlayer2++;
			restartPileczki();
			pileczkaPoziomo = true;
		}
	}
	/**
	 * Restart pi�eczki (umieszczenie jej na �rodku planszy) po zdobyciu punktu przez gracza
	 */
	private void restartPileczki() {
		xPileczka = Plansza.szerokoscPlanszy / 2;
		yPileczka = Plansza.wysokoscPlanszy / 2;			
		katPileczkiX = 2;
		katPileczkiY = 2;
		pileczkaPionowo = true;
		szybkoscGry = szybkoscGryNaStarcie;
		licznikPredkosci = 0;
		pokazWynik = true;
		repaint();	
		System.out.println("wynik\t" + punktyPlayer1+":"+punktyPlayer2);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pokazWynik = false;
		if (punktyPlayer1>=koniecGryIle || punktyPlayer2>=koniecGryIle) koniecGry=true;
		while(!start){}
	}
	
	/**
	 * Mechanika ruchu pi�eczki (odbicie od g�rnej i dolnej linii oraz ruch w�a�ciwy) 
	 */
	private void ruchPileczki() {
		if (yPileczka <= 0){
			pileczkaPionowo=false;
		}
		if (yPileczka >= Plansza.wysokoscPlanszy-korekta){
			pileczkaPionowo=true;
		}
		if (!pileczkaPionowo) yPileczka += katPileczkiY;
		if (pileczkaPionowo) yPileczka -= katPileczkiY;
		if (pileczkaPoziomo) xPileczka += katPileczkiX;
		if (!pileczkaPoziomo) xPileczka -= katPileczkiX;
	}

	/**
	 * Liczenie cykli gry oraz zmiena pr�dko�ci gry (zwi�kszenie pr�dko�ci)
	 */
	private void czasiSzybkoscGry() {
		licznikPredkosci++;
		if (licznikPredkosci % 500 == 0){
			System.out.println("licznikPredkosci= " + licznikPredkosci);
			System.out.println("szybkoscGry= " + szybkoscGry);
			if (szybkoscGry>5) szybkoscGry-=1;
		}
		try {
			Thread.sleep(szybkoscGry);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Obs�uga Windows'owych klawiszy w grze (q,a,p,l oraz spacji)
	 */
	public void keyPressed(KeyEvent key) {
		
		if (key.getKeyCode() == 81){
			Player1PaletkaY -= 40;
			if (Player1PaletkaY <= 0) Player1PaletkaY=0;
		}
		if (key.getKeyCode() == 65){
			Player1PaletkaY += 40;
			if (Player1PaletkaY >= Plansza.wysokoscPlanszy-100) Player1PaletkaY=Plansza.wysokoscPlanszy-100;
		}
		if (key.getKeyCode() == 80){
			Player2PaletkaY -= 40;
			if (Player2PaletkaY <= 0) Player2PaletkaY=0;
		}
		if (key.getKeyCode() == 76){
			Player2PaletkaY += 40;
			if (Player2PaletkaY >= Plansza.wysokoscPlanszy-100) Player2PaletkaY=Plansza.wysokoscPlanszy-100;
		}
		if (key.getKeyCode() == 32) start=true;	
		//if (key.getKeyCode() != 0) System.out.println(key.getKeyCode());
		
	}
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == 32) start=false;
	}
	public void keyTyped(KeyEvent key) {	
	}

	/**
	 * Kliekni�cie na plansz� gry
	 * @param x miejsce klikni�cia na osi OX
	 * @param y miejsce klikni�cia na osi OY
	 */
	public static void click(int x, int y) throws AWTException{
	    Robot bot = new Robot();
	    bot.mouseMove(x, y);    
	    bot.mousePress(InputEvent.BUTTON1_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
}

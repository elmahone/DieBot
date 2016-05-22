/**
 * Joel Vainikka
 * Roope Koski
 */

package projekti;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Tulostus {
	/**
	 * Tulostaa laskuvirheet<br>
	 * @param i monesko virhe
	 */
    public static void virhe(int i){
        if (i == 1){
            LCD.clear();
            LCD.drawString("Tarkkuutta", 0, 1);
            LCD.drawString("laskuihin!", 0, 2);
            Tulostus.putsaus(3000,1);
        }
        else if (i == 2){
            LCD.clear();
            LCD.drawString("Etkai vain",0,1);
            LCD.drawString("juksuta?", 0, 2);
            Tulostus.putsaus(3000,1);
        }
    }
    
    /**
     * Pit‰‰ annetun ajan viiveen ja putsaa ruudun sen j‰lkeen<br>
     * @param aika viive millisekuntteina 1 sek == 1000 ms<br>
     * @param putsaus putsataanko ruutu viiven j‰lkeen 1 = kyll‰, 0 = ei
     */
    public static void putsaus(int aika, int putsaus){
    	Delay.msDelay(aika);
    	if (putsaus == 1){
    		LCD.clear();
    	}
    }
    
    /**
     * Vertaa pelaajan ja robotin pisteit‰ kesken‰‰n ja ilmoittaa kumpi voitti<br>
     * @param pelaaja pelaajan pisteet<br>
     * @param robotti robotin pisteet
     */
    public static void tulos(int pelaaja, int robotti){
    	// Tulostaa pelaajan tuloksen
    	LCD.drawString("Pelaajan tulos " + pelaaja, 0, 1);
   		Delay.msDelay(1000);
   		
    	// Tulostaa robotin tuloksen
   		LCD.drawString("Robotin tulos " + robotti, 0, 2);
   		Delay.msDelay(1000);
   		// Katsoo kumpi voitti
  		if (robotti == pelaaja){
  			// Tasapeli
  			LCD.drawString("Tasapeli :I", 0, 3);
  			Delay.msDelay(3000); // 3000 ms = 3 sek
  			LCD.clear();
 		}
    	else if (robotti < pelaaja){
			// Pelaaja voitti
    		LCD.drawString("Sina voitit :P", 0, 3);  
 			Delay.msDelay(3000); // 3000 ms = 3 sek
 			LCD.clear();
    	}
    	else {
			// Robotti voitti
			LCD.drawString("Voitin :3", 0, 3);
			Tulostus.putsaus(3000, 1);
    	}
    }
    
    /**
     * Tulostaa robotin summan<br>
     * @param summa robotin summa
     */
    public static void robotinSumma(int summa){
		// Tulostetaan summa ja pyyhit‰‰n ruutu sen j‰lkeen
		LCD.drawString("Noppien summa " + summa, 0, 6);
		Tulostus.putsaus(2000, 1);
    }
    
    /**
     * Tulostaa robotin ruudulle kaukos‰‰timen ohjeet
     */
    public static void ohjeetRemote(){
		LCD.clear();
		LCD.drawString("Anna pisteet", 0, 0);
		LCD.drawString("Painike 1 ++", 0, 1);
		LCD.drawString("Painike 2 --", 0, 2);
		LCD.drawString("Painike 3 lopettaa", 0, 3);
    }
    
    /**
     * Valitsee tulostuksen riippuen onko kierroksia viel‰ j‰ljell‰
     * @param kierrokset pelatut kierrokset
     * @param maara pelattavat kierrokset
     */
    public static void tarkistus(int kierrokset, int maara){
    	LCD.drawString("Kierros " + kierrokset + " / " + maara, 0, 1);
		
    	if (kierrokset == maara){
			LCD.drawString("Peli loppui", 0, 2);
		}
		else {
			LCD.drawString("Uusi peli alkaa 4 sek", 0, 2);	
		}
    }
}
/**
 * Metodissa annaLuku annetaan arvot pelien sek‰ noppien m‰‰rille
 * Metodissa lueNopat v‰riskannataan nopat ja annetaan pelaajan nopille arvot
 * Metodissa robottiArvonta robotin nopille annetaan arvot
 * @author Oskar GusgÂrd
 * @author Miika Ahonen
 * @date 13.10.2014
 */

package projekti;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Laskuri {
	
	/**
	 * Voi antaa kaukos‰‰timell‰ luvun robotille<br>
	 * Painike 1 ++<br>
	 * Painike 2 --<br>
	 * Painike 3 lopettaa<br>
	 * @param mode tulostuu ruudulle<br>
	 * Parametri mode on ensimm‰isess‰ casessa sana "Noppia", ja toisessa sana "Kierroksia"
	 * @param infraredSensor sallii kaukos‰‰timen k‰ytˆn
	 * @return palauttaa lopullisen arvon
	 */
	public static int annaLuku(String mode, EV3IRSensor infraredSensor){
		
		int remoteCommand = 0;
		int count = 0;
		String tulostus = mode;
		
		do {
            remoteCommand = infraredSensor.getRemoteCommand(0);
            switch (remoteCommand){
            case 1:
                Delay.msDelay(500);
                LCD.clear();
                count++;
                LCD.drawString(tulostus + " " + count, 0, 2);
                break;
            case 2:
                Delay.msDelay(500);
                LCD.clear();
                count--;
                LCD.drawString(tulostus + " " + count, 0, 2);
                break;
            }
        } while (remoteCommand != 3);
		
		return count;
	}
	
	/**
	 * Skannaa pelaajan nopat ja laskee noppien summan<br>
	 * @param maara on noppien m‰‰r‰<br>
	 * @return noppien summan
	 */
	public static int lueNopat(int maara){
        // V‰risensori
        EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S3);
        
		int noppien_maara = maara;
		int summa = 0;
		int nopat[] = new int[noppien_maara];
		
		// Aloitetaan skannaus prosessi
        for (int i = 0; i < noppien_maara; i++){
            LCD.clear();
            // Pyydet‰‰n aina loopin kierrosta vastaava noppa
            LCD.drawString("Anna " + (i + 1) + ". noppa", 0, 1);
            Delay.msDelay(2000); // 2000 ms = 2 sek

            // Katsotaan nopan v‰ri‰ vastaava lukuarvo ja laitetaan lukuarvo
            // nopat[] taulukkoon heittoa vastaavaan soluun.
            switch (cs.getColorID()) {
            case Color.BLUE:
                LCD.drawString("Sininen = 1", 0, 2);
                nopat[i] = 1;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            case Color.GREEN:
                LCD.drawString("Vihrea = 2", 0, 2);
                nopat[i] = 2;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            case Color.BLACK:
                LCD.drawString("Musta = 3", 0, 2);
                nopat[i] = 3;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            case Color.YELLOW:
                LCD.drawString("Keltainen = 4", 0, 2);
                nopat[i] = 4;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            case Color.RED:
                LCD.drawString("Punainen = 5", 0, 2);
                nopat[i] = 5;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            case Color.WHITE:
                LCD.drawString("Valkoinen = 6", 0, 2);
                nopat[i] = 6;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            default:
                // default tulee jos mit‰‰n noppaa ei annettu ja annetaan nopan lukuarvoksi 0
                LCD.drawString("Ei noppaa = 0.", 0, 2);
                nopat[i] = 0;
                Delay.msDelay(2000); // 2000 ms = 2 sek
                break;
            }
        }
        
        // Lasketaan pelaajan nopat yhteen.
        for (int i = 0; i < noppien_maara; i++){
            summa = summa + nopat[i];
        }
		
        cs.close();
		return summa;
	}
	
	/**
	 * Robotti heitt‰‰ omat heittonsa ja tulostaa summan<br>
	 * @param maara noppien m‰‰r‰<br>
	 * @return heittojen summan
	 */
	public static int robottiArvonta(int maara){
		int r_nopat[] = new int[maara]; // Luodaan robotin heitoille taulukko
		int summa = 0;
		
		// Robotin heitot laitetaan taulukkoon r_nopat
		for (int j = 0; j < maara; j++) {
			r_nopat[j] = (int) (Math.random() * 6) + 1;
		}
		
		// Robotin yhteenlaskettu summa
		for (int k = 0; k < maara; k++) {
			summa = summa + r_nopat[k];
		}
		
		LCD.drawString("Robotin tulos " + summa, 0, 1);
		
		return summa;
	}
}
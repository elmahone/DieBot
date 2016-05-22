/**
 * ryhm‰1
 */

package projekti;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.utility.Delay;

public class NoppaRobotti {
	public static void main(String[] args){
		
		// Alustetaan muutamat muuttujat
		int virheita = 0;
		int pelatut_kierrokset = 0;
		int pelien_maara = 1;
		int noppien_maara = 0;
		
		// Oliot
		EV3LargeRegulatedMotor renkaat = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor moottori = new EV3LargeRegulatedMotor(MotorPort.D);
		EV3LargeRegulatedMotor flip = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3IRSensor infraredSensor = new EV3IRSensor(SensorPort.S2);
		
		Moottorit m = new Moottorit(renkaat, moottori, flip, infraredSensor);
		
		m.start();
		
		// Kysyt‰‰n noppien m‰‰r‰
		LCD.drawString("Monta noppaa?", 0, 2);
		noppien_maara = Laskuri.annaLuku("Noppia", infraredSensor);
		Tulostus.putsaus(500, 1); // Ruudun putsaus
		
		// Kysyt‰‰n kierrosten m‰‰r‰
		LCD.drawString("Monta kierrosta?", 0, 2);
		pelien_maara = Laskuri.annaLuku("Kierroksia", infraredSensor);
		Tulostus.putsaus(500, 1); // Ruudun putsaus
		
		while (pelatut_kierrokset < pelien_maara){
			int r_summa = 0;
			
			// Peli alkaa
			pelatut_kierrokset++; // Lis‰t‰‰n 1 lis‰‰ pelattuihin
			LCD.drawString("Kierros " + pelatut_kierrokset + " / " + pelien_maara, 0, 1);
			LCD.drawString("Noppia " + noppien_maara, 0, 2);
			LCD.drawString("Alkaa 3 sek", 0, 3);
			Tulostus.putsaus(3000, 1); // Ruudun putsaus
			
			// Tulostetaan ja odotetaan 3 sekunttia ennen skannauksen aloitusta
			LCD.drawString("Skannaan nopat", 0, 1);
			LCD.drawString("3 sek paasta", 0, 2);
			Delay.msDelay(3000); // 3000 ms = 3 sek
			
			// Skannaa nopat ja laskee ne yhteen
			int pelaaja_summa = Laskuri.lueNopat(noppien_maara);
			
			Tulostus.ohjeetRemote();
			
			int annetut_pisteet = Laskuri.annaLuku("Pisteet", infraredSensor);
			
			// Tarkistetaan laskiko pelaaja noppien summan oikein
			if (pelaaja_summa != annetut_pisteet){
				virheita=3;
				Tulostus.virhe(virheita);
				if (virheita == 3){ //t‰ss‰ on bugi, ohjelma ei sammu riehumisen j‰lkeen
					m.setRiehu();
					Delay.msDelay(25000);
					break;
				}
			}
			else {
				// Pelaaja laski oikein
				LCD.drawString("Hienosti laskettu =)", 0, 2);
				Tulostus.putsaus(2000, 1);
			}
			
			r_summa = Laskuri.robottiArvonta(noppien_maara); // Robotin noppien arvonta
			Tulostus.robotinSumma(r_summa); // Tulostaa robotin summan
			Tulostus.tulos(pelaaja_summa, r_summa); // Katsoo kumpi voitti
			Tulostus.tarkistus(pelatut_kierrokset, pelien_maara); // Tarkistaa pelataanko viel‰
			Tulostus.putsaus(4000, 1); // Ruudun putsaus
		}
		renkaat.close(); // Sulkee renkaiden k‰‰ntymisen
		moottori.close(); // Sulkee liikkumiseen tarvittavan moottorin
		flip.close(); // Sulkee pˆyd‰n moottorin
		infraredSensor.close(); // Sulkee infrapunavastaanottimen
	}
}
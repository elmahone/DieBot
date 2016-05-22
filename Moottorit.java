package projekti;

/**
 * Luokassa m‰‰ritet‰‰n kaikki liikkumiseen viittaava<br>
 * Luodaan moottorit joita voidaan k‰ytt‰‰ muissa luokissa.<br>
 * M‰‰ritet‰‰n robotin ohjaus kauko-ohjaimella metodissa "liiku"<br>
 * M‰‰ritet‰‰n robotin toiminta riehumisen aikana metodissa "riehuMoodi"<br>
 * @author Jari Sandstrˆm
 * @author Toni Pehkonen
 * @date 13.10.2014
 */

import java.io.File;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.utility.Delay;

public class Moottorit extends Thread {
	EV3LargeRegulatedMotor renkaat;
	EV3LargeRegulatedMotor moottori;
	EV3LargeRegulatedMotor flip;
	EV3IRSensor infraredSensor;
	boolean riehu;
	
	/**
	 * S‰ikeen kutsumetodi joka k‰ynnist‰‰ liikkumisen, metodi k‰ynnistet‰‰n main-ohjelmassa komennolla "start"
	 */
	public void run(){
		this.liiku();
	}
	
	/**
	 * Luodaan moottorit<br>
	 * Moottorit suljetaan viimeist‰‰n main-ohjelman lopussa<br>
	 * Jos robotti menee riehuMoodiin, moottorit suljetaan sen lopussa
	 * @param renkaat
	 * @param moottori
	 * @param flip
	 * @param infraredSensor
	 */
	
	public Moottorit(EV3LargeRegulatedMotor renkaat, EV3LargeRegulatedMotor moottori, EV3LargeRegulatedMotor flip, EV3IRSensor infraredSensor){
		this.riehu = false;
		this.renkaat = renkaat;
		this.moottori = moottori;
		this.flip = flip;
		this.infraredSensor = infraredSensor;
	}
	/**
	 * Main-ohjelmassa kutsutaan setRiehua jos virheiden m‰‰r‰ nousee kolmeen, joka laittaa muuttujan "riehu" true-tilaan<br>
	 * Jos "riehu" on true-tilassa, metodissa "liiku" painetaan kuvitteellista painiketta -1<br>
	 * T‰m‰ aiheuttaa metodin "riehuMoodi" aktivoitumisen, jolloin robotti alkaa riehumaan
	 * 
	 */
	public void setRiehu(){
		this.riehu = true;
	}
	
	/**
	 * Robotin liikuttaminen kauko-ohjaimella <br>
	 * painike 1 (vasen yl‰kulma) robotti liikkuu eteenp‰in <br>
	 * painike 2 (vasen alakulma) robotti liikkuu taaksep‰in <br>
	 * painike 10 (vasen yl‰- ja alakulma) robotti pyst‰htyy <br>
	 * painike 3 (oikea yl‰kulma) robotti k‰‰nt‰‰ renkaitaan <br>
	 * painike 4 (oikea alakulma) robotti k‰‰nt‰‰ renkaitaan toiseen suuntaan <br>
	 * painike 5 (vasen ja oikea yl‰kulma) robotti nostaa pˆyd‰n ylˆs <br>
	 * painike 8 (vasen ja oikea alakulma) robotti laskee pˆyd‰n alas <br>
	 * painike-1 (kuvitteellinen painike jota painetaan kun virheit‰ on kolme) robotti alkaa riehua <br>
	 * Escape-painikkeella poistutaan ohjelmasta
	 * 
	 */
	public void liiku(){
		moottori.setSpeed(5000);
		renkaat.setSpeed(200);
		flip.setSpeed(200);
		while (Button.ESCAPE.isUp()){
			int remoteCommand = infraredSensor.getRemoteCommand(1);
			
			if (this.riehu == true){
				remoteCommand = -1;
			}
			
			switch (remoteCommand){
			case 1:
				moottori.backward();
				Delay.msDelay(500);
				break;
			case 2:
				moottori.forward();
				Delay.msDelay(500);
				break;
			case 3:
				renkaat.rotate(30);
				Delay.msDelay(500);
				break;
			case 4:
				renkaat.rotate(-30);
				Delay.msDelay(500);
				break;
			case 10:
				moottori.stop();
				renkaat.stop();
				break;
			case 5:
				flip.setSpeed(200);
				flip.rotate(180);
				Delay.msDelay(500);
				break;
			case 8:
				flip.rotate(-100);
				flip.setSpeed(10);
				flip.rotate(-50);
				Delay.msDelay(500);
				break;
			case -1:
				this.riehuMoodi();
				this.riehu = false;
			default:
			}
		}
	}
	
	/**
	 * Riehuminen, jos virheit‰ on 3<br>
	 * setSpeed m‰‰ritet‰‰n moottorin pyˆrimisnopeus<br>
	 * setRotation m‰‰ritet‰‰n kierrosten m‰‰r‰<br>
	 * soundFile m‰‰ritet‰‰n ‰‰nitiedosto<br>
	 * playSample soitetaan ‰‰nitiedosto<br>
	 */
	public void riehuMoodi(){
		LCD.clear();
		LCD.drawString("NYT RIITTI!", 0, 1);
		Delay.msDelay(500);
		File soundFile = new File("Terminator2.wav");
		Sound.playSample(soundFile, 100);
		Delay.msDelay(800);
		Sound.playSample(soundFile, 100);
		Delay.msDelay(800);
		Sound.playSample(soundFile, 100);
		Button.LEDPattern(1);
		Delay.msDelay(500);
		Button.LEDPattern(2);
		Delay.msDelay(500);
		Button.LEDPattern(3);
		Delay.msDelay(500);
		Button.LEDPattern(0);
		Delay.msDelay(500);
		Delay.msDelay(500);
		Button.LEDPattern(1);
		Delay.msDelay(500);
		Button.LEDPattern(2);
		Delay.msDelay(500);
		Button.LEDPattern(3);
		Delay.msDelay(500);
		Button.LEDPattern(0);
		Delay.msDelay(500);
		Delay.msDelay(2500);
		LCD.clear();
		LCD.drawString("GET REKT NUB!", 0, 1);
		moottori.setSpeed(9000);
		moottori.rotate(-180);
		flip.setSpeed(100);
		flip.rotate(180);
		moottori.setSpeed(5000);
		moottori.rotate(-360);
		flip.setSpeed(5000);
		flip.rotate(-180);
		flip.setSpeed(5000);
		flip.rotate(-180);
		flip.setSpeed(5000);
		flip.rotate(100, true);
		moottori.setSpeed(5000);
		moottori.rotate(1000);
		moottori.close();
		flip.close();
		renkaat.close();
		infraredSensor.close();
	}
}
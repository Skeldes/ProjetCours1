package warcraftTD.Monster;

import java.awt.Color;
import java.util.List;

import warcraftTD.Position;
import warcraftTD.StdDraw;

/**
 * Monstre volant de type aérien
 * 
 * le monste volant possède:
 * -3 points de vie
 * -une vitesse de 0.02;
 * 
 * Quand le monstre meurt le joueur gagne 8golds
 *
 */
public class FlightMonster extends Monster{
	
	/*
	 * CONSTRUCTEUR
	 */

	public FlightMonster(List<Position> path) {
		super(path, 0.04);
		this.speed = 0.01;
		this.type = TypeMonster.AIR;
		this.gold = 8;
		this.life = 3;
		this.damage = 1;
	}

	/*
	 * FONCTION PUBLIC
	 */
	
	@Override
	public void draw() {
		/*StdDraw.setPenColor(59, 138, 33);
		StdDraw.filledCircle(p.getX(), p.getY(), size);*/
		StdDraw.picture(p.getX(), p.getY(), "images/FlightMonster.png", this.size, this.size);
	}

}

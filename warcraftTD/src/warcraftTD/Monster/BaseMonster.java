package warcraftTD.Monster;

import java.util.List;

import warcraftTD.Position;
import warcraftTD.StdDraw;

/**
 * Monstre de base de type terrestre
 * 
 * le monste de base poss�de:
 * -5 points de vie
 * -une vitesse de 0.01;
 * 
 * Quand le monstre meurt le joueur gagne 5golds
 *
 */
public class BaseMonster extends Monster {
	
	/*
	 * CONSTRUCTEUR
	 */

	public BaseMonster(List<Position> path) {
		super(path, 0.04);
		this.speed = 0.0075;
		this.gold = 5;
		this.type = TypeMonster.GROUND;
		this.life = 5;
		this.damage = 1;
	}
	
	
	/*
	 * FONCTIONS PUBLIC
	 */
	
	/**
	 * Affiche un monstre qui change de couleur au cours du temps
	 * Le monstre est représenté par un cercle de couleur bleue ou grise
	 */
	public void draw() {
		/*StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledCircle(p.getX(), p.getY(), size);*/
		StdDraw.picture(p.getX(), p.getY(), "images/BaseMonster.png", this.size, this.size);
	}
}

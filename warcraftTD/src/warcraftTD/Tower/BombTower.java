package warcraftTD.Tower;

import java.util.ArrayList;

import warcraftTD.Position;
import warcraftTD.StdDraw;
import warcraftTD.Monster.Monster;
import warcraftTD.Projectile.Projectile;
import warcraftTD.Projectile.Bomb;

/**
 * Tour de bombe. Attaque les monstres terrestre. Tire des projectiles de type bombe
 * Une tour possède :
 * -Une porté de 0.2
 * -Une vitesse d'attaque de 20
 * -Un coût de construction de 60
 * -Un total de 3 niveaux
 */
public class BombTower extends Tower {

	/*
	 * CONSTRUCTEURS
	 */
	
	public BombTower(double x, double y, double size) {
		super(x, y, size);
		this.range = 0.2;
		this.speed = 20;
		this.antiAir = false;
		this.antiGround = true;
		this.updgradePrice = 30;
	}

	public BombTower(Position p, double size) {
		super(p, size);
		this.range = 0.2;
		this.speed = 20;
		this.antiAir = false;
		this.antiGround = true;
		this.updgradePrice = 30;
	}
	
	/*
	 * FONCTIONS PUBLIC
	 */

	@Override
	public Projectile shoot(Position p) {
		double dx = p.getX() - this.p.getX() ;
		double dy = p.getY() - this.p.getY();
		double ratioX = dx / (Math.abs(dx)+Math.abs(dy));
		double ratioY = dy / (Math.abs(dx)+Math.abs(dy));
		return new Bomb(ratioX, ratioY, range, new Position(this.p), this.level);
	}

	@Override
	public void draw() {
		/*StdDraw.setPenColor(134, 216, 235);
		StdDraw.filledSquare(p.getX(), p.getY(), size/2);*/
		String file = "images/BombTower"+(this.level+1)+".png";
		StdDraw.picture(p.getX(), p.getY(), file, this.size, this.size);
	}

	//Aucun effet
	@Override
	public void applyEffect(ArrayList<Monster> entities) {
	}
}


package warcraftTD.Tower;


import java.util.ArrayList;

import warcraftTD.Position;
import warcraftTD.StdDraw;
import warcraftTD.Monster.Monster;
import warcraftTD.Projectile.Projectile;
import warcraftTD.Projectile.Arrow;

/**
 * Tour d'archer. Attaque les monstres volants et terrestre. Tire des projectiles de type fléches
 * Une tour possède :
 * -Une porté de 0.3
 * -Une vitesse d'attaque de 15
 * -Un coût de construction de 50
 * -Un Total de 3 niveaux
 */
public class ArcheryTower extends Tower {

		
	/*
	 * CONSTRUCTEUR
	 */
	
	
	public ArcheryTower(double x, double y, double size) {
		super(x, y, size);
		this.range = 0.3;
		this.speed = 15;
		this.antiAir = true;
		this.antiGround = true;
		this.updgradePrice = 25;
	}

	public ArcheryTower(Position p, double size) {
		super(p, size);
		this.range = 0.3;
		this.speed = 15;
		this.antiAir = true;
		this.antiGround = true;
		this.updgradePrice = 25;
	}
	

	/*
	 * FONCTION PUBLIC
	 */

	@Override
	public Projectile shoot(Position p) {
		double dx = p.getX() - this.p.getX() ;
		double dy = p.getY() - this.p.getY();
		double ratioX = dx / (Math.abs(dx)+Math.abs(dy));
		double ratioY = dy / (Math.abs(dx)+Math.abs(dy));
		return new Arrow(ratioX, ratioY, range, new Position(this.p), this.level);
		
	}

	@Override
	public void draw() {
		/*StdDraw.setPenColor(new Color(191, 222, 16));
		StdDraw.filledRectangle(p.getX(), p.getY(), this.size/2, this.size/2);*/
		String file = "images/ArcheryTower"+(this.level+1)+".png";
		StdDraw.picture(p.getX(), p.getY(), file, this.size, this.size);
	}

	//Aucun effet
	@Override
	public void applyEffect(ArrayList<Monster> entities) {
	}
}

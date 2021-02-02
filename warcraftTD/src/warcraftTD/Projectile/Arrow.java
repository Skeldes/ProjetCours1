package warcraftTD.Projectile;

import warcraftTD.Position;
import warcraftTD.StdDraw;

/**
 *
 * Fléches
 * Une flèches à une vitesse de 0.04 et donnes des dammages de base de 2
 * Les flèches touchent les monstres terrestres et aériens
 *
 */
public class Arrow extends Projectile {

	public Arrow(double ratioX, double ratioY, double range, Position p, int levelTower) {
		super(ratioX, ratioY, range, p, levelTower);
		this.damage = 2;
		this.speed = 0.04;
	}
	
	
	@Override
	public void draw() {
		/*StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledCircle(p.getX(), p.getY(), 0.002);*/
		double angle = Math.toDegrees(Math.acos(ratioY/(Math.sqrt(ratioX*ratioX+ratioY*ratioY))));
		if(ratioX>0) angle = -angle;
		StdDraw.picture(p.getX(),p.getY(), "images/Arrow.png", 0.03, 0.03, angle);
	}

}

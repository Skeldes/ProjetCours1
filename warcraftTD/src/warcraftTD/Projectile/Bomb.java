package warcraftTD.Projectile;

import warcraftTD.Position;
import warcraftTD.StdDraw;

/**
*
* Bombes
* Une Bombe à une vitesse de 0.02 et donnes des dammages de base de 8
* Les bombes touchent les monstres terrestres
*
*/
public class Bomb extends Projectile {

	public Bomb(double ratioX, double ratioY, double range, Position p, int levelTower) {
		super(ratioX, ratioY, range, p, levelTower);
		this.damage = 8;
		this.speed = 0.02;
	}

	@Override
	public void draw() {
		/*StdDraw.setPenColor(73, 24, 125);
		StdDraw.filledCircle(this.p.getX(), this.p.getY(), 0.003);*/
		if(this.hit) {
			StdDraw.picture(p.getX(), p.getY(), "images/Boum.png", 0.03, 0.03);
		}else {
			StdDraw.picture(p.getX(), p.getY(), "images/Bomb.png", 0.02, 0.02);
		}
	}

}

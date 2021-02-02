package warcraftTD.Tower;

import java.util.ArrayList;

import warcraftTD.Position;
import warcraftTD.StdDraw;
import warcraftTD.Effect.Effect;
import warcraftTD.Effect.Freeze;
import warcraftTD.Monster.Monster;
import warcraftTD.Projectile.Projectile;

public class FreezeTower extends Tower {

	public FreezeTower(double x, double y, double size) {
		super(x, y, size);
		this.range = 0.1;
		this.updgradePrice = 40;
		this.antiAir = true;
		this.antiGround = true;
	}
	
	public FreezeTower(Position p, double size) {
		super(p, size);
		this.range = 0.1;
		this.updgradePrice = 40;
		this.antiAir = true;
		this.antiGround = true;
	}

	@Override 
	public Projectile shoot(Position p) {
		return null; //ne tire pas de projectile
	}

	@Override
	public void draw() {
		String file = "images/FreezeTower"+(this.level+1)+".png";
		StdDraw.picture(p.getX(), p.getY(), file, this.size, this.size);
	}

	@Override
	public void applyEffect(ArrayList<Monster> entities) {
		for(Monster m : entities) {
			boolean apply = true;
			for(Effect e : m.getEffects()) {
				if(e instanceof Freeze) apply = false;
			}
			if(apply)m.addEffects(new Freeze(m, this.level));//Si le monstre n'est pas sous l'effet Freeze, on lui applique
		}
	}
}

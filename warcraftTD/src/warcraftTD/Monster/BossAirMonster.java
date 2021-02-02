package warcraftTD.Monster;

import java.util.List;

import warcraftTD.Position;
import warcraftTD.StdDraw;

public class BossAirMonster extends Monster {

	public BossAirMonster(List<Position> path) {
		super(path, 0.08);
		this.speed = 0.0075;
		this.life = 40;
		this.type = TypeMonster.AIR;
		this.gold = 50;
		this.damage = 3;
	}
	
	

	@Override
	public void draw() {
		/*StdDraw.setPenColor(33, 196, 172);
		StdDraw.filledCircle(p.getX(), p.getY(), size);*/
		StdDraw.picture(p.getX(), p.getY(), "images/BossAirMonster.png", size, size);
	}

}

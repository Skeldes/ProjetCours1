package warcraftTD.Monster;

import java.util.List;

import warcraftTD.Position;
import warcraftTD.StdDraw;

public class BossSolMonster extends Monster {

	public BossSolMonster(List<Position> path) {
		super(path, 0.06);
		this.speed = 0.0040;
		this.life = 50;
		this.type = TypeMonster.GROUND;
		this.gold = 50;
		this.damage = 3;
	}
	
	

	@Override
	public void draw() {
		/*StdDraw.setPenColor(166, 33, 196);
		StdDraw.filledCircle(p.getX(), p.getY(), size);*/
		StdDraw.picture(p.getX(), p.getY(), "images/BossSolMonster.png", this.size, this.size);
	}

}

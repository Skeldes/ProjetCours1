package warcraftTD.Effect;

import warcraftTD.Monster.Monster;

public class Freeze extends Effect {

	private int cycleSinceStart;
	private double monsterOriginalSpeed;
	private double speedRatio;
	private Monster target;
	
	public Freeze(Monster m, int level) {
		this.duration = 200;
		this.die = false;
		this.target = m;
		this.monsterOriginalSpeed = m.getSpeed();
		this.cycleSinceStart = 0;
		this.speedRatio = 0.5 - (0.1*level);
	}
	
	
	@Override
	public void Apply() {
		if(this.cycleSinceStart == 0) {
			this.target.setSpeed(this.target.getSpeed()*speedRatio);
		}
		this.cycleSinceStart++;
		if(this.cycleSinceStart>=this.duration || this.die) {
			this.target.setSpeed(monsterOriginalSpeed);
			this.die = true;
		}
	}

}

package warcraftTD.Effect;

import warcraftTD.Monster.Monster;

public abstract class Effect {

	protected int duration;
	protected boolean die;
	
	
	public abstract void Apply();
	
	public boolean shouldDie() {
		return this.die;
	}
	
	public void setDie(boolean b) {
		this.die = b;
	}
	
}

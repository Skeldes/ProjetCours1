package warcraftTD.Monster;

import java.util.ArrayList;
import java.util.List;

import warcraftTD.Hitbox;
import warcraftTD.Position;
import warcraftTD.Effect.Effect;

/**
 *Class abstraite représentant les monstres 
 *
 */
public abstract class Monster {
	// Position du monstre Ã  l'instant t
	protected Position p;
	// Position du prochain point que le monstre cherche à atteindre
	protected Position nextP;
	// Position de l'objectif final du monstre
	protected Position goal;
	
	protected List<Position> path; //Le chemin que suit le monstre
	
	// Vitesse du monstre
	protected double speed;
	// Taille du monstre
	protected double size;
	
	// Boolean pour savoir si le monstre Ã  atteint le chateau du joueur
	protected boolean reached;
	//Boolean pour savoir si le monstre doit mourir (si il à atteint le chateau ou s'il n'a plus de PV)
	protected boolean shouldDie;
	// Boolean qui sert à savoir si le monstre doit changer direction ou non
	protected boolean shouldChangeDir;
	protected boolean waiting; //Est-ce que le monstre attend de sortir du spawn ou si il parcour le plateau
	protected int checkpoint = 0; //Nombre de Point atteint dans le path
	
	protected TypeMonster type;
	
	protected int life;
	protected int gold; //Nombre de gold que donne le monstre à sa mort	
	protected int damage;
	
	protected Hitbox hb;
	
	protected ArrayList<Effect> effects;
	
	/*
	 *CONSTRUCTEUR 	
	 */
	
	public Monster(List<Position> path, double size) {
		this.path = new ArrayList<Position>(path);
		this.size = size;
		this.p = new Position(this.path.get(0));
		this.hb = new Hitbox(this.p, this.size);
		this.nextP = this.path.get(1);
		this.shouldChangeDir = false;
		this.shouldDie = false;
		this.waiting = true;
		this.goal = this.path.get(path.size()-1);
		this.effects = new ArrayList<Effect>();
		}
	
	/*
	 * FONCTION PUBLIC
	 */
	
	/**
	 * DÃ©place le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
	 */
	public void move() {
		// Mesure sur quel axe le monstre se dirige.
		double dx = nextP.getX() - p.getX();
		double dy = nextP.getY() - p.getY();
		if (dy + dx != 0){
			// Mesure la distance Ã  laquelle le monstre Ã  pu se dÃ©placer.
			double ratioX = dx/(Math.abs(dx) + Math.abs(dy));
			double ratioY = dy/(Math.abs(dx) + Math.abs(dy));
			p.setX( p.getX() + (ratioX * speed));
			p.setY( p.getY() + (ratioY * speed));
		}
		if(this.hb.hit(new Hitbox(this.nextP, 2*this.size))) {
			shouldChangeDir = true;
		}
	}

	/**
	 * Met à jour la position du monstre et son état
	 */
	public void update() {
		if(!shouldDie) {
			move();
			updateEffects();
			if(this.hb.hit( new Hitbox(this.goal, 2*this.size))) {
				reached = true;
				shouldDie = true;
			}
		}
	}
	
	/**
	 * Reduit les point de vie du monstre de damage et vérifie si le monstre est toujours vivant
	 * @param damage un entier
	 */
	public void damageTaken(int damage) {
		this.life -= damage;
		if(this.life<=0) this.shouldDie = true;
	}
	
	
	/*
	 * FONCTION PRIVEE
	 */
	
	private void updateEffects() {
		for(Effect e : effects) {
			e.Apply();
		}
		for(int i = 0; i<effects.size(); i++) {
			if(effects.get(i).shouldDie()) effects.remove(i--);
		}
	}
	
	/*
	 * FONCTION ABSTRAITE
	 */
	
	/**
	 * Fonction abstraite qui sera instanciÃ©e dans les classes filles pour afficher le monstre sur le plateau de jeu.
	 */
	public abstract void draw();
	
	/*
	 * GETTEUR / SETTEUR
	 */
	
	public int getGold() {
		return this.gold;
	}
	
	public double getSpeed() {
		return speed;
	}

	public double getSize() {
		return size;
	}

	public boolean isReached() {
		return reached;
	}

	public boolean isShouldDie() {
		return shouldDie;
	}
	
	public void setShouldDie(boolean b) {
		this.shouldDie = b;
	}

	public boolean isShouldChangeDir() {
		return shouldChangeDir;
	}
	
	public void setShouldChangeDir(boolean b) {
		this.shouldChangeDir = b;
	}

	public boolean isWaiting() {
		return waiting;
	}
	
	public void setWaiting(boolean b) {
		this.waiting = b;
	}

	public TypeMonster getType() {
		return type;
	}

	public int getDamage() {
		return this.damage;
	}
	
	public Position getNextP() {
		return this.nextP;
	}
	
	public void setNextP(Position p) {
		this.nextP = p;
	}
	
	public int getCheckpoint() {
		return this.checkpoint;
	}
	
	public void setCheckpoint(int c) {
		this.checkpoint = c;
	}
	
	public Position getPosition() {
		return this.p;
	}
	
	public Hitbox getHitbox() {
		return this.hb;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void addEffects(Effect e) {
		this.effects.add(e);
	}
	
	public ArrayList<Effect> getEffects() {
		return this.effects;
	}
	
}

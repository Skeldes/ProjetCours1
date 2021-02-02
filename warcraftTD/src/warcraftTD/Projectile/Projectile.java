package warcraftTD.Projectile;

import java.util.List;

import warcraftTD.Position;
import warcraftTD.Monster.Monster;

/**
 * Class abstraite qui gère les projectiles 
 *
 */
public abstract class Projectile {

	
	protected Position p;
		
	//La direction du projectile selon les axes x et y
	protected double ratioX;
	protected double ratioY;
	
	
	//distance parcouru par appel à update()
	protected double speed;
	
	protected double range;
	protected double distanceTraveled; //Distance parcouru par le projectile depuis son lancement
	
	protected boolean shouldDie; //Si le projectile doit disparaitre 
	protected boolean hit; // Si le projectile à toucher quelque chose
	
	protected int damage;
	protected final double damageMultiplier = 0.25; //Par combien est multiplier les dommages du projectile en fonction du niveau de la tour (ex tour nv1 dommage * 1.25) 
	
	protected int levelTower; //Niveau de la tour qui envoie tire le projectile
	
	protected String image;
	
	/*
	 * CONSTRUCTEUR
	 */
	public Projectile(double ratioX, double ratioY, double range, Position p, int levelTower) {
		this.ratioX = ratioX;
		this.ratioY = ratioY;
		this.distanceTraveled = 0;
		this.range = range;
		this.shouldDie = false;
		this.hit = false;
		this.p = p;
		this.levelTower = levelTower;
	}
	
	/**
	 * Fonction qui met à jour le projectile
	 * @param monsters la listes des monstres actuellement présent sur le plateau
	 */
	public void update(List<Monster> monsters) {
		p.setX(p.getX()+ratioX*speed);
		p.setY(p.getY()+ratioY*speed);
		hit = hitMonster(monsters);
		distanceTraveled += Math.sqrt(Math.pow(ratioX*speed , 2) + Math.pow(ratioY * speed, 2));
		if(hit || distanceTraveled>=range) {
			shouldDie = true;
		}
	}
	
	
	//Fonction qui permet de savoir si le projectile à toucher un monstre et gère les dégats infligé si c'est le cas
	private boolean hitMonster(List<Monster> monsters) {
		for(Monster m : monsters) {
			if(m.getHitbox().hit(p)) {
				m.damageTaken(damage+(int)(damage*levelTower*damageMultiplier));
				return true;
			}
		}
		return false;
	}
	
	public abstract void draw();
	
	/*
	 * GETTEUR/SETTEUR
	 */
	
	public boolean isShouldDie() {
		return this.shouldDie;
	}
	
}

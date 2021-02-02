package warcraftTD.Tower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import warcraftTD.Position;
import warcraftTD.Monster.Monster;
import warcraftTD.Projectile.Projectile;

/**
 *Class abstrait qui gère les tours
 *
 */
public abstract class Tower{

	protected Position p; //position de la tour
	
	protected Double size; //taille de la tour
		
	protected int speed; //Vitesse d'attaque de la tour
	protected double speedMultiplier = 0.80; //multiplicateur de vitesse selon le niveau
	
	protected double range; //Portée de la tour
	private double rangeMultiplier = 1.33; //multiplicateur de portée
	
	protected int level; //niveau de la tour
	protected int updgradePrice; //Prix d'amélioration de la tour
	protected final int MAX_LEVEL = 3;
	
	//Quelle type de monstre la tour attaque
	protected boolean antiAir; 
	protected boolean antiGround;
	
	protected int nbCycleSinceLastShoot;
	protected boolean canShoot;
		
	protected String image;
	
	private static Map<String, Integer> costTower;
	static {
		costTower = new HashMap<String, Integer>();
		costTower.put("ArcheryTower", 50);
		costTower.put("BombTower", 60);
		costTower.put("FreezeTower", 70);
	}
	
	
	/*
	 * CONSTRUCTEURS
	 */
	
	public Tower(Position p, double size) {
		this.p = new Position(p);
		this.canShoot = true;
		this.size = size;
		this.level = 0;
		
	}
	
	public Tower(double x, double y, double size) {
		this.p = new Position(x,y);
		this.canShoot = true;
		this.size = size;
		this.level = 0;
	}
	
	/*
	 * FONCTIONS PUBLIC
	 */
	
	/**
	 * Fonction qui met à jours l'état de la tour
	 */
	public void update() {
		nbCycleSinceLastShoot++;
		if(nbCycleSinceLastShoot >= speed) {
			nbCycleSinceLastShoot = 0;
			canShoot = true;
		}else {
			canShoot = false;
		}
	}
	
	/**
	 * Fonction qui gère l'upgrade de la tour
	 * @return true si la tour a été up false sinon
	 */
	public boolean upgrade() {
		if(this.level+1<MAX_LEVEL) {
			this.level++;
			this.updgradePrice += this.updgradePrice;
			this.speed = (int)(this.speed * this.speedMultiplier);
			this.range = this.range * this.rangeMultiplier;
			return true;
		}
		return false;
	}
	
	
	
	/*
	 * FONCTIONS ABSTRAITES
	 */
	
	/**
	 * @param p la position vers la quelle le projectile va se dirigé
	 * @return un projectile
	 */
	public abstract Projectile shoot(Position p);

	public abstract void draw();
	
	/**
	 * Si la tour afflige un effet (exemple : freeze), la tour donne cette effet à toutes les monstres sensible a l'effet
	 * @param entity liste des entités sensible au effet
	 */
	public abstract void applyEffect(ArrayList<Monster> entities);
	
	/*
	 * GETTEUR / SETTEUR
	 */
	
	public int getUpgradPrice() {
		return this.updgradePrice;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public boolean canShoot() {
		return this.canShoot;
	}
	
	public Position getPosition() {
		return this.p;
	}
	
	public boolean isAntiAir() {
		return this.antiAir;
	}
	
	public boolean isAntiGround() {
		return this.antiGround;
	}
	
	public double getRange() {
		return this.range;
	}
	
	public static int getCost(String tower) {
		if(costTower.containsKey(tower)) {
			return costTower.get(tower);
		}
		else {
			return -1;
		}
	}
}

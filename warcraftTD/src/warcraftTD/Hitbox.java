package warcraftTD;

import warcraftTD.Tower.Tower;

/**
 * Hitbox
 * 
 * Objets qui sert à gérer les colisions
 */
public class Hitbox {
	
	
	private double size; //largeur de la hitbox
	private Position p; //Position de l'entité

	
	/*
	 * CONSTRUCTEUR
	 */
	
	public Hitbox(Position p, double size) {
		this.p = p;
		this.size = size;
	}
	
	/*
	 * FONCTIONS PUBLIC
	 */
	
	/**
	 * Vérifie si la hitbox touche la hitbox hb
	 * @param hb une hitBox
	 * @return true si la hitbox touche false sinon
	 */
	public boolean hit(Hitbox hb) {
		return ((this.p.getX() - this.size/2) >= (hb.p.getX() - hb.size/2) && (this.p.getX() + this.size/2) <= (hb.p.getX() + hb.size/2)
				&& (this.p.getY() - this.size/2) >= (hb.p.getY() - hb.size/2) && (this.p.getY() + this.size/2) <= (hb.p.getY() + hb.size/2));
	}
	
	/**
	 * Vérifie si la hitbox touche un point p
	 * @param p un point
	 * @return true si la hitbox touche le point false sinon
	 */
	public boolean hit(Position p) {
		return ((this.p.getX() - this.size/2) <= p.getX() && (this.p.getX() + this.size/2) >= p.getX())
				&& (this.p.getY() - this.size/2) <= p.getY() && (this.p.getY() + this.size/2) >= p.getY();
	}
	
	/**
	 * regarde si la hitbox se trouve dans la portée de la tour t
	 * @param t une tour
	 * @return return true si la hitbox se trouve dans la portée de la tour
	 */
	public boolean inRange(Tower t) {
		return cornerHit(new Position(p.getX()-(size/2), p.getY()-(size/2)), t) ||
				cornerHit(new Position(p.getX()+(size/2), p.getY()-(size/2)), t) ||
				cornerHit(new Position(p.getX()-(size/2), p.getY()+(size/2)), t) ||
				cornerHit(new Position(p.getX()+(size/2), p.getY()+(size/2)), t);
	}
	
	/*
	 * FONCTIONS PRIVEE
	 */
	
	//Fonction qui regarde si le point corner est dans la porté de la tour t
	private boolean cornerHit(Position corner, Tower t) {
		return Math.sqrt(Math.pow(corner.getX() - t.getPosition().getX(), 2) + Math.pow(corner.getY() - t.getPosition().getY(), 2))<=t.getRange();
	}
	
	/*
	 * GETTER / SETTER
	 */

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}
	
}

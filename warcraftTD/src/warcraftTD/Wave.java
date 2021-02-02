package warcraftTD;

import java.util.List;

import warcraftTD.Monster.Monster;
import warcraftTD.Monster.MonsterFactory;

import java.util.ArrayList;

/**
 * Class qui gère les vagues de monstre
 */
public class Wave {

	/*
	 * ATTRIBUT
	 */
	
	private ArrayList<Monster> monsters; //liste des monstres qui composent la vagues
	private boolean monsterWaiting; //Est-ce qu'il reste des monstres en attente 
	private int cycle; //nombre de cycle depuis le debut de la vague
	
	private boolean done; //Est-ce que la vague est fini ou non
	
	//temps en milliseconde entre deux monstres
	private final int cycleBetweenLaunch = 10;
	
	private World w; //le monde dans le quel se trouve la vague
	
	/*
	 * CONSTRUCTEUR
	 */
	
	public Wave(World w) {
		monsters = new ArrayList<Monster>();
		monsterWaiting = false;
		cycle = 0;
		this.w = w;
	}
	
	/*
	 * FONCTIONS PUBLIC
	 */
	
	/**
	 * 
	 * @param path le chemin que suit la vague
	 * @return le nombre de point de vie a retirer au joueur
	 */
	public void update(List<Position> path) {
		checkMonsterWaiting();
		if(monsterWaiting) {//On regarde si des monstres sont en attentes 
			if(cycle%cycleBetweenLaunch==0 && cycle>50) {
				for(Monster m : monsters) {
					if(m.isWaiting()) {
						m.setWaiting(false);
						break;
					}
				}
			}
		}
		
		for(Monster monster : monsters) {
			if(!monster.isWaiting()) {
				monster.update();
				if(monster.isReached()) {
					w.setLife(w.getLife() - monster.getDamage());
				}
				if(monster.isShouldChangeDir()) {
					if(monster.getCheckpoint()+1<path.size()) {
						monster.setCheckpoint(monster.getCheckpoint()+1);
						monster.setNextP(path.get(monster.getCheckpoint()));
						monster.setShouldChangeDir(false);	
					}
				}	
			}
		}
		for(int i = 0; i<monsters.size(); i++) {
			if(monsters.get(i).isShouldDie()) {
				if(!monsters.get(i).isReached()) w.setGold(w.getGold() + monsters.get(i).getGold());
				monsters.remove(i--);
			}
		}
		checkEnd();
		cycle++;
	}
	
	/**
	 * Rajoute le monstre m à la liste des monstres
	 * @param m un Monster
	 */
	public void AddMonster(Monster m) {
		monsters.add(m);
		monsterWaiting = true;
	}
	
	/*
	 * GETTEUR / SETTEUR
	 */
	public boolean isDone() {
		return done;
	}
	
	public ArrayList<Monster> getMonsters(){
		return this.monsters;
	}
	
	
	/*
	 * FONCTIONS PRIVEES
	 */
	
	
	private void checkMonsterWaiting() {
		for(Monster m : monsters) {
			if(m.isWaiting()) {
				monsterWaiting = true;
				return;
			}
		}
		monsterWaiting = false;
	}
	
	private void checkEnd() {
		done = monsters.isEmpty();
	}

	public void AddMonster(String n, List<Position> p) {
		Monster m = MonsterFactory.getFactory().createMonster(n, p);
		if(m!=null) monsters.add(m);
	}
	
}

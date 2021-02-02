package warcraftTD;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import warcraftTD.Monster.Monster;
import warcraftTD.Monster.MonsterFactory;
import warcraftTD.Monster.TypeMonster;
import warcraftTD.Projectile.Projectile;
import warcraftTD.Tower.ArcheryTower;
import warcraftTD.Tower.BombTower;
import warcraftTD.Tower.FreezeTower;
import warcraftTD.Tower.Tower;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class World implements Runnable {
	// l'ensemble des monstres, tours et projectiles pour gerer (notamment) l'affichage
	private List<Wave> waves = new ArrayList<Wave>();
	private List<Tower> towers = new ArrayList<Tower>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Position> path = new ArrayList<Position>();
		
	private Wave currentWave;
	private int tickSinceLastWave;
	private final int tickBetweenWave = 200; //nombre tick entre deux vagues
	
	// Position par laquelle les monstres vont venir
	private Position spawn;
	// Position du chateau
	private Hitbox castle;
	
	// Information sur la taille du plateau de jeu
	private int width;
	private int height;
	private int nbSquareX;
	private int nbSquareY;
	private double squareWidth;
	private double squareHeight;
	
	// Nombre de points de vie du joueur
	private int life = 20;
	
	private int gold;
	// Commande sur laquelle le joueur appuie (sur le clavier)
	private char key;
	
	// Condition pour terminer la partie
	private volatile boolean end = false;
	
	
	private volatile boolean pause;
	private boolean start = false;
	
	// Variables sauvegardant les paramètre du jeu
	private String pathFileName;
	private String waveFileName;
	private int initialLife = 20;
	private int initialGold;
	
	
	/*
	 * *******************************CONSTRUCTEUR********************************************************************
	 */
	
	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases donnÃ©es
	 * @param width
	 * @param height
	 * @param nbSquareX
	 * @param nbSquareY
	 * @param startSquareX
	 * @param startSquareY
	 */
	public World(int width, int height, int nbSquareX, int nbSquareY, String pathFileName, String waveFileName, int gold) {
		this.width = width;
		this.height = height;
		this.nbSquareX = nbSquareX;
		this.nbSquareY = nbSquareY;
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		this.gold = gold;
		this.initialGold = gold;
		this.pathFileName = pathFileName;
		this.waveFileName = waveFileName;
		init(this.pathFileName, this.waveFileName);
		castle = new Hitbox(new Position(path.get(path.size()-1)), squareWidth);
		this.pause = false;
	}
	
	
	/*
	 * ***********************************FONCTIONS PUBLIC*******************************************************
	 */
	
	 
	 /*
	  * ********************************** FONCTIONS UPDATE ******************************************************
	  */
	 
	 
	 /**
	  * Pour chaque tour de la liste des tours, utilise la fonction update() qui appelle la fonction shoot() de Tower.
	  * Modifie le compteur de tour entre deux tirs à l'aide du paramètre nbCycleSinceLastShoot
	  */
	 public void updateTowers() {
		Iterator<Tower> i = towers.iterator();
		Tower t;
		while (i.hasNext()) {
			 t = i.next();
			 t.update();
			 Monster m = monsterInRange(t);
			 if(t.canShoot() && m != null) {
				 Projectile p = t.shoot(m.getPosition());
				 if(p!=null) projectiles.add(p);
			 }
			 t.applyEffect(monstersInRange(t));
		 }
	 }
	 
	 /**
	  * Pour chaque projectile dans a liste des projectiles, appelle la fonction update de Projectile puis retire les projectiles qui ont fini leur parcour
	  */
	public void updateProjectiles() {
		 for(Projectile l : projectiles) {
			 l.update(currentWave.getMonsters());
		 }
		 for(int i = 0; i<projectiles.size(); i++) {
			 if(projectiles.get(i).isShouldDie()) projectiles.remove(i--);
		 }
	 }
	 
	/**
	 * Fonction qui gère les vagues
	 */
	 public void updateWave() {
		 if(!currentWave.isDone()) {
				currentWave.update(path);
				if(currentWave.isDone()) {
					this.tickSinceLastWave = 0;
				}
			}else {
				this.tickSinceLastWave++;
				if(this.tickSinceLastWave >= this.tickBetweenWave) {
					waves.remove(0);
					if(!waves.isEmpty()) {
						currentWave = waves.get(0);
						System.out.println("wave start");
					}
				}
			}
	 }
	 
	 /**
	  * Met à  jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
	  * @return les points de vie restants du joueur
	  */
	 public int update() {
		if(!this.pause && this.start) {
			updateWave();
			updateTowers();
			updateProjectiles();
		}
		return life;
	 }
	 
	 /*
	  * ********************************************FONCTION GESTION INPUT***********************************************
	  */
	 
	 
	/**
	 * RÃ©cupÃ¨re la touche appuyÃ©e par l'utilisateur et affiche les informations pour la touche sÃ©lÃ©ctionnÃ©e
	 * @param key la touche utilisÃ©e par le joueur
	 */
	public void keyPress(char key) {
		key = Character.toLowerCase(key);
		this.key = key;
		switch (key) {
		case 'a':
			System.out.println("Arrow Tower selected ("+Tower.getCost("ArcheryTower")+"g).");
			break;
		case 'b':
			System.out.println("Bomb Tower selected ("+Tower.getCost("BombTower")+"g).");
			break;
		case 'f':
			System.out.println("Freeze Tower selected ("+Tower.getCost("FreezeTower")+"g).");
			break;
		case 'e':
			System.out.println("Evolution selected.");
			break;
		case 'r':
			System.out.println("Sell selected");
			break;
		case 's':
			if(!this.start) this.start = true;
			this.key = 0;
			System.out.println("Starting game!");
			break;
		case 'p':
			if(this.pause) {
				this.pause = false;
				System.out.println("Game resume");
			}
			else {
				this.pause = true;
				System.out.println("Game paused");
			}
			break;
		case 'q':
			this.end = true;
			System.out.println("Exiting.");
			break;
		}
	}



	/**
	 * VÃ©rifie lorsque l'utilisateur clique sur sa souris qu'il peut: 
	 * 		- Ajouter une tour Ã  la position indiquÃ©e par la souris.
	 * 		- AmÃ©liorer une tour existante.
	 * Puis l'ajouter Ã  la liste des tours
	 * @param x
	 * @param y
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		if(!this.pause) {//Si le jeu est en pause on ne peut pas Ajouter une tour ou améliorer une existante
			if(key != 0) {
				if(!towerPresent(normalizedX, normalizedY) && valideLocation(normalizedX, normalizedY)) {
					switch (key) {
					case 'a':
						if(this.gold>=Tower.getCost("ArcheryTower")) {
							towers.add(new ArcheryTower(normalizedX, normalizedY, squareWidth));
							gold -= Tower.getCost("ArcheryTower");
							System.out.println("gold : "+this.gold);
						}
						else {
							System.out.println("not enought gold");
						}
						key = 0;
						break;
					case 'b':
						if(this.gold>=Tower.getCost("BombTower")) {
							towers.add(new BombTower(normalizedX, normalizedY, squareWidth));
							gold -= BombTower.getCost("BombTower");
							System.out.println("gold : "+this.gold);
						}
						else {
							System.out.println("not enought gold");
						}
						key = 0;
						break;
					case 'f':
						if(this.gold>=Tower.getCost("FreezeTower")) {
							towers.add(new FreezeTower(normalizedX, normalizedY, squareWidth));
							gold -= Tower.getCost("FreezeTower");
							System.out.println("gold : "+this.gold);
						}else {
							System.out.println("Gold Insuffisant");
						}
						key = 0;
						break;
					}
				}
				if(key == 'e') {
					Tower t = getTower(normalizedX, normalizedY);
					if(t != null) {
						if(t.getUpgradPrice()<=this.gold) {
							int cost = t.getUpgradPrice();
							if(t.upgrade()) {
								System.out.println("Tour upgrade niveau " + t.getLevel());
								this.gold -= cost;
							}
							else System.out.println("La tour est déjà au niveau max");
						}
						else {
							System.out.println("Or insuffisant");
						}
						key = 0;
					}else {
						System.out.println("Il n'y a pas de tour ici");
					}
				}
				else if(key == 'r') {
					Tower t = getTower(normalizedX, normalizedY);
					if(t!=null) {
						this.gold += Tower.getCost(t.getClass().getSimpleName())/2;
						this.towers.remove(t);
					}
				}
			}
		}
	}
	
	/**
	 * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les diffÃ©rentes possibilitÃ©s 
	 * offertes au joueur pour intÃ©ragir avec le clavier
	 */
	public void printCommands() {
		System.out.println("Press A to select Arrow Tower (cost "+Tower.getCost("ArhceryTower")+"g).");
		System.out.println("Press B to select Cannon Tower (cost "+Tower.getCost("BombTower")+"g).");
		System.out.println("Press F to select Freeze Tower (cost "+Tower.getCost("FreezeTower")+"g).");
		System.out.println("Press E to update a tower (cost 40g).");
		System.out.println("Click on the grass to build it.");
		System.out.println("Press S to start.");
		System.out.println("Press P to pause the game");
	}
	
	/**
	 * RÃ©cupÃ¨re la touche entrÃ©e au clavier ainsi que la position de la souris et met Ã  jour le plateau en fonction de ces interractions
	 */
	
	/*
	 * *********************************FONCTION RUN***************************************
	 */
	public void run() {
		printCommands();
		while(!end) {
			if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}
			
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				StdDraw.pause(50);
			}
			long now = System.currentTimeMillis();
			synchronized(this){
				update();	
			}
			try {
				Thread.sleep(20-(now-System.currentTimeMillis()));//On fait commencé un tic toutes les 20ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(life <= 0 || waves.isEmpty()) end = true;
		}
		if(life>0 && this.waves.isEmpty()) {
			System.out.println("You win !"); 
		}else {
			System.out.println("You loose");
		}
	}
	
	
	/*
	 * ************************************FONCTIONS PRIVEES****************************************
	 */
	
	/*
	 * ********************FONCTIONS LIEES AUX CHEMINS**************************************
	 */
	
	/**
	 * Fonction qui récupére depuis un fichier un tableau de dimension deux représentant le chemin à parcourir
	 * @param fileName le nom du fichier
	 * @return un tableau à deux dimensions
	 */
	private int[][] getDataPath(String fileName) {
		int[][] res = new int[nbSquareY][nbSquareX];
		Scanner sc = null;
		try {
			File f = new File(fileName);
			sc = new Scanner(f);
			int line = 0;
			while(sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				if(currentLine.compareTo("fin")==0) {
					int startSquareX = sc.nextInt();
					int startSquareY = sc.nextInt();
					spawn = new Position(startSquareX * squareWidth + squareWidth / 2, startSquareY * squareHeight + squareHeight / 2);
					break;
				}
				String[] temp = currentLine.split(",");
				for(int i = 0; i<temp.length; i++) {
					res[line][i] = Integer.parseInt(temp[i]);
				}
				line++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			sc.close(); 
		}
		return res;
	}
	
	private void initPath(int[][] data) {
		this.path = calPath(data, 1, 0);
	}
	
	/**
	 * Calcul de manière récursive le chemin que doit parcourir les monstres depuis un tableau d'entier de dimension 2
	 * @param dataPath un tableau de dimension 2, 
	 * @param currentX 
	 * @param currentY 
	 * @return une liste de position par les quelles doivent passer les monstres 
	 */
	private List<Position> calPath(int[][] dataPath, int currentX, int currentY) {
		ArrayList<Position> res = new ArrayList<Position>();
		res.add(new Position(currentX * squareWidth + squareWidth / 2,((nbSquareY-1) - currentY) * squareHeight + squareHeight / 2 ));
		dataPath[currentY][currentX] = 0;
		if((currentY + 1) < dataPath.length && dataPath[currentY+1][currentX] == 1) {
			res.addAll(calPath(dataPath, currentX, currentY+1));
		}
		else if((currentX + 1) < dataPath[currentY].length && dataPath[currentY][currentX+1] == 1) {
			res.addAll(calPath(dataPath, currentX+1, currentY));
		}
		else if((currentY - 1) >= 0 && dataPath[currentY-1][currentX] == 1) {
			res.addAll(calPath(dataPath, currentX, currentY-1));
		}
		else if((currentX - 1) >= 0 && dataPath[currentY][currentX-1] == 1) {
			res.addAll(calPath(dataPath, currentX-1, currentY));
		}
		return res;
	}
	
	
	/*
	 * ***************FONCTION D'INITIALISATION**********************	
	 */
	
	/**
	 * Fonction qui initialise le chemin depuis le fichier fileName et les différentes vagues
	 * @param fileName le nom d'un fichier contenant le chemin à suivre
	 */
	private void init(String pathFileName, String waveFileName) {
		initPath(getDataPath(pathFileName));
		if(waveFileName.compareTo("")!=0)
			initWave(waveFileName);
		else{
			initWaveRandom();
		}
	}
	
	/*
	 * *****************************FONCTIONS LIEES AUX TOURS ************************************
	 */
	
	private void initWaveRandom() {
		Random rd = new Random();
		int nbWave = 5+rd.nextInt(20); //on choisi aléatoirement un nombre de vague entre 5 et 24
		this.waves = new ArrayList<Wave>();
		MonsterFactory mf = MonsterFactory.getFactory();
		for(int i = 0; i<nbWave; i++) {
			int nbMonster = (4+i)+rd.nextInt(5);
			Wave w = new Wave(this);
			for(int j = 0; j<nbMonster; j++) {
				int randomNumber = rd.nextInt(100);
				if(randomNumber<45) w.AddMonster(mf.createMonster("1", this.path));
				else if(randomNumber<90) w.AddMonster(mf.createMonster("2", this.path));
				else if(randomNumber<95) w.AddMonster(mf.createMonster("3", this.path));
				else w.AddMonster(mf.createMonster("4", this.path));
			}
			this.waves.add(w);
		}
		this.currentWave=this.waves.get(0);
	}

	private void initWave(String waveFileName) {
		Scanner sc = null;
		try {
			File f = new File(waveFileName);
			sc = new Scanner(f);
			while(sc.hasNext()) {
				String[] l = sc.nextLine().split(",");
				Wave w = new Wave(this);
				for(String n : l) {
					w.AddMonster(n, path);
				}
				waves.add(w);
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
		if(!waves.isEmpty())
			currentWave = waves.get(0);
	}


	//Regarde si une tour est présente en (x,y)
	public boolean towerPresent(double x, double y) {
		for(Tower t : towers) {
			Position p = new Position(x, y);
			if(t.getPosition().equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	//regarde si il est possible de mettre une tour en (x,y)
	public boolean valideLocation(double x, double y) {
		for(Position p : path) {
			if(p.getX() == x && p.getY() == y) return false;
		}
		return true;
	}
	
	//recupére le tour en (x,y) si elle existe
	private Tower getTower(double x, double y) {
		for(Tower t : towers) {
			Position p = new Position(x, y);
			if(t.getPosition().equals(p)) return t;
		}
		return null;
	}
	
	/**
	 * Regarde si un monstre est dans la porté de la tour t et si oui le renvoie
	 * @param t la tour
	 * @return un Monster si il est à porté null sinon
	 */
	 private Monster monsterInRange(Tower t) {
		for(Monster m : currentWave.getMonsters()) {
			if(!m.isWaiting() && m.getHitbox().inRange(t) && ((m.getType() == TypeMonster.AIR && t.isAntiAir())||(m.getType() == TypeMonster.GROUND && t.isAntiGround()))) return m;
		}
		return null;
	}
	 
	 /**
	  * Renvoie tous les monstres possédant l'interface Sensitive à porté d'une tour donnée en paramètre
	  * @param t une Tower
	  * @return une liste de Sensitive à porté de la Tower t
	  */
	 private ArrayList<Monster> monstersInRange(Tower t){
		 ArrayList<Monster> res = new ArrayList<Monster>();
		 for(Monster m : currentWave.getMonsters()) {
			 if(!m.isWaiting() && m.getHitbox().inRange(t) && ((m.getType() == TypeMonster.AIR && t.isAntiAir())||(m.getType() == TypeMonster.GROUND && t.isAntiGround()))) res.add(m);
		 }
		 return res;
	 }
	 /*
	  * Fonctions liées au jeu 
	  */
	 
		
		public void commande(String input) {
			String[] command = input.split(":");
			switch(command[0]) {
			case "help":
				if(command.length==1) {
					System.out.println("gold:[montant] - obtenir 'montant' d'or (ex : gold:500)");
					System.out.println("life:[montant] - obtenir 'montant' de points de vie  (ex: life:50)");
					System.out.println("nextWave - lance la vague suivante");
					System.out.println("restart - recommence la partie");
					}else {
						switch(command[1]) {
							case "gold":
								System.out.println("Commande qui ajoute de l'or au joueur");
								System.out.println("Prend un arguments : Un entier représentant le nombre de pièce d'or à ajouter au joueur");
								break;
							case "life":
								System.out.println("Commande qui ajoute des points de vie au joueur");
								System.out.println("Prend un arguments : Un entier représentant le nombre de point de vie à ajouter au joueur");
								break;
							case "nextWave":
								System.out.println("Commande qui lance la vague suivante");
								System.out.println("Ne prend pas d'argument");
								System.out.println("La commande marche que si la vague courante est fini ET qu'il y a une autre vague en attente");
								break;
							case "restart":
								System.out.println("Commande qui relance la partie avec les mêmes configurations");
								System.out.println("Ne prend pas d'argument");
								break;
						}
					}
				break;
			case "gold":
				if(command.length>1) {
					try {
						this.gold += Integer.parseInt(command[1]);
					}catch(NumberFormatException e) {
						System.out.println("Veuillez entrez un nombre en argument");
					}
				}else {
					System.out.println("Argument manquant");
				}
				break;
			case "life":
				if(command.length>1) {
					try {
						this.life += Integer.parseInt(command[1]);
					}catch(NumberFormatException e) {
						System.out.println("Veuillez entrez un nombre en argument");
					}
				}else {
					System.out.println("Argument manquant");
				}
				break;
			case "nextWave":
				if(this.currentWave.getMonsters().isEmpty() && this.waves.size()>1) {
					this.tickSinceLastWave = this.tickBetweenWave;
				}
				else {
					System.out.println("Commande invalide pour l'état du jeu actuel");
				}
				break;
			case "restart":
				this.currentWave = null;
				this.towers = new ArrayList<Tower>();
				init(this.pathFileName, this.waveFileName);
				this.start = false;
				this.end = false;
				this.gold = this.initialGold;
				this.life = this.initialLife;
				System.out.println("game restart");
				break;
			default:
				System.out.println("commande invalide");
			}
		}
	 
	 /*
	  * Getteur et Setteur
	  */
	 public void setLife(int life) {
		 this.life = life;
	 }
	 
	 public int getLife() {
		 return this.life;
	 }
	 
	 public void setGold(int gold) {
		 this.gold = gold;
	 }
	 
	 public int getGold() {
		 return this.gold;
	 }
	 
	 public boolean isEnd() {
		 return this.end;
	 }


	public List<Tower> getTowers() {
		return towers;
	}


	public List<Projectile> getProjectiles() {
		return projectiles;
	}


	public List<Position> getPath() {
		return path;
	}


	public Wave getCurrentWave() {
		return currentWave;
	}


	public int getNbSquareX() {
		return nbSquareX;
	}


	public int getNbSquareY() {
		return nbSquareY;
	}


	public double getSquareWidth() {
		return squareWidth;
	}


	public double getSquareHeight() {
		return squareHeight;
	}


	public char getKey() {
		return key;
	}
	 
	public boolean isPause() {
		return this.pause;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	 
}
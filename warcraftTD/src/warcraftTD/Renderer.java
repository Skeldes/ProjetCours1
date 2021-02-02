package warcraftTD;

import java.awt.Font;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import warcraftTD.Monster.Monster;
import warcraftTD.Projectile.Projectile;
import warcraftTD.Tower.Tower;

/**
 * Class qui gère le rendu du jeu. On sépare l'horloge du rendu de l'horloge d'update en utilisant 2 threads. Cela permet d'avoir une fréquences d'affichage différentes
 * de la fréquence d'update. 
 */
public class Renderer implements Runnable {
	
	private World w;
	
	private final int fps = 60;
	
	private long lastRenderer;
	
	public Renderer(World w) {
		this.w = w;
		StdDraw.setCanvasSize(this.w.getWidth(), this.w.getHeight());
		StdDraw.enableDoubleBuffering();
		this.lastRenderer = System.currentTimeMillis();
	}

	
	public void run() {
		while(!w.isEnd()) {
			long now = System.currentTimeMillis();
			if((now - lastRenderer) >= 1000/fps) {
				lastRenderer = now;
				try {
					StdDraw.clear();
					drawBackground();
					drawPath();
					drawMonster();
					drawTower();
					drawProjectile();
					drawInfos();
					drawMouse();
					StdDraw.show();
				}catch(ConcurrentModificationException e) {
					//Une erreur ? je vois pas ce qui vous fait dire ça
				}
			}
		}
	}
	
	private void drawBackground() {
		for(int i = 0; i<w.getNbSquareX(); i++) {
			for(int j = 0 ; j<w.getNbSquareY(); j++) {
				 StdDraw.picture(i * w.getSquareWidth() + w.getSquareWidth() / 2, j * w.getSquareHeight() + w.getSquareHeight() / 2, "images/herbe.png", w.getSquareWidth(), w.getSquareHeight());
			}
		}
	}
	
	private void drawPath() {
		for(Position p : w.getPath()) {
			StdDraw.picture(p.getX(), p.getY(), "images/Sol.png", w.getSquareWidth(), w.getSquareHeight());
		}
	}
	
	private void drawMonster() {
		Iterator<Monster> ite = w.getCurrentWave().getMonsters().iterator();
		Monster m;
		while(ite.hasNext()) {
			m = ite.next();
			if(!m.isWaiting())
				m.draw();
		}
	}
	
	private void drawTower() {
		Iterator<Tower> ite = w.getTowers().iterator();
		Tower t;
		while(ite.hasNext()) {
			t = ite.next();
			t.draw();
		}
	}
	
	private void drawProjectile() {
		Iterator<Projectile> ite = w.getProjectiles().iterator();
		Projectile p;
		while(ite.hasNext()) {
			p = ite.next();
			p.draw();
		}
	}
	
	private void drawInfos() {
		StdDraw.setPenColor(StdDraw.BLACK);
		 Font f = new Font("Sans Serif", Font.PLAIN, 20);
		 StdDraw.setFont(f);
		 StdDraw.text(0.95, 0.95, "Or :"+w.getGold());
		 StdDraw.text(0.95, 0.92, "Pv :"+w.getLife());
		 if(w.isPause()) {
			 f = new Font("Sans Serif", Font.BOLD, 100);
			 StdDraw.setFont(f);
			 StdDraw.text(0.5, 0.5, "PAUSE");
		 }
	}
	
	
	 private void drawMouse() {
		double normalizedX = (int)(StdDraw.mouseX() / w.getSquareWidth()) * w.getSquareWidth() + w.getSquareWidth() / 2;
		double normalizedY = (int)(StdDraw.mouseY() / w.getSquareHeight()) * w.getSquareHeight() + w.getSquareHeight() / 2;
		if(!w.isPause()) {
			switch (w.getKey()) {
			case 'a' : 
				if(w.valideLocation(normalizedX, normalizedY) && !w.towerPresent(normalizedX, normalizedY)) {
					/*StdDraw.setPenColor(168, 71, 35);
					StdDraw.filledSquare(normalizedX, normalizedY, w.getSquareWidth()/4);*/
					StdDraw.picture(normalizedX, normalizedY, "images/ArcheryTower1.png", w.getSquareWidth(), w.getSquareHeight());
				}
				break;
			case 'b' :
				if(w.valideLocation(normalizedX, normalizedY) && !w.towerPresent(normalizedX, normalizedY)) {
					/*StdDraw.setPenColor(139, 168, 35);
					StdDraw.filledSquare(normalizedX, normalizedY, this.w.getSquareWidth()/4);*/
					StdDraw.picture(normalizedX, normalizedY, "images/BombTower1.png", w.getSquareWidth(), w.getSquareHeight());
				}
				 break;
			case 'f':
				if(w.valideLocation(normalizedX, normalizedY) && !w.towerPresent(normalizedX, normalizedY)) {
					StdDraw.picture(normalizedX, normalizedY, "images/FreezeTower1.png", w.getSquareWidth(), w.getSquareHeight());
				}
				break;
			}
		}
	 }
	
}

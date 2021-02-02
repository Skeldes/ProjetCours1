package warcraftTD;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int width = 1200;
		int height = 800;
		int nbSquareX = 10;
		int nbSquareY = 10;	
		String map = "";
		String difficulte = null;
		int gold = 0;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Bienvenu sur ce jeu mgl");
		
		while(true) {
			System.out.println("Sur quelle map voulez-vous jouer ?");
			System.out.println("-Map 1 : 1");
			System.out.println("-Map 2 : 2");
			String res = sc.next();
			switch(res) {
			case "1":
				map = "res/map1";
				break;
			case "2":
				map = "res/map2";
				break;
			}
			if(map.compareTo("")!=0) break;
		}
		
		while(true) {
			System.out.println("Avec quelle difficulté voulez-vous jouer ?");
			System.out.println("-Niveau 1 : 1");
			System.out.println("-Niveau 2 : 2");
			System.out.println("-Niveau 3 : 3");
			System.out.println("-Niveau 4 : 4");
			System.out.println("-Vague aléatoire : ale");
			String res = sc.next();
			switch(res) {
			case "1":
				difficulte = "res/wave1";
				gold = 300;
				break;
			case "2":
				difficulte = "res/wave2";
				gold = 300;
				break;
			case "3":
				difficulte = "res/wave1";
				gold = 150;
				break;
			case "4":
				difficulte = "res/wave2";
				gold = 150;
				break;
			case "ale":
				difficulte = "";
				gold = 150;
				break;
			}
			if(difficulte!=null) break;
		}
				
		World w = new World(width, height, nbSquareX, nbSquareY, map, difficulte, gold);
		Renderer r = new Renderer(w);
		
		Thread world = new Thread(w);
		Thread renderer = new Thread(r);
		world.start();
		renderer.start();
		while(!w.isEnd()) {
			if(sc.hasNext()) {
				w.commande(sc.next());
			}
		}
		sc.close();
	}
}


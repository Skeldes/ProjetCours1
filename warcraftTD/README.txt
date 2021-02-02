Feuilletin Thomas 
Saur Romain


Nous avons implement� le jeu de base :
	-Un monstre de type terrestre (baseMonster)
	-Un monstre de type a�rien (FlightMonster)
	
	-Une tour qui tire des fl�ches (ArcheryTower)
	-Une tou qui tire des bombes (BombTower)
	
	-Un syst�me permettant d'am�liorer les tours de 2 niveau
	-Un syst�me qui g�re les diff�rentes vagues de monstres (Wave)
	
Au jeu de base nous avons implementer :
	-Deux boss :
		-Un boss terrestre poss�dant plus de pv que le monstre terrestre de base ma va plus lentement (BossSolMonster)
		-Un boss a�rien poss�dant plus de pv que le monstre de a�rien de base ma va l�gerement plus lentement (BossAirMonster)
	Les deux boss inflige plus de d�g�t au joueur (1 d�g�t pour les monstres de base et 3 pour les boss)
	
	-Une tour qui g�le (FreezeTower)
		Cette tour inflige un effet freeze au monstre entrant de son p�rim�tre qui les ralenties d'un certain pourcentage en fonction du niveau de la tour.
		L'effet dure 200tic.
	
	-La possibilit� de vendre une tour via la 'R'. 
		La vente d'une tour permet de r�cup�r� un montant fixe d'or d�pendant du prix de la tour. Se montant ne change pas en fonction du niveau de la tour.
		
	-La possibilit� de mettre le jeu en pause via a touche 'P'.
		Lorsque le jeu est en pause le rendu continue. Il n'est pas possible de poser/am�liorer/vendre une tour.
		Pour enlever la pause il suffit de re appuyer sur 'p'
		
	-Un syst�me de s�lection de niveau et de difficult�
		-Il y a deux maps enregistrer dans les fichier map1 et map2
			Le fichier commence avec la map. Les 1 representant le chemin
			A la fin de la map le mot "fin" permet de signaler au programme que c'est la fin de la map
			Le fichier se termine par les coordonn�es X puis Y du d�part du chemin. Avec ces coordonn�e et la map le programme calcul le chemin tout seul
		-Il y a 5 niveau de difficult�
			Les 4 premiers utilise des vagues pr� enregistrer dans des fichiers (wave1 et wave2)
				-1 wave1 et 300 pi�ces d'or
				-2 wave2 et 300 pi�ces d'or
				-3 wave1 et 150 pi�ces d'or
				-4 wave2 et 150 pi�ces d'or
			Le dernier niveau de difficult�:
				Le joueur commence avaec 150 pi�ces d'or
				Le nombre de vagues et leur composition sont g�n�r� al�atoirement
	
	-Un syst�me de commande
		Lorsque le jeu tourne il est toujours possible d'�crire des choses dans la console. Ainsi le programme reconnait quelques commandes :
			-help : Commande qui permet d'obtenir la liste compl�te des commandes ainsi qu'une br�ve description de chacune.
			-help:[commande] : Commande qui permet des infos plus d�taill�es sur la commande
			-gold:[montant]/life:[montant] : Commande qui permet d'incr�menter la variable choisi du montant choisi (ex l'or du joueur de 50 pi�ces avec gold:50 )
			-nextWave : Commande qui permet de passer � la vague suivante sans attendre le temps entre deux vagues
			-restart : Commande qui permet de relancer la partie avec les param�tres qui ont �t� choisi � la base. Si la difficult� est al�atoire, les vagues sont reg�n�r� de mani�re al�atoire.
		
Nous avons aussi apporter quelques modifications � la structure du jeu:
	-Nous avons pass� l'ensemble des variables public en private en ajoutant des getteurs et setteurs pour les variables le n�cessitant. Nous respectons ainsi 
	le principe d'Encapsulation.
	-Nous avons sortie du code les donn�es de jeu (structure des vagues, maps) en les mettant dans des fichiers � part. Ce qui permet de les modifies simplement et d'en ajouter 
	autant que l'on veut sans alourdir le code.
	-Nous avons ajouter une factory pour les cr�ations des monstres pour facilit� l'ajout de nouveau type de monstre.
	-Nous avons s�par� le moteur de jeu du moteur de rendu. chacun tournant sur un thread diff�rent. Cela nous permet d'avoir une fr�quence de rendu diff�rente de la fr�quence d'update.
	Lors de la s�paration nous avons eu quelques probl�mes avec l'erreur "ConcurrentModificationException" dans le thread de rendu. En effet il arrivait que le thread du jeu modifi� un
	element du jeu et qu'au m�me moment le thread de rendu essayer d'afficher cet element ce qui caus� cette erreur et le programme s'arr�tait. Ne trouvant pas de moyen de r�gler ce probl�me
	nous avons d�cid� d'entourer les fonctions de rendu d'un try/catch pour capturer l'erreur et laisser le programme continuer. L'erreur ne changeant rien au performence ni au 
	ressenti du joueur, nous avons d�cid� de laisser cela comme �a.
	Avoir mis le moteur de jeu et de rendu dans deux thread diff�rent nous a permit de facilement impl�menter le syst�me de commande via le thread principal.
	Le moteur de jeu se met � jour toutes les 20ms. On prend le temp du d�but de la mise � jour, le temp de la fin et on met le thread en pause 20ms-(temp fin - temp debut).
	Le moteur de rendu se met � jour toutes les 1000/60 ms soit une fr�quence d'affichage de 60 images par seconde. Nous ne mettons pas en pause ce thread.
	
	
L'ensemble du projet a �t� r�alis� en bin�me. Nous avons travaill� ensemble sur l'ensemble des parties du projet. Il n'est donc pas possible de dire en d�tail qui � fait quoi.


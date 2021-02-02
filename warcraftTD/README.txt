Feuilletin Thomas 
Saur Romain


Nous avons implementé le jeu de base :
	-Un monstre de type terrestre (baseMonster)
	-Un monstre de type aérien (FlightMonster)
	
	-Une tour qui tire des flèches (ArcheryTower)
	-Une tou qui tire des bombes (BombTower)
	
	-Un système permettant d'améliorer les tours de 2 niveau
	-Un système qui gère les différentes vagues de monstres (Wave)
	
Au jeu de base nous avons implementer :
	-Deux boss :
		-Un boss terrestre possédant plus de pv que le monstre terrestre de base ma va plus lentement (BossSolMonster)
		-Un boss aérien possédant plus de pv que le monstre de aérien de base ma va légerement plus lentement (BossAirMonster)
	Les deux boss inflige plus de dégât au joueur (1 dégât pour les monstres de base et 3 pour les boss)
	
	-Une tour qui géle (FreezeTower)
		Cette tour inflige un effet freeze au monstre entrant de son périmètre qui les ralenties d'un certain pourcentage en fonction du niveau de la tour.
		L'effet dure 200tic.
	
	-La possibilité de vendre une tour via la 'R'. 
		La vente d'une tour permet de récupéré un montant fixe d'or dépendant du prix de la tour. Se montant ne change pas en fonction du niveau de la tour.
		
	-La possibilité de mettre le jeu en pause via a touche 'P'.
		Lorsque le jeu est en pause le rendu continue. Il n'est pas possible de poser/améliorer/vendre une tour.
		Pour enlever la pause il suffit de re appuyer sur 'p'
		
	-Un système de sélection de niveau et de difficulté
		-Il y a deux maps enregistrer dans les fichier map1 et map2
			Le fichier commence avec la map. Les 1 representant le chemin
			A la fin de la map le mot "fin" permet de signaler au programme que c'est la fin de la map
			Le fichier se termine par les coordonnées X puis Y du départ du chemin. Avec ces coordonnée et la map le programme calcul le chemin tout seul
		-Il y a 5 niveau de difficulté
			Les 4 premiers utilise des vagues pré enregistrer dans des fichiers (wave1 et wave2)
				-1 wave1 et 300 pièces d'or
				-2 wave2 et 300 pièces d'or
				-3 wave1 et 150 pièces d'or
				-4 wave2 et 150 pièces d'or
			Le dernier niveau de difficulté:
				Le joueur commence avaec 150 pièces d'or
				Le nombre de vagues et leur composition sont généré aléatoirement
	
	-Un système de commande
		Lorsque le jeu tourne il est toujours possible d'écrire des choses dans la console. Ainsi le programme reconnait quelques commandes :
			-help : Commande qui permet d'obtenir la liste complète des commandes ainsi qu'une brève description de chacune.
			-help:[commande] : Commande qui permet des infos plus détaillées sur la commande
			-gold:[montant]/life:[montant] : Commande qui permet d'incrémenter la variable choisi du montant choisi (ex l'or du joueur de 50 pièces avec gold:50 )
			-nextWave : Commande qui permet de passer à la vague suivante sans attendre le temps entre deux vagues
			-restart : Commande qui permet de relancer la partie avec les paramètres qui ont été choisi à la base. Si la difficulté est aléatoire, les vagues sont regénèré de manière aléatoire.
		
Nous avons aussi apporter quelques modifications à la structure du jeu:
	-Nous avons passé l'ensemble des variables public en private en ajoutant des getteurs et setteurs pour les variables le nécessitant. Nous respectons ainsi 
	le principe d'Encapsulation.
	-Nous avons sortie du code les données de jeu (structure des vagues, maps) en les mettant dans des fichiers à part. Ce qui permet de les modifies simplement et d'en ajouter 
	autant que l'on veut sans alourdir le code.
	-Nous avons ajouter une factory pour les créations des monstres pour facilité l'ajout de nouveau type de monstre.
	-Nous avons séparé le moteur de jeu du moteur de rendu. chacun tournant sur un thread différent. Cela nous permet d'avoir une fréquence de rendu différente de la fréquence d'update.
	Lors de la séparation nous avons eu quelques problèmes avec l'erreur "ConcurrentModificationException" dans le thread de rendu. En effet il arrivait que le thread du jeu modifié un
	element du jeu et qu'au même moment le thread de rendu essayer d'afficher cet element ce qui causé cette erreur et le programme s'arrêtait. Ne trouvant pas de moyen de régler ce problème
	nous avons décidé d'entourer les fonctions de rendu d'un try/catch pour capturer l'erreur et laisser le programme continuer. L'erreur ne changeant rien au performence ni au 
	ressenti du joueur, nous avons décidé de laisser cela comme ça.
	Avoir mis le moteur de jeu et de rendu dans deux thread différent nous a permit de facilement implémenter le système de commande via le thread principal.
	Le moteur de jeu se met à jour toutes les 20ms. On prend le temp du début de la mise à jour, le temp de la fin et on met le thread en pause 20ms-(temp fin - temp debut).
	Le moteur de rendu se met à jour toutes les 1000/60 ms soit une fréquence d'affichage de 60 images par seconde. Nous ne mettons pas en pause ce thread.
	
	
L'ensemble du projet a été réalisé en binôme. Nous avons travaillé ensemble sur l'ensemble des parties du projet. Il n'est donc pas possible de dire en détail qui à fait quoi.

